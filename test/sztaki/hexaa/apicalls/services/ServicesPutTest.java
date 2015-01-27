package sztaki.hexaa.apicalls.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the PUT method on the /api/services/{id} call.
 */
public class ServicesPutTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesPutTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created services.
     */
    public static JSONArray services = new JSONArray();

    /**
     * Creates two services.
     */
    @BeforeClass
    public static void setUpClass() {
        services = Utility.Create.service(new String[]{"testService1", "testService2"});
    }

    /**
     * PUTs the first service and checks that only the first one was modified
     * and the second one is the original.
     */
    @Test
    public void testServicesPut() {
        // Modify the first role
        JSONObject json;
        json = services.getJSONObject(0);
        json.put("name", "modifiedByPut1");
        json.remove("id");

        persistent.call(
                Const.Api.SERVICES_ID,
                BasicCall.REST.PUT,
                json.toString());

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesPutTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
        try {
            JSONAssert.assertEquals(
                    services,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
