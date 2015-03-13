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
 * Tests the GET method on the /api/organizations/{id}/role and /api/roles/{id}
 * calls.
 */
public class RolesGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + RolesGetTest.class.getSimpleName()
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
				.organization(new String[] { "RolesGetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] { \"RolesGetTest_org1\" }); did not succeed");
		}

		roles = Utility.Create.role(new String[] { "RolesGetTest_role1",
				"RolesGetTest_role2" },
				organizations.getJSONObject(0).getInt("id"));
		if (roles.length() < 2) {
			fail("Utility.Create.role(new String[] { \"RolesGetTest_role1\", \"RolesGetTest_role2\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesGetTest.class.getSimpleName());
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * GET the role in two way.
	 */
	@Test
	public void testRolesGetWithItems() {
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ROLES, BasicCall.REST.GET, null,
					organizations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(roles, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonObjectResponse;
		try {
			jsonObjectResponse = persistent.getResponseJSONObject(
					Const.Api.ROLES_ID, BasicCall.REST.GET, null, roles
							.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(roles.getJSONObject(0), jsonObjectResponse,
					JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the role in two way.
	 */
	@Test
	public void testRolesGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.ORGANIZATIONS_ID_ROLES, BasicCall.REST.GET, null,
					organizations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(roles, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
