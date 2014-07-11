package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesEntitlementsTest extends CleanTest {

    private static JSONArray entitlemenets = new JSONArray();
    private static JSONArray services = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void buildUp() {
        // GET the existing entityids
        JSONArray jsonEntityArray = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.ENTITYIDS,
                        BasicCall.REST.GET,
                        null,
                        0,
                        0));

        // Creates the first json object to be POSTed on the server
        JSONObject json = new JSONObject();
        json.put("name", "TestService1");
        json.put("entityid", jsonEntityArray.getString(0));
        json.put("url", "test." + jsonEntityArray.getString(0) + ".test");
        json.put("description", "This is a test service for the " + jsonEntityArray.getString(0) + "service provider entity.");
        // POSTs the json object
        persistent.call(
                Const.Api.SERVICES,
                BasicCall.REST.POST,
                json.toString(),
                0, 0);

        services.put(json);

        // Fail the test class if the BeforeClass was unsuccessful at creating the necessary services
        try {
            Assume.assumeTrue(persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
        } catch (AssumptionViolatedException e) {
            System.out.println(
                    "In "
                    + ServicesEntitlementpacksTest.class.getName()
                    + " the first POST call failed");
            fail("POST /api/services was unsuccessful.");
        }

        if (jsonEntityArray.length() > 1) {
            // Creates the second json object to be POSTed on the server
            json = new JSONObject();
            json.put("name", "TestService2");
            json.put("entityid", jsonEntityArray.getString(1));
            json.put("url", "test." + jsonEntityArray.getString(1) + ".test");
            json.put("description", "This is a test service for the " + jsonEntityArray.getString(1) + " service provider entity.");
            // POSTs the json object
            persistent.call(
                    Const.Api.SERVICES,
                    BasicCall.REST.POST,
                    json.toString(),
                    0, 0);

            services.put(json);

            // Fail the test class if the BeforeClass was unsuccessful at creating the necessary services
            try {
                Assume.assumeTrue(persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + ServicesEntitlementpacksTest.class.getName()
                        + " the second POST call failed");
                fail("POST /api/services was unsuccessful.");
            }
        }
    }

    /**
     * POSTs 2 different entitlement over one of the services and verifies them
     * by GETting them on /api/services/{id}/entitlements
     */
    @Test
    public void testServiceEntitlementsPosts() {
        // Creating the first entitlement object
        JSONObject json = new JSONObject();
        json.put("uri", "/testUri1");
        json.put("name", "testName1");
        json.put("description", "This is a test entitlement, the 1st one");
        // Store it
        entitlemenets.put(json);
        // POST it
        persistent.call(
                Const.Api.SERVICES_ENTITLEMENTS,
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
                Const.Api.SERVICES_ENTITLEMENTS,
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
                        Const.Api.SERVICES_ENTITLEMENTS,
                        BasicCall.REST.GET,
                        null,
                        1, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenets.getJSONObject(0), jsonResponseObject.getJSONObject(0), JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenets.getJSONObject(1), jsonResponseObject.getJSONObject(1), JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
