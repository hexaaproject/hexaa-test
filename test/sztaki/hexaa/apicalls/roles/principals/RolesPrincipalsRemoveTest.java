package sztaki.hexaa.apicalls.roles.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the DELETE method on the /api/role/{id}/principals/{eid} call.
 */
public class RolesPrincipalsRemoveTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesPrincipalsRemoveTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store created roles.
	 */
	private static JSONArray roles = new JSONArray();
	/**
	 * JSONArray to store created principals.
	 */
	private static JSONArray principals = new JSONArray();

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
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesPrincipalsRemoveTest.class.getSimpleName());
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
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

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ROLES_ID_PRINCIPALS, BasicCall.REST.GET, null,
					roles.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(1, jsonResponse.length());
			assertEquals(principals.getJSONObject(0).getInt("id"), jsonResponse
					.getJSONObject(0).getInt("principal_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
