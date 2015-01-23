package sztaki.hexaa.apicalls.roles.entitlements;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the PUT method on the /api/role/{id}/entitlements/{eid} call.
 */
public class RolesEntitlementsAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesEntitlementsAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates an organization, two role, a service, an entitlement and an
 entitlementpack.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.role(new String[]{"testRole1", "testRole2"}, 1);
        Utility.Create.service(new String[]{"testService1"});
        Utility.Create.entitlements(1, new String[]{"testEntitlement1"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});
    }

    /**
     * PUT the entitlement to a role with three different outcome.
     */
    @Test
    public void testRolesEntitlementsAdd() {
        // PUT an entitlement from outside of organization.
        Utility.Link.entitlementsToRole(1, 1);

        try {
            assertEquals(Const.StatusLine.BadRequest, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // PUT entitlement to pack and pack to organization.
        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpackToOrg(1, new int[]{1});

        // PUT entitlement to role.
        Utility.Link.entitlementsToRole(1, 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // PUT same entitlement to role again.
        Utility.Link.entitlementsToRole(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
