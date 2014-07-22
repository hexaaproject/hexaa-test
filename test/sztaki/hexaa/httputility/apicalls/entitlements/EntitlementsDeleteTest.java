package sztaki.hexaa.httputility.apicalls.entitlements;

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
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DEL call on the /api/entitlements/{id}.
 */
public class EntitlementsDeleteTest extends CleanTest {

    private static JSONArray entitlements = new JSONArray();

    /**
     * Uses the Services class utilities to build services and entitlements.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.services(new String[]{"testService1"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});
    }

    /**
     * DELETEs one of the two created entitlements, and GETs both of them, the
     * first to check that its deleted and gives a 404 error, and the second to
     * make sure that it does not effect independent objects.
     */
    @Test
    public void testEntitlementsDelete() {
        // The DEL call
        persistent.call(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.DEL, null, 1, 0);

        try {
            // Checks the status line from the DEL call for 204
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            // GETs the one that was deleted and checks the status line for 404
            persistent.call(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.GET, null, 1, 0);
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
            // GETs the second entitlements and asserts it as a JSON
            JSONAssert.assertEquals(
                    entitlements.getJSONObject(1),
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ENTITLEMENTS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 0)),
                    JSONCompareMode.LENIENT);
            // Checks the status line for 200
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
