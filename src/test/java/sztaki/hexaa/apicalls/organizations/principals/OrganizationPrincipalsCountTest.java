package sztaki.hexaa.apicalls.organizations.principals;

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
 * Tests the GET method on the /api/organizations/{id}/member/count and
 * /api/organizations/{id}/manager/count calls.
 */
public class OrganizationPrincipalsCountTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationPrincipalsCountTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates an organization and principals and links them to be members and
	 * manager.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("OrganizationPrincipalsCountTest_org1");
		principals = Utility.Create.principal(new String[] {
				"OrganizationPrincipalsCountTest_pri1",
				"OrganizationPrincipalsCountTest_pri2" });

		Utility.Link.managerToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));
		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(1).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationPrincipalsCountTest.class.getSimpleName());
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * Tests the ../member/count.
	 */
	@Test
	public void testOrganizationMemberCount() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_MEMBER_COUNT,
					BasicCall.REST.GET, null, organizations.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(3, jsonResponse.get("count"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the ../manager/count.
	 */
	@Test
	public void testOrganizationManagerCount() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_MANAGER_COUNT,
					BasicCall.REST.GET, null, organizations.getJSONObject(0)
					.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(2, jsonResponse.get("count"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
