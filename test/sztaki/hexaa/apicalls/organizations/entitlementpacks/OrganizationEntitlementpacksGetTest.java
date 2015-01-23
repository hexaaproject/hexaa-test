package sztaki.hexaa.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the DELETE method on the /api/organizations/{id}/entitlementpacks and
 * /api/organizations/{id}/entitlements call.
 */
public class OrganizationEntitlementpacksGetTest extends CleanTest {

    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlements = new JSONArray();
    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlementpacks = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates a service, an organization, entitlements and entitlementpacks and
     * puts them together to create full entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService"});
        Utility.Create.organization(new String[]{"testOrganization"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlement1", "testEntitlement2"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});
        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementToPack(2, 1);
        
        Utility.Link.entitlementpackToOrg(1, 1);
    }

    /**
     * GETs the entitlementpack.
     */
    @Test
    public void testOrganizationEntitlementpacksGetPacks() {
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                BasicCall.REST.GET,
                                null,
                                1, 1));
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals(1, jsonResponseArray.getJSONObject(0).getInt("entitlement_pack_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GETs the entitlements.
     */
    @Test
    public void testOrganizationEntitlementpacksGetEntitlements() {
        try {
            JSONAssert.assertEquals(
                    entitlements,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
