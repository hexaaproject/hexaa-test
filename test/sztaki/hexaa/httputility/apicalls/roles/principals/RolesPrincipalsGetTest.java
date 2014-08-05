package sztaki.hexaa.httputility.apicalls.roles.principals;

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
 * Tests the GET method on the /api/roles/{id}/principals call.
 */
public class RolesPrincipalsGetTest extends CleanTest {

    /**
     * JSONArray to store created principals.
     */
    private static JSONArray principals = new JSONArray();

    /**
     * Creates one organization and one role for it than creates a principal and
     * link it to the role.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");
        Utility.Create.role("testRole", 1);
        principals = Utility.Create.principal("testPrincipal");
        Utility.Link.memberToOrganization(1, 2);
        Utility.Link.principalToRole(1, 2);
    }

    /**
     * GET the role and checks the inner JSONObject for the principal.
     */
    @Test
    public void testRolesPrincipalGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ROLES_ID_PRINCIPALS,
                                BasicCall.REST.GET,
                                null,
                                1, 1));

        if (response instanceof JSONObject) {
            fail();
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    principals.getJSONObject(0),
                    jsonResponse.getJSONObject(0).getJSONObject("principal"),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
