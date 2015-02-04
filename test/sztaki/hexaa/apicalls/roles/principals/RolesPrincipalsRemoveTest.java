package sztaki.hexaa.apicalls.roles.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the DELETE method on the /api/role/{id}/principals/{eid} call.
 */
public class RolesPrincipalsRemoveTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesPrincipalsRemoveTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store created principals.
	 */
	private static JSONArray principals = new JSONArray();
	/**
	 * JSONArray to store created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store created roles.
	 */
	private static JSONArray roles = new JSONArray();

	/**
	 * Creates one organization and one role for it than creates a principal and
	 * link it to the role.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("RolesPrincipalsRemoveTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"RolesPrincipalsRemoveTest_org1\"); did not succeed");
		}
		roles = Utility.Create.role("RolesPrincipalsRemoveTest_role1",
				organizations.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.role(\"RolesPrincipalsRemoveTest_role1\"); did not succeed");
		}
		principals = Utility.Create.principal("RolesPrincipalsRemoveTest_pri1");
		if (principals.length() < 1) {
			fail("Utility.Create.principal(\"RolesPrincipalsRemoveTest_pri1\"); did not succeed");
		}

		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));
		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				Const.HEXAA_ID);
		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				principals.getJSONObject(0).getInt("id"));
	}

	/**
	 * DELETE the principal from the local array and the server as well.
	 */
	@Test
	public void testRolesPrincipalRemove() {
		Utility.Remove.principalFromRole(roles.getJSONObject(0).getInt("id"),
				Const.HEXAA_ID);

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.ROLES_ID_PRINCIPALS, BasicCall.REST.GET, null,
					roles.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(1, jsonResponse.length());
			assertEquals(principals.getJSONObject(0).getInt("id"), jsonResponse
					.getJSONObject(0).getInt("id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
