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
 * Tests the DELETE method on the /api/organizations/{id}/entitlementpacks and
 * /api/organizations/{id}/entitlements call.
 */
public class OrganizationEntitlementpacksGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationEntitlementpacksGetTest.class.getSimpleName()
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
	 * JSONArray to store the created entitlements.
	 */
	public static JSONArray entitlements = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates a service, an organization, entitlements and entitlementpacks and
	 * puts them together to create full entitlementpacks.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "OrganizationEntitlementpacksGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"OrganizationEntitlementpacksGetTest_service1\" }); did not succeed");
		}

		organizations = Utility.Create
				.organization(new String[] { "OrganizationEntitlementpacksGetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"OrganizationEntitlementpacksGetTest_org1\"}); did not succeed");
		}

		entitlements = Utility.Create.entitlements(services.getJSONObject(0).getInt("id"), new String[] {
				"OrganizationEntitlementpacksGetTest_entitlement1", "OrganizationEntitlementpacksGetTest_entitlement2" });
		if (entitlements.length() < 2) {
			fail("Utility.Create.attributespec(services.getJSONObject(0).getInt(\"id\"), new String[] {\"OrganizationEntitlementpacksGetTest_entitlement1\", \"OrganizationEntitlementpacksGetTest_entitlement2\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				new String[] { "OrganizationEntitlementpacksGetTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.attributespec(services.getJSONObject(0).getInt(\"id\"), new String[] {\"OrganizationEntitlementpacksGetTest_ep1\" }); did not succeed");
		}

		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(1).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));

		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationEntitlementpacksGetTest.class.getSimpleName());
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlement(entitlements.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i)
					.getInt("id"));
		}
	}
	
	/**
	 * GETs the entitlementpack.
	 */
	@Test
	public void testOrganizationEntitlementpacksGetPacks() {
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
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(entitlementpacks.getJSONObject(0).getInt("id"),
					jsonResponse.getJSONObject(0).getInt("entitlement_pack_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GETs the entitlements.
	 */
	@Test
	public void testOrganizationEntitlementpacksGetEntitlements() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
					BasicCall.REST.GET, null, organizations.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			JSONAssert.assertEquals(entitlements, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
