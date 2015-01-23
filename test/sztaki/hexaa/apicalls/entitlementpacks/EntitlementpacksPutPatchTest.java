package sztaki.hexaa.apicalls.entitlementpacks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the PUT methods on the /api/entitlementpacks/{id} uri.
 */
public class EntitlementpacksPutPatchTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementpacksPutPatchTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created entitlementpacks.
     */
    private static JSONArray entitlementpacks = new JSONArray();

    /**
     * Creates one service and two entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
    }

    /**
     * Changes one attribute of the entitlementpack by put and verifies it by
     * GETing the entitlementpack from the server.
     */
    @Test
    public void testEntitlementpacksPut() {
        JSONObject json = entitlementpacks.getJSONObject(0);

        json.remove("name");
        json.put("name", "changedNameByPut");
        json.remove("id");

        persistent.call(
                Const.Api.ENTITLEMENTPACKS_ID,
                BasicCall.REST.PUT,
                json.toString(),
                1, 0);
        System.out.println(persistent.getResponse());

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONObject jsonResponse;
        try {
            jsonResponse
                    = persistent.getResponseJSONObject(
                            Const.Api.ENTITLEMENTPACKS_ID,
                            BasicCall.REST.GET,
                            null,
                            1, 0);

        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }
        
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.LENIENT);
            assertEquals("changedNameByPut", jsonResponse.getString("name"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Changes one attribute of the entitlementpack by patch and verifies it by
     * GETing the entitlementpack from the server.
     */
    @Test
    public void testEntitlementpacksPatch() {
        JSONObject json = entitlementpacks.getJSONObject(0);
        
        json.remove("name");
        json.put("name", "changedNameByPut");

        JSONObject temp = new JSONObject();
        temp.put("name", "changedNameByPut");

        persistent.call(
                Const.Api.ENTITLEMENTPACKS_ID,
                BasicCall.REST.PATCH,
                temp.toString(),
                1, 0);

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONObject jsonResponse = (JSONObject) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.ENTITLEMENTPACKS_ID,
                        BasicCall.REST.GET,
                        null,
                        1, 0));
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.LENIENT);
            assertEquals("changedNameByPut", jsonResponse.getString("name"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
