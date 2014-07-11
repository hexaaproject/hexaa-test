package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesGetTest extends CleanTest {

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
                    + ServicesGetTest.class.getName()
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
                        + ServicesGetTest.class.getName()
                        + " the second POST call failed");
                fail("POST /api/services was unsuccessful.");
            }
        }
    }

    /**
     * Tests the /api/services GET which responds with an Array of available
     * services.
     */
    @Test
    public void testServicesGetArray() {
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(persistent.call(
                                Const.Api.SERVICES,
                                BasicCall.REST.GET));
        try {
            JSONAssert.assertEquals(services, jsonResponseArray, false);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Tests the /api/services/{id} GET which responds with an Object of that
     * exact service.
     */
    @Test
    public void testServicesGetByID() {
        JSONObject jsonResponseObject
                = (JSONObject) JSONParser.parseJSON(persistent.call(
                                Const.Api.SERVICES_ID,
                                BasicCall.REST.GET,
                                null,
                                1, 0));
        try {
            JSONAssert.assertEquals(services.getJSONObject(0), jsonResponseObject, false);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        if (services.length() > 1) {
            jsonResponseObject
                    = (JSONObject) JSONParser.parseJSON(persistent.call(
                                    Const.Api.SERVICES_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 0));
            try {
                JSONAssert.assertEquals(services.getJSONObject(1), jsonResponseObject, false);
                assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }

    /**
     * Tests the /api/services GET which responds with an Array of available
     * services and checks the contained Objects one by one.
     */
    @Test
    public void testServicesGetByArray() {
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(persistent.call(
                                Const.Api.SERVICES,
                                BasicCall.REST.GET));
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
            return;
        }
        for (int i = 0; i < services.length(); i++) {
            try {
                JSONAssert.assertEquals(services.getJSONObject(i), jsonResponseArray.getJSONObject(i), false);
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }
}
