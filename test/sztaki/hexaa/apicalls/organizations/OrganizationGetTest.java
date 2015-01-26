package sztaki.hexaa.apicalls.organizations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/organizations and /api/organizations/{id}
 * call.
 */
public class OrganizationGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created organizations.
     */
    public static JSONArray organizations = new JSONArray();

    /**
     * Creates 2 organizations.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organization(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    /**
     * Test the GET method to get an array of all the organizations.
     */
    @Test
    public void testOrganizationGetArray() {
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS,
                                BasicCall.REST.GET));
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(organizations, jsonResponseArray, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Tests the GET method to get an object of specific id.
     */
    @Test
    public void testOrganizationGetById() {
        for (int i = 0; i < organizations.length(); i++) {
            JSONObject jsonResponse
                    = (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    i + 1, 0));
            try {
                JSONAssert.assertEquals(organizations.getJSONObject(i), jsonResponse, JSONCompareMode.LENIENT);
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }
}