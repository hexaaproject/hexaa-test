package sztaki.hexaa.httputility.apicalls.roles;

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
 * Tests the DELETE method on the /api/role/{id} call.
 */
public class RolesDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesDeleteTest.class.getSimpleName() + " ***");
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
     * DELETEs the first role and checks that only the second one exists.
     */
    @Test
    public void testRolesDelete() {
        // The DELETE call.
        persistent.call(Const.Api.ROLES_ID, BasicCall.REST.DEL);

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    roles.getJSONObject(1),
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ROLES,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}