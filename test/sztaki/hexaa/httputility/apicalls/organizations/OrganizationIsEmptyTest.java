package sztaki.hexaa.httputility.apicalls.organizations;

import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DEL methods on the /api/entitlementpacks/public ,
 * /api/entitlementpacks/{id} and /api/entitlementpacks/{id}/entitlements calls.
 */
public class OrganizationIsEmptyTest extends IsEmptyTest {

    /**
     * Tests several uris with GET calls to check the proper empty strings and
     * 404 errors.
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
                Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATIONS,
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
     * Tests several uris with PUT calls to check the proper empty strings and
     * 404 errors.
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
     * Tests several uris with DEL calls to check the proper empty strings and
     * 404 errors.
     */
    @Test
    public void testOrganizationIsEmptyDelete() {
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.DEL);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                BasicCall.REST.DEL);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
                BasicCall.REST.DEL);
        expectingNotFound(
                Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
                BasicCall.REST.DEL);
    }
}
