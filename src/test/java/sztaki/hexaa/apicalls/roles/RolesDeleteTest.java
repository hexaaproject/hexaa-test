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
 * Tests the DELETE method on the /api/role/{id} call.
 */
public class RolesDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + RolesDeleteTest.class.getSimpleName()
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
				.organization(new String[] { "RolesDeleteTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] { \"RolesDeleteTest_org1\" }); did not succeed");
		}

		roles = Utility.Create.role(new String[] { "RolesDeleteTest_role1",
				"RolesDeleteTest_role2" }, organizations.getJSONObject(0)
				.getInt("id"));
		if (roles.length() < 2) {
			fail("Utility.Create.role(new String[] { \"RolesDeleteTest_role1\", \"RolesDeleteTest_role2\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesDeleteTest.class.getSimpleName());
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * DELETEs the first role and checks that only the second one exists.
	 */
	@Test
	public void testRolesDelete() {
		// The DELETE call.
		Utility.Remove.roles(roles.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

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
			JSONAssert.assertEquals(roles.getJSONObject(1),
					jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
