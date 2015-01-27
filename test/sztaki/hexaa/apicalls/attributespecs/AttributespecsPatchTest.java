package sztaki.hexaa.apicalls.attributespecs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

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
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTESPECS_ID,
                    BasicCall.REST.GET,
                    null,
                    1, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(AttributespecsPatchTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals("oidByPut", jsonResponse.get("oid"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
