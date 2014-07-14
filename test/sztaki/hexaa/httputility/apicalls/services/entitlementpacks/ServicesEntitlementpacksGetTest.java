package sztaki.hexaa.httputility.apicalls.services.entitlementpacks;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.services.Services;
import static sztaki.hexaa.httputility.apicalls.services.Services.createServiceEntitlements;
import static sztaki.hexaa.httputility.apicalls.services.Services.createServices;

/**
 * Tests the GET method on the /api/services/{id}/entitlementpacks call with
 * more services and entitlementpacks.
 */
public class ServicesEntitlementpacksGetTest extends Services {

    private static JSONArray entitlementpacks = new JSONArray();
    private static JSONArray services = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void buildUp() {
        services = createServices(2);
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(persistent.call(
                                Const.Api.SERVICES,
                                BasicCall.REST.GET));
        entitlementpacks = createServiceEntitlementpacks(jsonResponseArray.getJSONObject(0).getInt("id"), 2);
        entitlementpacks = createServiceEntitlementpacks(jsonResponseArray.getJSONObject(1).getInt("id"), 1);
    }

    /**
     * Calls GET /api/services/{id}/entitlements on the 2 services to get the 3
     * enttitlements created in the buildUp().
     */
    @Test
    public void testServicesEntitlementsGet() {

        JSONArray jsonResponse = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ENTITLEMENTPACKS,
                        BasicCall.REST.GET,
                        null,
                        1, 0));

        JSONArray jsonTemp = new JSONArray();
        jsonTemp.put(entitlementpacks.getJSONObject(0));
        jsonTemp.put(entitlementpacks.getJSONObject(1));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        jsonResponse = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ENTITLEMENTPACKS,
                        BasicCall.REST.GET,
                        null,
                        2, 0));

        jsonTemp = new JSONArray();
        jsonTemp.put(entitlementpacks.getJSONObject(2));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
