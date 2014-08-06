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
 * Tests the DELETE method on the /api/services/{id}/managers call.
 */
public class ServicesManagersRemoveTest extends CleanTest {

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
        persistent.call(
                Const.Api.SERVICES_ID_MANAGERS_PID,
                BasicCall.REST.DEL,
                null,
                1, 2);
        managers.remove(0);
        
        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    managers,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_MANAGERS,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(
                    managers.getJSONObject(1),
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_MANAGERS,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 2))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
