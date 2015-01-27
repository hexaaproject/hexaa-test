package sztaki.hexaa.apicalls.roles.principals;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the DELETE method on the /api/role/{id}/principals/{eid} call.
 */
public class RolesPrincipalsRemoveTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesPrincipalsRemoveTest.class.getSimpleName() + " ***");
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
        try {
            principals.put(persistent.getResponseJSONObject(Const.Api.PRINCIPALS_ID_ID,
                    BasicCall.REST.GET));
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(RolesPrincipalsRemoveTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("principals = Utility.Create.principal(\"testPrincipal\") failed");
        }
        Utility.Link.memberToOrganization(1, 2);
        Utility.Link.principalToRole(1, 1);
        Utility.Link.principalToRole(1, 2);
    }

    /**
     * DELETE the principal from the local array and the server as well.
     */
    @Test
    public void testRolesPrincipalRemove() {
        principals.remove(1);

        Utility.Remove.principalFromRole(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.ROLES_ID_PRINCIPALS,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(RolesPrincipalsRemoveTest.class.getName()).log(Level.SEVERE, null, ex);
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
