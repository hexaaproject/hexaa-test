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
 * Tests the PATCH method on the /api/attributespecs/{id} call.
 */
public class AttributespecsPatchTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributespecsPatchTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created attributespecs.
     */
    private static JSONArray attributespecs = new JSONArray();

    /**
     * Creates one attributespec.
     */
    @BeforeClass
    public static void setUpClass() {
        attributespecs = Utility.Create.attributespec(new String[]{"testName1"}, "user");
    }

    /**
     * PATCH a changed attributespec and verify it.
     */
    @Test
    public void testAttributespecsPatch() {
        // Change and PUT
        JSONObject json = new JSONObject();
        json.put("oid", "oidByPut");
        attributespecs.getJSONObject(0).put("oid", "oidByPut");
        

        persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.PATCH,
                json.toString(),
                1, 0);

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals("oidByPut", jsonResponse.get("oid"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
