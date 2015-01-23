package sztaki.hexaa.httputility.apicalls.roles.principals;

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
import sztaki.hexaa.httputility.apicalls.CleanTest;

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
        principals.put(((JSONObject) JSONParser.parseJSON(
                persistent.call(Const.Api.PRINCIPALS_ID_ID,
                        BasicCall.REST.GET))));
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

        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ROLES_ID_PRINCIPALS,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail(response.toString());
        }
        JSONArray jsonResponse = (JSONArray) response;
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
