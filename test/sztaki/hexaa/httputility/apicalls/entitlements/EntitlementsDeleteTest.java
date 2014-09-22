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
 * Tests the DELETE method on the /api/entitlements/{id} call.
 */
public class EntitlementsDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementsDeleteTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created entitlements.
     */
    private static JSONArray entitlements = new JSONArray();

    /**
     * Creates one service and two entitlements.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(
                new String[]{
                    "testService1"});
        entitlements = Utility.Create.entitlements(
                1,
                new String[]{
                    "testEntitlements1",
                    "testEntitlements2"});
    }

    /**
     * DELETEs the first entitlement and checks that only the second one exists.
     */
    @Test
    public void testEntitlementsDelete() {
        // The DELETE call on the first one.
        Utility.Remove.entitlement(1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            // GET the first one (the DELETEd one).
            persistent.call(
                    Const.Api.ENTITLEMENTS_ID,
                    BasicCall.REST.GET);
            assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
            // GET the second one.
            JSONAssert.assertEquals(
                    entitlements.getJSONObject(1),
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ENTITLEMENTS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 0)),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
