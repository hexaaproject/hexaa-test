package sztaki.hexaa.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the PUT method on the
 * /api/organizations/{id}/entitlementpacks/{token}/token call.
 */
public class OrganizationEntitlementpacksTokenTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksTokenTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates a service, an organization and entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService"});
        Utility.Create.organization(new String[]{"testOrganization"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});
    }

    /**
     * Connects an entitlementpack to an organization with PUT using the token.
     */
    @Test
    public void testOrganizationEntitlementpacksPut() {
        Utility.Link.entitlementpackToOrgByToken(1, 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
            JSONArray jsonResponseArray
                    = (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                    BasicCall.REST.GET));
            assertEquals(
                    1,
                    jsonResponseArray.getJSONObject(0).getInt("entitlement_pack_id"));
            assertEquals(
                    "accepted",
                    jsonResponseArray.getJSONObject(0).getString("status"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
