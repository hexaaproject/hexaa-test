package sztaki.hexaa.apicalls.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the PUT and PATCH methods on /api/principals/{id}/id calls.
 */
public class PrincipalsPutPatchTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsPutPatchTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store principals.
     */
    public static JSONArray principals = new JSONArray();

    /**
     * Creates one principal.
     */
    @BeforeClass
    public static void setUpClass() {
        principals = Utility.Create.principal("testToPutPatch");
    }

    /**
     * Changes the test principals fedid by PUT.
     */
    @Test
    public void testPrincipalsPut() {
        principals.getJSONObject(0).put("fedid", "modifiedByPut");
        
        persistent.call(
                Const.Api.PRINCIPALS_ID,
                BasicCall.REST.PUT,
                principals.getJSONObject(0).toString(),
                2, 2);
        
        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(Const.Api.PRINCIPALS_ID_ID, BasicCall.REST.GET, null, 2, 2);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }
        
        try {
            JSONAssert.assertEquals(principals.getJSONObject(0), jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Changes the test principals fedid by PATCH.
     */
    @Test
    public void testPrincipalsPatch() {
        JSONObject json = new JSONObject();
        json.put("fedid", "modifiedByPatch");
        principals.getJSONObject(0).put("fedid", "modifiedByPatch");
        
        persistent.call(
                Const.Api.PRINCIPALS_ID,
                BasicCall.REST.PATCH,
                json.toString(),
                2, 2);
        
        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(Const.Api.PRINCIPALS_ID_ID, BasicCall.REST.GET, null, 2, 2);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }
        
        try {
            JSONAssert.assertEquals(principals.getJSONObject(0), jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
