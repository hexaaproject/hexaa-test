package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.junit.Test;

/**
 * Tests the PUT method on the
 * /api/organizations/{id}/entitlementpacks/{epid}/accept call.
 */
public class OrganizationEntitlementpacksAcceptTest extends OrganizationEntitlementpack {

    /**
     * Creates a pending link between an organization and an entitlementpack and
     * accepts it.
     */
    @Test
    public void testOrganizationEntitlementpacksAccept() {
        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[]{1});
        // Accepts the pending link
        acceptPendingLink(1, new int[]{1});
    }
}
