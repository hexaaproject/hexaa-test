package sztaki.hexaa.httputility.apicalls.organizations;

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
 * Tests the POST method on the /api/organizations call.
 */
public class OrganizationPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationPostTest.class.getSimpleName() + " ***");
    }

    /**
     * Test for creating a new organization and verify its existence.
     */
    @Test
    public void testOrganizationPost() {
        // Creating the JSON object
        JSONObject json = new JSONObject();
        json.put("name", "testOrganizationName1");
        
        Utility.Create.organization(json.getString("name"));
        // Verifies the POST with a GET
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
