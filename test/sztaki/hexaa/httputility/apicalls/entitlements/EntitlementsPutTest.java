package sztaki.hexaa.httputility.apicalls.entitlements;

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
 * Tests the PUT call on the /api/entitlements/{id}.
 */
public class EntitlementsPutTest extends CleanTest {

    private static JSONArray entitlements = new JSONArray();

    /**
     * Uses the Services class utilities to build services and entitlements.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.services(new String[]{"testService1"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});
    }

    /**
     * Changes one attribute of the entitlement and verifies it by GETing the
     * entitlement from the server.
     */
    @Test
    public void testEntitlementsPut() {
        JSONObject json = entitlements.getJSONObject(0);

        json.put("name", "changedNameByPut");

        persistent.call(
                Const.Api.ENTITLEMENTS_ID,
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
                        Const.Api.ENTITLEMENTS_ID,
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
