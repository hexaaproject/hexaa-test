package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 * Tests the DELETE method on the
 * /api/organizations/{id}/entitlementpacks/{epid} call.
 */
public class OrganizationEntitlementpacksDelete extends OrganizationEntitlementpack {

    @Test
    public void testOrganizationEntitlementpacksDelete() {
        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[]{1});

        // Delete a pending link
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                BasicCall.REST.DEL,
                null,
                1, 1);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[]{1});
        // Accepts the pending link
        this.acceptPendingLink(1, new int[]{1});

        // Delete an accepted link
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                BasicCall.REST.DEL,
                null,
                1, 1);

    }
}
