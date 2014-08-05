package sztaki.hexaa.httputility.apicalls.organizations.roles;

import org.json.JSONArray;
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
 * Test the GET method on the /api/organizations/{id}/roles call.
 */
public class OrganizationsRolesGetTest extends CleanTest {

    /**
     * JSONArray to store the created roles in it.
     */
    public static JSONArray roles = new JSONArray();

    /**
     * Creates one organization and one role for it.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");
        roles = Utility.Create.role("testRole", 1);
    }

    /**
     * GET the role of the organization.
     */
    @Test
    public void testOrganizationsRolesGet() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_ROLES,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(roles, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
