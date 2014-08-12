package sztaki.hexaa.httputility.apicalls.principals;

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
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST method on the /app.php/api/principals call.
 */
public class PrincipalsPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsPostTest.class.getSimpleName() + " ***");
    }

    /**
     * POST a new principal and verify it.
     */
    @Test
    public void testPrincipalsPost() {
        JSONObject json = new JSONObject();
        json.put("fedid", "testFedid1");
        json.put("email", "test@email.something");

        persistent.call(Const.Api.PRINCIPALS, BasicCall.REST.POST, json.toString());

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.PRINCIPALS,
                                    BasicCall.REST.GET)))
                    .getJSONObject(1),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
