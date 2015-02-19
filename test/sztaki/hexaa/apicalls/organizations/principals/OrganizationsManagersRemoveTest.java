package sztaki.hexaa.apicalls.organizations.principals;

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
 * Tests the DELETE method on the /api/organizations/{id}/managers/{pid} call.
 */
public class OrganizationsManagersRemoveTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationsManagersRemoveTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created principals.
	 */
	public static JSONArray principals = new JSONArray();
	/**
	 * JSONArray to store the created principals.
	 */
	public static JSONObject admin = new JSONObject();

	/**
	 * Creates one organization and one principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("OrganizationsManagersRemoveTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"OrganizationsManagersRemoveTest_org1\"); did not succeed");
		}
		principals = Utility.Create
				.principal(new String[] { "OrganizationsManagersRemoveTest_pri1" });
		if (principals.length() < 1) {
			fail("Utility.Create.principal(new String[] {\"OrganizationsManagersRemoveTest_pri1\", \"OrganizationsManagersAddTest_pri2\" }); did not succeed");
		}

		Utility.Link.managerToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));

		try {
			admin =persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationsManagersRemoveTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.persistent.isAdmin = true;
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Tests the DELETE method.
	 */
	@Test
	public void testOrganizationManagersRemove() {
		Utility.Remove.managers(organizations.getJSONObject(0).getInt("id"), principals.getJSONObject(0).getInt("id"));

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
							Const.Api.ORGANIZATIONS_ID_MANAGERS,
							BasicCall.REST.GET, null, organizations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			JSONAssert.assertEquals(admin, jsonResponse.getJSONObject(0),
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
