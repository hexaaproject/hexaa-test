package sztaki.hexaa.httputility.apicalls.roles.entitlements;

import org.json.JSONArray;
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
 * Tests the DELETE method on the /api/role/{id}/entitlements/{eid} call.
 */
public class RolesEntitlementsRemoveTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesEntitlementsRemoveTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlements = new JSONArray();

    /**
     * Creates an organization, a role, a service, two entitlements and an
     * entitlementpack, and links them together.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.role(new String[]{"testRole1"}, 1);
        Utility.Create.service(new String[]{"testService1"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlement1", "testEntitlement2"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementToPack(2, 1);
        Utility.Link.entitlementpackToOrg(1, new int[]{1});

        Utility.Link.entitlementsToRole(1, new int[]{1, 2});
    }

    /**
     * DELETE the entitlements of the role.
     */
    @Test
    public void testRolesEntitlementsDelete() {
        entitlements.remove(0);

        Utility.Remove.entitlementFromRole(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    entitlements,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ROLES_ID_ENTITLEMENTS,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
