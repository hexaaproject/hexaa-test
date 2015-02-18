package sztaki.hexaa.apicalls.organizations.entitlementpacks;

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
 * Tests the DELETE method on the
 * /api/organizations/{id}/entitlementpacks/{epid} call.
 */
public class OrganizationEntitlementpacksUnlinkTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationEntitlementpacksUnlinkTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates a service, an organization and entitlementpacks.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "OrganizationEntitlementpacksUnlinkTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] {\"OrganizationEntitlementpacksUnlinkTest_service1\" }); did not succeed");
		}

		organizations = Utility.Create
				.organization(new String[] { "OrganizationEntitlementpacksUnlinkTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"OrganizationEntitlementpacksUnlinkTest_org1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacksPrivate(services
				.getJSONObject(0).getInt("id"),
				new String[] { "OrganizationEntitlementpacksUnlinkTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(new String[] {\"OrganizationEntitlementpacksUnlinkTest_ep1\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationEntitlementpacksUnlinkTest.class.getSimpleName());
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Creates a pending link and deletes it than creates an accepted link and
	 * deletes that also, than asserts it for an empty array on GET
	 * .../entitlementpacks to verify.
	 */
	@Test
	public void testOrganizationEntitlementpacksDelete() {
		Utility.Link.entitlementpackToOrgRequest(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));

		// Delete a pending link
		Utility.Remove.entitlementpackFromOrg(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));

		// Delete an accepted link
		Utility.Remove.entitlementpackFromOrg(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
					BasicCall.REST.GET, null, organizations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(0, jsonItems.getInt("item_number"));
			JSONAssert.assertEquals(new JSONArray(),jsonResponse, JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
