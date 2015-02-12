package sztaki.hexaa.apicalls.roles.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the PUT method on the /api/role/{id}/principal/{pid} call.
 */
public class RolesPrincipalsAddTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesPrincipalsAddTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created roles.
	 */
	private static JSONArray roles = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates an organization, two role, a service and a principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "RolesPrincipalsAddTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] { \"RolesPrincipalsAddTest_org1\" }); did not succeed");
		}

		roles = Utility.Create.role(
				new String[] { "RolesPrincipalsAddTest_role1",
						"RolesPrincipalsAddTest_role2" }, organizations
						.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.role(new String[] { \"RolesPrincipalsAddTest_role1\", \"RolesPrincipalsAddTest_role2\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}

		services = Utility.Create
				.service(new String[] { "RolesPrincipalsAddTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] { \"RolesPrincipalsAddTest_service1\" }); did not succeed");
		}

		principals = Utility.Create
				.principal(new String[] { "RolesPrincipalsAddTest_pri1" });
		if (principals.length() < 1) {
			fail("Utility.Create.principal(new String[] { \"RolesPrincipalsAddTest_pri1\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesPrincipalsAddTest.class.getSimpleName());
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * PUT the principal to a role with three different outcome.
	 */
	@Test
	public void testRolesPrincipalsPut() {
		// PUT the first principal to the first role.
		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				principals.getJSONObject(0).getInt("id"));
		// Bad Request because the principal is not a member of the
		// organization.
		try {
			assertEquals(Const.StatusLine.BadRequest,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));
		// PUT the first principal to the first role again.
		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				principals.getJSONObject(0).getInt("id"));
		// 201 because the principal is now a member and not yet part of the
		// role.
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		// PUT the first principal to the first role for the third time.
		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				principals.getJSONObject(0).getInt("id"));
		// 204 No Content because the principal is already part of the role.
		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
