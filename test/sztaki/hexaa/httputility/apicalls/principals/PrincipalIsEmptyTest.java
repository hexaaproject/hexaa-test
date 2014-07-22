package sztaki.hexaa.httputility.apicalls.principals;

import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the empty database for the GET calls on the /api/manager/organizations,
 * /api/manager/services, /api/member/organizations, /api/principal,
 * /api/principal/attributespecs,
 * /api/principals/{asid}/attributespecs/attributevalueprincipals,
 * /api/principal/attributevalueprincipal, /api/principal/emailinvitations,
 * /api/principal/urlinvitations, /api/principals and /api/principals/{id}/id
 * uris.
 */
public class PrincipalIsEmptyTest extends IsEmptyTest {

    /**
     * Tests for empty strings and 404 errors.
     */
    @Test
    public void testPrincipalisEmpty() {
        expectingEmpty(
                Const.Api.MANAGER_ORGANIZATIONS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.MANAGER_SERVICES,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.MEMBER_ORGANIZATIONS,
                BasicCall.REST.GET);
        expectingFedid(
                Const.Api.PRINCIPAL,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_EMAILINVITATIONS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ENTITLEMENTS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ROLES,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_URLINVITATIONS,
                BasicCall.REST.GET);
        expectingFedid(
                Const.Api.PRINCIPALS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS,
                BasicCall.REST.GET);
        expectingFedid(
                Const.Api.PRINCIPALS_ID,
                BasicCall.REST.GET);
    }
}
