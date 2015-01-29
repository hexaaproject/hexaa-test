package sztaki.hexaa.apicalls.roles;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the role related calls and expecting not
 * found or empty answers.
 */
public class RolesIsEmptyTest extends IsEmptyTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + RolesIsEmptyTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * GET method tests.
	 */
	@Test
	public void testRolesIsEmptyGet() {
		expectingNotFound(Const.Api.ROLES_ID, BasicCall.REST.GET);
		expectingNotFound(Const.Api.ROLES_ID_ENTITLEMENTS, BasicCall.REST.GET);
		expectingNotFound(Const.Api.ROLES_ID_PRINCIPALS, BasicCall.REST.GET);
	}

	/**
	 * PUT method tests.
	 */
	@Test
	public void testRoleIsEmptyPut() {
		expectingNotFound(Const.Api.ROLES_ID, BasicCall.REST.PUT);
		expectingNotFound(Const.Api.ROLES_ID_ENTITLEMENTS_EID,
				BasicCall.REST.PUT);
		expectingNotFound(Const.Api.ROLES_ID_PRINCIPALS_PID, BasicCall.REST.PUT);
	}

	/**
	 * DELETE method tests.
	 */
	@Test
	public void testRoleIsEmptyDelete() {
		expectingNotFound(Const.Api.ROLES_ID, BasicCall.REST.DELETE);
		expectingNotFound(Const.Api.ROLES_ID_ENTITLEMENTS_EID,
				BasicCall.REST.DELETE);
		expectingNotFound(Const.Api.ROLES_ID_PRINCIPALS_PID,
				BasicCall.REST.DELETE);
	}

	/**
	 * PATCH method tests.
	 */
	@Test
	public void testRolesIsEmptyPatch() {
		expectingNotFound(Const.Api.ROLES_ID, BasicCall.REST.PATCH);
	}
}
