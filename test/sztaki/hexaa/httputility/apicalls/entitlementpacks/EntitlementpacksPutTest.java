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
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Tests the PUT methods on the /api/entitlementpacks/{id} uri.
 */
public class EntitlementpacksPutTest extends CleanTest {

    private static JSONArray entitlementpacks = new JSONArray();

    /**
     * Uses the Services class utilities to build services and entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Services.createServices(new String[] {"testService1"});
        entitlementpacks = Services.createServiceEntitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
    }

    /**
     * Changes one attribute of the entitlementpack and verifies it by GETing
     * the entitlementpack from the server.
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
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
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
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.LENIENT);
            assertEquals("changedNameByPut", jsonResponse.getString("name"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
