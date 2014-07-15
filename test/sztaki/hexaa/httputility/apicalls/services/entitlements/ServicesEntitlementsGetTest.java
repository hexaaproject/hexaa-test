package sztaki.hexaa.httputility.apicalls.services.entitlements;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Tests the GET method on the /api/services/{id}/entitlements call with more
 * services and entitlements.
 */
public class ServicesEntitlementsGetTest extends Services {

    private static JSONArray entitlements = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void setUpClass() {
        createServices(2);
        entitlements = createServiceEntitlements(1, 2);
        entitlements = createServiceEntitlements(2, 1);
    }

    /**
     * Calls GET /api/services/{id}/entitlements on the 2 services to get the 3
     * enttitlements created in the buildUp().
     */
    @Test
    public void testServicesEntitlementsGet() {
        // GETs the first services entitlements
        JSONArray jsonResponse = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ENTITLEMENTS,
                        BasicCall.REST.GET,
                        null,
                        1, 0));

        JSONArray jsonTemp = new JSONArray();
        jsonTemp.put(entitlements.getJSONObject(0));
        jsonTemp.put(entitlements.getJSONObject(1));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        // GETs the second services entitlement
        jsonResponse = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ENTITLEMENTS,
                        BasicCall.REST.GET,
                        null,
                        2, 0));

        jsonTemp = new JSONArray();
        jsonTemp.put(entitlements.getJSONObject(2));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
