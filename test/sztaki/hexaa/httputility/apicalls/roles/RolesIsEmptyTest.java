package sztaki.hexaa.httputility.apicalls.roles;

import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the role related calls and expecting not
 * found or empty answers.
 */
public class RolesIsEmptyTest extends IsEmptyTest {

    /**
     * GET method tests.
     */
    @Test
    public void testRolesIsEmptyGet() {
        expectingNotFound(
                Const.Api.ROLES_ID,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ROLES_ID_ENTITLEMENTS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ROLES_ID_PRINCIPALS,
                BasicCall.REST.GET);
    }

    /**
     * PUT method tests.
     */
    @Test
    public void testRoleIsEmptyPut() {
        expectingNotFound(
                Const.Api.ROLES_ID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ROLES_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ROLES_ID_PRINCIPALS_PID,
                BasicCall.REST.PUT);
    }

    /**
     * DELETE method tests.
     */
    @Test
    public void testRoleIsEmptyDelete() {
        expectingNotFound(
                Const.Api.ROLES_ID,
                BasicCall.REST.DEL);
        expectingNotFound(
                Const.Api.ROLES_ID_ENTITLEMENTS_EID,
                BasicCall.REST.DEL);
        expectingNotFound(
                Const.Api.ROLES_ID_PRINCIPALS_PID,
                BasicCall.REST.DEL);
    }
}
