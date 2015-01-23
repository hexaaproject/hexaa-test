package sztaki.hexaa.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET methods on the /api/services and /api/services/{id} calls.
 */
public class ServicesGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created services.
     */
    private static JSONArray services = new JSONArray();

    /**
     * Creates two services.
     */
    @BeforeClass
    public static void setUpClass() {
        services = Utility.Create.service(new String[]{"testServForSrvGet1", "testServForSrvGet2"});
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
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
                assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals("Expected " + services.length() + " services but got " + jsonResponseArray.length(),
                    services.length(), jsonResponseArray.length());
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
