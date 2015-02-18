package sztaki.hexaa.apicalls.organizations.entitlementpacks;

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
 * Tests the PUT method on the
 * /api/organizations/{id}/entitlementpacks/{epid}/accept call.
 */
public class OrganizationEntitlementpacksLinkRequestTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationEntitlementpacksLinkRequestTest.class
						.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	public static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created organizations.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	public static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates a service, an organization, entitlements and entitlementpacks and
	 * puts them together to create full entitlementpacks.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "OrganizationEntitlementpacksLinkRequestTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] { \"OrganizationEntitlementpacksLinkRequestTest_service1\" }); did not succeed");
		}

		organizations = Utility.Create
				.organization(new String[] { "OrganizationEntitlementpacksLinkRequestTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] { \"OrganizationEntitlementpacksLinkRequestTest_org1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create
				.entitlementpacks(
						services.getJSONObject(0).getInt("id"),
						new String[] { "OrganizationEntitlementpacksLinkRequestTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] { \"OrganizationEntitlementpacksLinkRequestTest_ep1\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationEntitlementpacksLinkRequestTest.class
						.getSimpleName());
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
	 * Creates a pending link between an organization and an entitlementpack.
	 */
	@Test
	public void testOrganizationEntitlementpacksLinkRequest() {
		Utility.Link.entitlementpackToOrgRequest(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
					BasicCall.REST.GET, null, organizations.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(entitlementpacks.getJSONObject(0).getInt("id"),
					jsonResponse.getJSONObject(0).get("entitlement_pack_id"));
			assertEquals("pending", jsonResponse.getJSONObject(0).get("status"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
