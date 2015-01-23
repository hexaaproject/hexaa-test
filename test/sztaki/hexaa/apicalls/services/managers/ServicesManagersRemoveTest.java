package sztaki.hexaa.apicalls.services.managers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the DELETE method on the /api/services/{id}/managers call.
 */
public class ServicesManagersRemoveTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesManagersRemoveTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store managers.
     */
    public static JSONArray managers = new JSONArray();

    /**
     * Creates two services and two managers and link the two managers to the
     * first service.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        managers = Utility.Create.principal(new String[]{"principalTest1", "principalTest2"});
        managers.put((JSONObject) JSONParser.parseJSON(persistent.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET)));
        Utility.Link.managersToService(1, new int[]{2, 3});
    }

    /**
     * Remove the first test manager from the first service, then GET managers
     * on both services.
     */
    @Test
    public void testServicesManagersRemove() {
        // DELETE call
        Utility.Remove.managerFromService(1, 2);
        managers.remove(0);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_MANAGERS,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesManagersRemoveTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            JSONAssert.assertEquals(
                    managers,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
