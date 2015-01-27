package sztaki.hexaa.apicalls.roles.principals;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/roles/{id}/principals call.
 */
public class RolesPrincipalsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesPrincipalsGetTest.class.getSimpleName() + " ***");
    }

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
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.ROLES_ID_PRINCIPALS,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(RolesPrincipalsGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        if (jsonResponse.length() < 1) {
            fail(jsonResponse.toString());
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    principals.getJSONObject(0),
                    jsonResponse.getJSONObject(0).getJSONObject("principal"),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
