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
     * JSONArray to store managers.
     */
    public static JSONArray managers = new JSONArray();

    /**
     * Creates two services and two principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.services(new String[]{"testService1", "testService2"});
        managers = Utility.Create.principal(new String[]{"principalTest1", "principalTest2"});
        managers.put((JSONObject) JSONParser.parseJSON(persistent.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET)));
    }

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
            JSONAssert.assertEquals(
                    managers,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_MANAGERS,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);

        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
