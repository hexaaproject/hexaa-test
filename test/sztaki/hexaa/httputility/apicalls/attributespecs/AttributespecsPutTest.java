package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/attributespecs/{id} call.
 */
public class AttributespecsPutTest extends CleanTest {

    /**
     * JSONArray to store the created attributespecs.
     */
    private static JSONArray attributespecs = new JSONArray();

    /**
     * Creates one attributespec.
     */
    @BeforeClass
    public static void setUpClass() {
        attributespecs = Utility.Create.attributespec(new String[]{"testName1"});
    }

    /**
     * PUT a changed attributespec and verify it.
     */
    @Test
    public void testAttributespecsPut() {
        // Change and PUT
        ((JSONObject) attributespecs.get(0)).put("oid", "oidByPut");
        ((JSONObject) attributespecs.get(0)).put("friendly_name", "nameByPut");

        persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.PUT,
                ((JSONObject) attributespecs.get(0)).toString(),
                1, 0);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Verify
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTESPECS_ID,
                                BasicCall.REST.GET,
                                null,
                                1, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals("oidByPut", jsonResponse.get("oid"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
