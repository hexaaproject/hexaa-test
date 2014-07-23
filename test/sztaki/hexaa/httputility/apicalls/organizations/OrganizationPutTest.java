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
 * Tests the PUT method on the /api/organizations/{id} call.
 */
public class OrganizationPutTest extends CleanTest {

    public static JSONArray organizations = new JSONArray();

    /**
     * Creates 2 organizations.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organizations(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    /**
     * Modifies one of the two existing organizations and verifies the change of
 its name, and the unchanged second one as well.
     */
    @Test
    public void testOrganizationPut() {
        JSONObject json = new JSONObject();
        json.put("name", "ModifiedByPut");

        persistent.call(Const.Api.ORGANIZATIONS_ID, BasicCall.REST.PUT, json.toString(), 1, 1);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            assertEquals(
                    "ModifiedByPut",
                    ((JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1))).getString("name"));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
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
