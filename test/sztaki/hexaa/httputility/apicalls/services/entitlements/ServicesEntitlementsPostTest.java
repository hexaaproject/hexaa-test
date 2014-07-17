package sztaki.hexaa.httputility.apicalls.services.entitlements;

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
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Test for the POST /app.php/api/services/{id}/entitlements call.
 */
public class ServicesEntitlementsPostTest extends Services {

    private static JSONArray entitlemenets = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void setUpClass() {
        createServices(new String[] {"testService1","testService2"});
    }

    /**
     * POSTs 2 different entitlement over one of the services and verifies them
     * by GETting them on /api/services/{id}/entitlements.
     */
    @Test
    public void testServiceEntitlementsPosts() {
        // Creating the first entitlement object
        JSONObject json = new JSONObject();
        json.put("uri", "/testUri1");
        json.put("name", "testEntitlementName1");
        json.put("description", "This is a test entitlement, the 1st one");
        // Store it
        entitlemenets.put(json);
        // POST it
        persistent.call(
                Const.Api.SERVICES_ID_ENTITLEMENTS,
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
        json.put("uri", "/testUri2");
        json.put("name", "testName2");
        json.put("description", "This is a test entitlement, the 2nd one");
        // Store it
        entitlemenets.put(json);
        // POST it
        persistent.call(
                Const.Api.SERVICES_ID_ENTITLEMENTS,
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
                        Const.Api.SERVICES_ID_ENTITLEMENTS,
                        BasicCall.REST.GET,
                        null,
                        1, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenets.getJSONObject(0), jsonResponseObject.getJSONObject(0), JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(entitlemenets.getJSONObject(1), jsonResponseObject.getJSONObject(1), JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
