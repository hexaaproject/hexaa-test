package sztaki.hexaa.apicalls.principals;

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
 * Test the GET method on the /api/member/organizations call.
 */
public class PrincipalsGetMemebersOrganizationsTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsGetMemebersOrganizationsTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();

	/**
	 * Creates one organization.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "PrincipalsGetMemebersOrganizationsTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"PrincipalsGetMemebersOrganizationsTest_org1\" ); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalsGetMemebersOrganizationsTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * GET the list of organizations where the user is a member.
	 */
	@Test
	public void testPrincipalGetMemberOrgWithItems() {
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.MEMBER_ORGANIZATIONS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the list of organizations where the user is a member.
	 */
	@Test
	public void testPrincipalGetMemberOrg() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.MEMBER_ORGANIZATIONS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
