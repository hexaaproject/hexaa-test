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
 *
 */
public class RolesPrincipalsSetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesPrincipalsSetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created roles.
	 */
	public static JSONArray roles = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	public static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created principals.
	 */
	public static JSONArray principals = new JSONArray();

	/**
	 * Creates an organization, two role, a service and a principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "RolesPrincipalsSetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"RolesPrincipalsSetTest_org1\" ); did not succeed");
		}

		roles = Utility.Create.role(
				new String[] { "RolesPrincipalsSetTest_role1",
						"RolesPrincipalsSetTest_role2" }, organizations
						.getJSONObject(0).getInt("id"));
		if (roles.length() < 2) {
			fail("Utility.Create.role( new String[] { \"RolesPrincipalsSetTest_role1\", \"RolesPrincipalsSetTest_role2\" }, organizations.getJSONObject(0).getInt(\"id\") ); did not succeed");
		}

		services = Utility.Create
				.service(new String[] { "RolesPrincipalsSetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service( new String[] { \"RolesPrincipalsSetTest_service1\" }); did not succeed");
		}

		principals = Utility.Create.principal(new String[] {
				"RolesPrincipalsSetTest_pri1", "RolesPrincipalsSetTest_pri2" });
		if (principals.length() < 2) {
			fail("Utility.Create.principal( new String[] { \"RolesPrincipalsSetTest_pri1\", \"RolesPrincipalsSetTest_pri2\" }); did not succeed");
		}

		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"),
				new int[] { principals.getJSONObject(0).getInt("id"),
						principals.getJSONObject(1).getInt("id") });
	}
	
	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesPrincipalsSetTest.class.getSimpleName());
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i)
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
	 * PUT the principal to a role as an array.
	 */
	@Test
	public void testRolesPrincipalsSet() {
		Utility.Link.principalToRoleSet(
				roles.getJSONObject(0).getInt("id"), new int[] {
						principals.getJSONObject(0).getInt("id"),
						principals.getJSONObject(1).getInt("id") });

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

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(principals.getJSONObject(0).getInt("id"), jsonResponse
					.getJSONObject(0).getInt("principal_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
