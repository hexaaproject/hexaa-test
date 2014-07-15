package sztaki.hexaa.httputility.apicalls.services.entitlementpacks;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.services.Services;

public class ServicesEntitlementpacksPostTest extends Services {

    private static JSONArray entitlemenetpacks = new JSONArray();
    private static JSONArray services = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void buildUp() {
        services = createServices(2);
    }

    /**
     * POSTs 2 different entitlement over one of the services and verifies them
     * by GETting them on /api/services/{id}/entitlements.
     */
    @Test
    public void testServiceEntitlementpacksPosts() {
        // Creating the first entitlement object
        JSONObject json = new JSONObject();
        json.put("name", "testEntitlementpackName1");
        json.put("description", "This is a test entitlementpack, the 1st one");
        json.put("type", "public");
        // Store it
        entitlemenetpacks.put(json);
        // POST it
        persistent.call(
                Const.Api.SERVICES_ENTITLEMENTPACKS,
                BasicCall.REST.POST,
                json.toString(),
                1, 0);
        // Checks the status line
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Creating the second entitlement object
        json = new JSONObject();
        json.put("name", "testEntitlementpackName2");
        json.put("description", "This is a test entitlementpack, the 2nd one");
        json.put("type", "public");
        // Store it
        entitlemenetpacks.put(json);
        // POST it
        persistent.call(
                Const.Api.SERVICES_ENTITLEMENTPACKS,
                BasicCall.REST.POST,
                json.toString(),
                1, 0);
        // Checks the status line
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs the entitlements from the server
        JSONArray jsonResponseObject = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ENTITLEMENTPACKS,
                        BasicCall.REST.GET,
                        null,
                        1, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenetpacks.getJSONObject(0), jsonResponseObject.getJSONObject(0), JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(entitlemenetpacks.getJSONObject(1), jsonResponseObject.getJSONObject(1), JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}