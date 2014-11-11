package sztaki.hexaa.httputility.apicalls.consents;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST method on the /api/consents call.
 */
public class ConsentsGetTest extends CleanTest {

    /**
     * JSONArray to store the created services.
     */
    private static JSONArray consents = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ConsentsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates the necessary objects on the server to begin the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service("testService1");
        Utility.Create.attributespec("randomOID1", "user");
        Utility.Link.attributespecsToService(1, 1, true);
        consents = Utility.Create.consent(true, new int[]{1}, 1);

        Utility.Create.service("testService2");
        Utility.Create.attributespec("randomOID2", "user");
        Utility.Link.attributespecsToService(2, 2, true);
        consents.put(Utility.Create.consent(true, new int[]{2}, 2).getJSONObject(0));
    }

    /**
     * Creates a consent and verifies it from the status line.
     */
    @Test
    public void testConsentsGet() {
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.CONSENTS,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }

        System.out.println(consents);
        System.out.println(jsonResponse);

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            // támad a kerge jsonassert kór, ugyan elemről elemre pontosan benne van nem dob egyezést, holnap folytatom.
            JSONAssert.assertEquals(consents, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
