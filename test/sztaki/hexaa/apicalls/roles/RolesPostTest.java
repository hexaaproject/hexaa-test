package sztaki.hexaa.apicalls.roles;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the POST method on the /api/organization/{id}/role call.
 */
public class RolesPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesPostTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one organization.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForRole1"});
    }

    /**
     * POSTs a new role.
     */
    @Test
    public void testRolesPost() {
        // Creates the JSON object
        Utility.Create.role("testRole", 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
