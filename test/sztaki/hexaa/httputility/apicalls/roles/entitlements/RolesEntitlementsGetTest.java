package sztaki.hexaa.httputility.apicalls.roles.entitlements;

import org.json.JSONArray;
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
 * Tests the GET method on the /api/roles/{id}/entitlements call.
 */
public class RolesEntitlementsGetTest extends CleanTest {

    /**
     * JSONArray to store the created roles.
     */
    public static JSONArray entitlements = new JSONArray();

    /**
     * Creates an organization, a role, a service, two entitlements and an
     * entitlementpack, and links them together.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.roles(new String[]{"testRole1"}, 1);
        Utility.Create.services(new String[]{"testService1"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlement1", "testEntitlement2"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementToPack(2, 1);
        Utility.Link.entitlementpacksToOrg(1, new int[]{1});

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
