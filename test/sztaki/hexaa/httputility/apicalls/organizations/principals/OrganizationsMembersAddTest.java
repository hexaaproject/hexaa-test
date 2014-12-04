package sztaki.hexaa.httputility.apicalls.organizations.principals;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/organizations/{id}/members/{pid} and
 * /api/organizations/{id}/member calls.
 */
public class OrganizationsMembersAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationsMembersAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one organization and one principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");
        Utility.Create.principal(new String[]{"testPrincipal1", "testPrincipal2"});
    }

    /**
     * Tests the PUT method to link the principal to the organization as a
     * member.
     */
    @Test
    public void testOrganizationMemberAdd() {
        Utility.Link.memberToOrganization(1, 2);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Tests the PUT method on the /api/organizations/{id}/member call to link a
     * principal given by an array.
     */
    @Test
    public void testOrganizationMemberAddByArray() {
        Utility.Link.memberToOrganizationByArray(1, new int[]{3});

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
