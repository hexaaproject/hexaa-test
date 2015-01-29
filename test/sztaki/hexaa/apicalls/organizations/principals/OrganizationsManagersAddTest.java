package sztaki.hexaa.apicalls.organizations.principals;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the PUT method on the /api/organizations/{id}/managers/{pid} and
 * /api/organizations/{id}/manager calls.
 */
public class OrganizationsManagersAddTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationsManagersAddTest.class.getSimpleName() + " ***");
	}

	/**
	 * Creates one organization and one principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("testOrg");
		Utility.Create.principal(new String[] { "testPrincipal1",
				"testPrincipal2" });
	}

	/**
	 * Tests the PUT method to link the principal to the organization as a
	 * manager.
	 */
	@Test
	public void testOrganizationManagersAdd() {
		Utility.Link.managerToOrganization(1, 2);
		System.out.println(Utility.persistent.getResponse());

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the PUT method on the /api/organizations/{id}/manager call to link
	 * a principal given by an array.
	 */
	@Test
	public void testOrganizationManagerAddByArray() {
		Utility.Link.managerToOrganizationByArray(1, new int[] { 3 });
		System.out.println(Utility.persistent.getResponse());

		try {
			assertEquals(Const.StatusLine.BadRequest,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		Utility.Link.memberToOrganization(1, new int[] { 3 });

		Utility.Link.managerToOrganizationByArray(1, new int[] { 3 });
		System.out.println(Utility.persistent.getResponse());

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
