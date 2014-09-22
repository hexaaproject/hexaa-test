package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

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
     * PUTs the first service and checks that only the first one was modified and
     * the second one is the original.
     */
    @Test
    public void testServicesPut() {
        // Modify the first role
        services.getJSONObject(0).put("name", "modifiedByPut1");

        persistent.call(
                Const.Api.SERVICES_ID,
                BasicCall.REST.PUT,
                services.getJSONObject(0).toString());

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    services,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}