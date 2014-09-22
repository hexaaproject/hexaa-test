package sztaki.hexaa.httputility.apicalls.organizations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/organization/{id} call.
 */
public class OrganizationPutTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationPutTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created organization.
     */
    public static JSONArray organizations = new JSONArray();

    /**
     * Creates 2 organization.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organization(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    /**
     * Modifies one of the two existing organization and verifies the change of
     * its name, and the unchanged second one as well.
     */
    @Test
    public void testOrganizationPut() {
        JSONObject json = new JSONObject();
        json.put("name", "ModifiedByPut");

        persistent.call(
                Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.PUT,
                json.toString(),
                1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
            assertEquals(
                    "ModifiedByPut",
                    ((JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1))).getString("name"));
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    organizations.getJSONObject(1),
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 1)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
