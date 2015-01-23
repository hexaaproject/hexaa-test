package sztaki.hexaa.apicalls.attributespecs;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/attributespecs and /api/attributespecs/{id}
 * calls.
 */
public class AttributespecsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributespecsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created attributespecs.
     */
    private static JSONArray attributespecs = new JSONArray();

    /**
     * Creates two attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        attributespecs = Utility.Create.attributespec(new String[]{"testName1", "differentTestName1"}, "user");
    }

    /**
     * GET the attributespec object with call /api/attributespecs/{id}.
     */
    @Test
    public void testAttributespecsGetByID() {
        // Get the attributespec.
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTESPECS_ID,
                                BasicCall.REST.GET,
                                null,
                                2, 0));

        JSONObject jsonResponse = (JSONObject) response;

        try {
            JSONAssert.assertEquals((JSONObject) attributespecs.get(1),
                    jsonResponse, false);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
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
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTESPECS,
                                BasicCall.REST.GET));
        
        if (response instanceof JSONObject) {
            fail("Not a JSONArray but a JSONObject: " + ((JSONObject) response).toString());
        }

        JSONArray jsonResponse = (JSONArray) response;
        try {

            JSONAssert.assertEquals(
                    attributespecs,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
