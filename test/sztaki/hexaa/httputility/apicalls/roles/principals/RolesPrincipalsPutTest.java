package sztaki.hexaa.httputility.apicalls.roles.principals;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/roles/{id}/principals/{pid} call.
 */
public class RolesPrincipalsPutTest extends CleanTest {

    /**
     * Creates an organization, two roles, a service and a principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organizations(new String[]{"testOrg1"});
        Utility.Create.roles(new String[]{"testRole1", "testRole2"}, 1);
        Utility.Create.services(new String[]{"testService1"});
        Utility.Create.principals(new String[]{"fedidTest1"});
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

        try {
            assertEquals("HTTP/1.1 400 Bad Request", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        
        

    }
}
