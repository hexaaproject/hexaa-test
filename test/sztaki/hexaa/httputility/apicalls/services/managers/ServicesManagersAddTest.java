package sztaki.hexaa.httputility.apicalls.services.managers;

import org.json.JSONArray;
import org.json.JSONObject;
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
 * Tests the PUT method on the /api/services/{id}/managers/{pid} call.
 */
public class ServicesManagersAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesManagersAddTest.class.getSimpleName() + " ***");
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
    }

    /**
     * ADD two managers to the service and check them by GET.
     */
    @Test
    public void testServicesManagersAdd() {
        // PUT the first manager.
        persistent.call(
                Const.Api.SERVICES_ID_MANAGERS_PID,
                BasicCall.REST.PUT,
                null,
                1, 2);
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // PUT the second manager
        persistent.call(
                Const.Api.SERVICES_ID_MANAGERS_PID,
                BasicCall.REST.PUT,
                null,
                1, 3);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            // GET the managers of the service
            JSONArray jsonResponseArray
                    = (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_MANAGERS,
                                    BasicCall.REST.GET));
            // Remove the updated_at key, as it is changing every time it is asked for
            for (int i=0;i<jsonResponseArray.length();i++) {
                jsonResponseArray.getJSONObject(i).remove("updated_at");
            }
            for (int i=0;i<managers.length();i++) {
                managers.getJSONObject(i).remove("updated_at");
            }
            
            JSONAssert.assertEquals(
                    managers,
                    jsonResponseArray,
                    JSONCompareMode.LENIENT);

        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
