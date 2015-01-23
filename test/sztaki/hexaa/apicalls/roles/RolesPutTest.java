package sztaki.hexaa.apicalls.roles;

import org.json.JSONArray;
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
 * Tests the PUT method on the /api/role/{id} call.
 */
public class RolesPutTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesPutTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created role.
     */
    public static JSONArray roles = new JSONArray();

    /**
     * Creates one organization and two role.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForRoleDel"});
        roles = Utility.Create.role(new String[]{"testRole1", "testRole2"}, 1);
    }

    /**
     * PUTs the first role and checks that only the first one was modified and
     * the second one is the original.
     */
    @Test
    public void testRolesPut() {
        // Modify the first role
        roles.getJSONObject(0).put("name", "modifiedByPut1");

        persistent.call(
                Const.Api.ROLES_ID,
                BasicCall.REST.PUT,
                roles.getJSONObject(0).toString());

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    roles,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ROLES,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
