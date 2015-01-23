package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DELETE method on the /api/services/{id} call.
 */
public class ServicesDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesDeleteTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created services.
     */
    public static JSONArray services = new JSONArray();

    /**
     * Creates one organization and two services.
     */
    @BeforeClass
    public static void setUpClass() {
        services = Utility.Create.service(new String[]{"testService1", "testService2"});
    }

    /**
     * DELETE the first service and checks that only the second one exists.
     */
    @Test
    public void testServicesDelete() {
        // The DELETE call.
        Utility.Remove.service(1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    services.getJSONObject(1),
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}