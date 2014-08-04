package sztaki.hexaa.httputility.apicalls.roles.entitlements;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/roles/{id}/entitlements/{eid} call.
 */
public class RolesEntitlementsPutTest extends CleanTest {

    /**
     * Creates an organization, two roles, a service, an entitlement and an
     * entitlementpack.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.roles(new String[]{"testRole1", "testRole2"}, 1);
        Utility.Create.services(new String[]{"testService1"});
        Utility.Create.entitlements(1, new String[]{"testEntitlement1"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});
    }

    /**
     * PUT the entitlement to a role with three different outcome.
     */
    @Test
    public void testRolesEntitlementsPut() {
        // PUT an entitlement from outside of organization.
        persistent.call(
                Const.Api.ROLES_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT);

        try {
            assertEquals("HTTP/1.1 400 Bad Request", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // PUT entitlement to pack and pack to organization.
        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpacksToOrg(1, new int[]{1});

        // PUT entitlement to role.
        persistent.call(
                Const.Api.ROLES_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // PUT same entitlement to role again.
        persistent.call(
                Const.Api.ROLES_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
