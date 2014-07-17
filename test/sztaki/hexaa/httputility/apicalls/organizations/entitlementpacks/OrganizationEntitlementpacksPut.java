package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.junit.Test;

/**
 * Tests the PUT method on the /api/organizations/{id}/entitlementpacks/{epid}
 * call.
 */
public class OrganizationEntitlementpacksPut extends OrganizationEntitlementpack {

    /**
     * Connects an entitlementpack to an organization with PUT and verifies its
     * pending status.
     */
    @Test
    public void testOrganizationEntitlementpacksPut() {
        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[] {1});
    }
}
