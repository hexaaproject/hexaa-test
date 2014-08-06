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
 * Tests the GET methods on the /api/entitlementpacks/public call.
 */
public class EntitlementpacksGetTest extends CleanTest {

    /**
     * JSONArray to store the created entitlementpacks.
     */
    public static JSONArray entitlementpacks = new JSONArray();
    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlements = new JSONArray();

    /**
     * Creates a service, two entitlementpacks for it, and an entitlement for
     * the first entitlementpack.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlements1"});
        Utility.Link.entitlementToPack(1, 1);
    }

    /**
     * Tests the GET method on /api/entitlementpacks/public call.
     */
    @Test
    public void testEntitlementpacksPublicGet() {
        // GET call and JSON parsing
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ENTITLEMENTPACKS_PUBLIC,
                                BasicCall.REST.GET));
        try {
            // Asserting on the statusline for 200
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            // Asserting the JSON response with the local entitlementpacks array
            JSONAssert.assertEquals(
                    entitlementpacks.getJSONObject(0),
                    jsonResponseArray.getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Tests the GET method on /api/entitlementpacks/{id}.
     */
    @Test
    public void testEntitlementpacksGetByID() {
        for (int i = 0; i < 2; i++) {
            // GET call and JSON parsing
            JSONObject jsonResponse
                    = (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ENTITLEMENTPACKS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    i + 1, 0));
            try {
                // Asserting on the statusline for 200
                assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
                // Asserting the JSON response with the local entitlementpacks array
                JSONAssert.assertEquals(
                        entitlementpacks.getJSONObject(i),
                        jsonResponse,
                        JSONCompareMode.LENIENT);
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }

    /**
     * Tests the GET method on /api/entitlementpacks/{id}/entitlements.
     */
    @Test
    public void testEntitlementpacksGetEntitlement() {
        // GET call and JSON parsing
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                                BasicCall.REST.GET,
                                null,
                                1, 0));
        try {
            // Asserting on the statusline for 200
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            // Asserting the JSON response with the local entitlements array
            JSONAssert.assertEquals(
                    entitlements.getJSONObject(0),
                    jsonResponseArray.getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
