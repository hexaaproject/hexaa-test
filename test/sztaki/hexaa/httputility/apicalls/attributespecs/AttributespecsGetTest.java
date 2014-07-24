package sztaki.hexaa.httputility.apicalls.attributespecs;

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
 * Tests the GET method on the /app.php/api/attributespecs and
 * /app.php/api/attributespecs/{id} calls.
 */
public class AttributespecsGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributespecs.
     */
    private static JSONArray attributespecs = new JSONArray();

    /**
     * Creates two attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        attributespecs = Utility.Create.attributespecs(new String[]{"testName1", "differentTestName1"});
    }

    /**
     * GET the attributespec object with call /api/attributespecs/{id}.
     */
    @Test
    public void testAttributespecsGetByID() {
        // Get the attributespec.
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTESPECS_ID,
                                BasicCall.REST.GET,
                                null,
                                2, 0));
        try {
            JSONAssert.assertEquals((JSONObject) attributespecs.get(1),
                    jsonResponse, false);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET the attributespecs array with call /app.php/api/attributespecs.
     */
    @Test
    public void testAttributespecsArray() {
        // GET the array.
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTESPECS,
                                BasicCall.REST.GET));

        try {

            JSONAssert.assertEquals(
                    attributespecs,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
