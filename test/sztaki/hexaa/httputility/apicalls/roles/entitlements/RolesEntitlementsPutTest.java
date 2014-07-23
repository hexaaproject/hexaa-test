package sztaki.hexaa.httputility.apicalls.roles.entitlements;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class RolesEntitlementsPutTest extends CleanTest {

    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organizations(new String[]{"testOrg1"});
        Utility.Create.roles(new String[]{"testRole1", "testRole2"}, 1);
        Utility.Create.services(new String[]{"testService1"});
        Utility.Create.entitlements(1, new String[]{"testEntitlement1"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});
    }

    @Test
    public void testRolesEntitlementsPut() {
        persistent.call(Const.Api.ROLES_ID_ENTITLEMENTS_EID, BasicCall.REST.PUT, null, 1, 1);

        try {
            assertEquals("HTTP/1.1 400 Bad Request", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpacksToOrg(1, new int[]{1});

        persistent.call(Const.Api.ROLES_ID_ENTITLEMENTS_EID, BasicCall.REST.PUT, null, 1, 1);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        persistent.call(Const.Api.ROLES_ID_ENTITLEMENTS_EID, BasicCall.REST.PUT, null, 1, 1);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
