package sztaki.hexaa.httputility.apicalls.principals;

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
 * Test the GET method on /api/principal/roles call.
 */
public class PrincipalsRoleGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsRoleGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created roles.
     */
    private static JSONArray roles = new JSONArray();

    /**
     * Creates an organization and a role and links the current principal to the
     * organization and the role.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForPrincGet"});
        roles = Utility.Create.role(new String[]{"role1"}, 1);
        
        Utility.Link.memberToOrganization(1, 1);

        Utility.Link.principalToRole(1, new int[]{1});
    }

    /**
     * GET all the roles of the current principal.
     */
    @Test
    public void testPrincipalRolesGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ROLES,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(roles, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
