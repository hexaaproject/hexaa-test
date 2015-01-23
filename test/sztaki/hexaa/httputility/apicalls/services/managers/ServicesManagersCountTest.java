package sztaki.hexaa.httputility.apicalls.services.managers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/services/{id}/manager/count call.
 */
public class ServicesManagersCountTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesManagersCountTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store managers.
     */
    public static JSONArray managers = new JSONArray();

    /**
     * Creates two services and two principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        managers = Utility.Create.principal(new String[]{"principalTest1", "principalTest2"});
        managers.put((JSONObject) JSONParser.parseJSON(persistent.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET)));
        Utility.Link.managersToServiceByArray(1, new int[]{1, 2, 3});
    }

    /**
     * Get the manager count.
     */
    @Test
    public void testServicesManagersCount() {
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.SERVICES_ID_MANAGER_COUNT,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesManagersAddTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
//        System.out.println(managers);
        System.out.println(jsonResponse);

        try {
            assertEquals(3, jsonResponse.get("count"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
