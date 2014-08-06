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
 * Tests the GET method on the /api/entitlements/{id} call.
 */
public class EntitlementsGetTest extends CleanTest {

    /**
     * JSONArray to store the created entitlements.
     */
    private static JSONArray entitlements = new JSONArray();

    /**
     * Uses the Services class utilities to build services and entitlements.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});
    }

    /**
     * Gets the recently created 2 entitlements and compares them to the locally
     * stored ones.
     */
    @Test
    public void testEntitlementsGet() {
        // GETs the entitlement with the id 1.
        JSONObject jsonReaponse = (JSONObject) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.ENTITLEMENTS_ID,
                        BasicCall.REST.GET,
                        null,
                        1, 0));
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlements.getJSONObject(0), jsonReaponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs the entitlement with the id 2.
        jsonReaponse = (JSONObject) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.ENTITLEMENTS_ID,
                        BasicCall.REST.GET,
                        null,
                        2, 0));
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlements.getJSONObject(1), jsonReaponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
