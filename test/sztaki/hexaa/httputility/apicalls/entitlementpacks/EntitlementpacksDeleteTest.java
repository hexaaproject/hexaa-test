package sztaki.hexaa.httputility.apicalls.entitlementpacks;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DELETE method on the /api/entitlementpacks/{id} call.
 */
public class EntitlementpacksDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementpacksDeleteTest.class.getSimpleName() + " ***");
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
     * DELETEs one of the two created entitlements, and GETs both of them.
     */
    @Test
    public void testEntitlementsDelete() {
        // The DELETE call.
        Utility.Remove.entitlementpack(1);
        
        try {
            // Checks the status line from the DELETE call for 204
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            // GETs the one that was deleted and checks the status line for 404
            persistent.call(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.GET, null, 1, 0);
            assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
            // GETs the second entitlements and asserts it as a JSON
            JSONAssert.assertEquals(
                    entitlementpacks.getJSONObject(1),
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ENTITLEMENTPACKS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 0)),
                    JSONCompareMode.LENIENT);
            // Checks the status line for 200
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
