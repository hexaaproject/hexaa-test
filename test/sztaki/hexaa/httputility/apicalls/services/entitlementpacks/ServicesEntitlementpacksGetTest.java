package sztaki.hexaa.httputility.apicalls.services.entitlementpacks;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/services/{id}/entitlementpacks call with
 * more services and entitlementpacks.
 */
public class ServicesEntitlementpacksGetTest extends CleanTest {

    private static JSONArray entitlementpacks = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void buildUp() {
        Utility.Create.services(new String[]{"testService1", "testService2"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
        JSONArray entitlementpacksTemp = Utility.Create.entitlementpacks(2, new String[]{"testEntitlementpacks3"});
        entitlementpacks.put(entitlementpacksTemp.getJSONObject(0));
    }

    /**
     * Calls GET /api/services/{id}/entitlements on the 2 services to get the 3
     * enttitlements created in the buildUp().
     */
    @Test
    public void testServicesEntitlementsGet() {
        // GETs the first service's entitlementpacks
        JSONArray jsonResponse = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
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
        // GETs the second service's entitlementpacks
        jsonResponse = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
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
