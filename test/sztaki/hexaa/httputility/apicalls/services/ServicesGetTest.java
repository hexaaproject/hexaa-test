package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 * Tests the GET methods on the /api/services and /api/services/{id} calls after
 * a sample database (with 2 services) have been built.
 */
public class ServicesGetTest extends Services {

    /**
     * JSONArray for a local reference of the objects on the server for asserts.
     */
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
