package sztaki.hexaa.httputility.apicalls.roles.principals;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/role/{id}/principal/{pid} call.
 */
public class RolesPrincipalsAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesPrincipalsAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates an organization, two role, a service and a principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.role(new String[]{"testRole1", "testRole2"}, 1);
        Utility.Create.service(new String[]{"testService1"});
        Utility.Create.principal(new String[]{"fedidTest1"});
    }

    /**
     * PUT the principal to a role with three different outcome.
     */
    @Test
    public void testRolesPrincipalsPut() {
        // PUT the first principal to the first role.
        persistent.call(
                Const.Api.ROLES_ID_PRINCIPALS_PID,
                BasicCall.REST.PUT,
                null,
                1, 2);
        // Bad Request because the principal is not a member of the organization.
        try {
            assertEquals(Const.StatusLine.BadRequest, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        Utility.Link.memberToOrganization(1, 2);
        // PUT the first principal to the first role again.
        persistent.call(
                Const.Api.ROLES_ID_PRINCIPALS_PID,
                BasicCall.REST.PUT,
                null,
                1, 2);
        // 201 because the principal is now a member and not yet part of the role.
        try {
            assertEquals(Const.StatusLine.Created, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        // PUT the first principal to the first role for the third time.
        persistent.call(
                Const.Api.ROLES_ID_PRINCIPALS_PID,
                BasicCall.REST.PUT,
                null,
                1, 2);
        // 204 No Content because the principal is already part of the role.
        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
