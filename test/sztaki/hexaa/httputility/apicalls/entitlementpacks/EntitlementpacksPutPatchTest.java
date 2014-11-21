package sztaki.hexaa.httputility.apicalls.entitlementpacks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

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
    public void testEntitlementsPut() {
        JSONObject json = entitlementpacks.getJSONObject(0);

        json.put("name", "changedNameByPut");

        persistent.call(
                Const.Api.ENTITLEMENTPACKS_ID,
                BasicCall.REST.PUT,
                json.toString(),
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

    /**
     * Changes one attribute of the entitlementpack by patch and verifies it by
     * GETing the entitlementpack from the server.
     */
    @Test
    public void testEntitlementsPatch() {
        JSONObject json = entitlementpacks.getJSONObject(0);

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