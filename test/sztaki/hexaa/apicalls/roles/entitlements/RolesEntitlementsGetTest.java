package sztaki.hexaa.apicalls.roles.entitlements;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/role/{id}/entitlements call.
 */
public class RolesEntitlementsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesEntitlementsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created role.
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
     * GET the entitlements of the role.
     */
    @Test
    public void testRolesEntitlementsGet() {

        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ROLES_ID_ENTITLEMENTS,
                                BasicCall.REST.GET));

        try {
            JSONAssert.assertEquals(
                    entitlements.getJSONObject(0),
                    jsonResponseArray.getJSONObject(0),
                    JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(
                    entitlements,
                    jsonResponseArray,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
