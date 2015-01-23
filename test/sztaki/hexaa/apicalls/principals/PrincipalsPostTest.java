package sztaki.hexaa.apicalls.principals;

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
        json.put("email", "testFedid1@email.something");
        json.put("display_name", "testFedid1_name");

        Utility.Create.principal(json.getString("fedid"));
        
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.PRINCIPALS,
                                    BasicCall.REST.GET)))
                    .getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        System.out.println(persistent.call(
                                    Const.Api.PRINCIPALS,
                                    BasicCall.REST.GET));
    }
}
