package sztaki.hexaa.httputility.apicalls.organizations.principals;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/organizations/{id}/managers/{pid} call.
 */
public class OrganizationsManagersAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationsManagersAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one organization and one principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");
        Utility.Create.principal("testPrincipal");
    }

    /**
     * Tests the PUT method to link the principal to the organization as a
     * manager.
     */
    @Test
    public void testOrganizationManagerPut() {
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
                BasicCall.REST.PUT,
                null,
                1, 2);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
