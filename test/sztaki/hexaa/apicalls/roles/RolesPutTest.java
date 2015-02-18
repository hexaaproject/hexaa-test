package sztaki.hexaa.apicalls.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the PUT method on the /api/role/{id} call.
 */
public class RolesPutTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + RolesPutTest.class.getSimpleName()
				+ " ***");
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
	 * Creates one organization and two role.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "RolesPutTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"RolesPutTest_org1\" ); did not succeed");
		}

		roles = Utility.Create.role(new String[] { "RolesPutTest_role1",
				"RolesPutTest_role2" },
				organizations.getJSONObject(0).getInt("id"));
		if (roles.length() < 2) {
			fail("Utility.Create.role( new String[] { \"RolesPutTest_role1\", \"RolesPutTest_role2\" }, organizations.getJSONObject(0).getInt(\"id\") ); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesPutTest.class.getSimpleName());
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * PUTs the first role and checks that only the first one was modified and
	 * the second one is the original.
	 */
	@Test
	public void testRolesPut() {
		// Modify the first role
		JSONObject json = new JSONObject();
		json.put("name", "RolesPutTest_role1_modified");

		persistent.call(Const.Api.ROLES_ID, BasicCall.REST.PUT,
				json.toString(), roles.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ROLES, BasicCall.REST.GET, null,
					organizations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			JSONAssert.assertNotEquals(roles, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
