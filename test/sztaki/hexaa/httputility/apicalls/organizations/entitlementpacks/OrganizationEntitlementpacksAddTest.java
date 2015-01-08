package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/organizations/{id}/entitlementpacks/{epid},
 * /api/organizations/{id}/entitlementpacks/{epid}/accept and
 * /api/organizations/{id}/entitlementpack calls.
 */
public class OrganizationEntitlementpacksAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates a service, an organization and entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService"});
        Utility.Create.organization(new String[]{"testOrganization"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1", "testEntitlementpack2"});
    }

    /**
     * Tests the PUT method to add additional entitlementpacks to the
     * organization one by one.
     */
    @Test
    public void testOrganizationEntitlementpacksAdd() {
        Utility.Link.entitlementpackToOrg(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Tests the PUT method to set the entitlementpacks of the organization.
     */
    @Test
    public void testOrganizationEntitlementpacksAddByArray() {
        Utility.Link.entitlementpackToOrgByArray(1, new int[]{1, 2});

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
