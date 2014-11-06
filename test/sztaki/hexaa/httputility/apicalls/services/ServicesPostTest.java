package sztaki.hexaa.httputility.apicalls.services;

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
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST methods on the /api/services.
 */
public class ServicesPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesPostTest.class.getSimpleName() + " ***");
    }

    /**
     * Test for the /app.php/api/services POST call, creates a new Service on
     * the sample entityid and verifies the success.
     */
    @Test
    public void testServicePost() {
        // Creates the json object to be POSTed on the server
        JSONArray services = Utility.Create.service("myService");

        // Checks the creation by Status Line
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs the service by id for verification
        Object response
                = JSONParser.parseJSON(persistent.call(
                                Const.Api.SERVICES,
                                BasicCall.REST.GET,
                                null,
                                1, 0));
        if (response instanceof JSONObject) {
            fail(((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;
        // Checks the service by StatusLine and id as well
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(services, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
