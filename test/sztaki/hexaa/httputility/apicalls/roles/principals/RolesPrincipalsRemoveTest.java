package sztaki.hexaa.httputility.apicalls.roles.principals;

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
                persistent.call(
                        Const.Api.PRINCIPALS_ID,
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

        persistent.call(
                Const.Api.ROLES_ID_PRINCIPALS_PID,
                BasicCall.REST.DEL,
                null,
                1, 1);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    principals.getJSONObject(0),
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ROLES_ID_PRINCIPALS,
                                    BasicCall.REST.GET))).getJSONObject(0).getJSONObject("principal"),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
