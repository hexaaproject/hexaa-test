package sztaki.hexaa.apicalls.organizations;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the organization related calls and
 * expecting not found or empty answers.
 */
public class OrganizationIsEmptyTest extends IsEmptyTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationIsEmptyTest.class.getSimpleName() + " ***");
    }

    /**
     * GET method tests.
     */
    @Test
    public void testOrganizationIsEmptyGet() {
        expectingEmpty(
                Const.Api.ORGANIZATIONS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_MANAGERS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_MEMBERS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ROLES,
                BasicCall.REST.GET);
    }

    /**
     * PUT method tests.
     */
    @Test
    public void testOrganizationIsEmptyPut() {
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
                BasicCall.REST.PUT);
    }

    /**
     * DELETE method tests.
     */
    @Test
    public void testOrganizationIsEmptyDelete() {
        expectingNotFound(Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.DELETE);
        expectingNotFound(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                BasicCall.REST.DELETE);
        expectingNotFound(Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
                BasicCall.REST.DELETE);
        expectingNotFound(Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
                BasicCall.REST.DELETE);
    }
    
    /**
     * PATCH method tests.
     */
    @Test
    public void testOrganizationIsEmptyPatch() {
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.PATCH);
    }
}
