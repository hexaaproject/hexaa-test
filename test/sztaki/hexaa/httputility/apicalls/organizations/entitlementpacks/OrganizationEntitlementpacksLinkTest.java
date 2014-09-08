package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the PUT method on the /api/organizations/{id}/entitlementpacks/{epid}
 * call.
 */
public class OrganizationEntitlementpacksLinkTest extends OrganizationEntitlementpack {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksLinkTest.class.getSimpleName() + " ***");
    }

    /**
     * Connects an entitlementpack to an organization with PUT and verifies its
     * pending status.
     */
    @Test
    public void testOrganizationEntitlementpacksLinkRequest() {
        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[]{1});
    }
    
    /**
     * Connects an entitlementpack to an organization with PUT and verifies its
     * pending status.
     */
    @Test
    public void testOrganizationEntitlementpacksLinkAccept() {
        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.acceptPendingLink(1, new int[]{1});
    }
}