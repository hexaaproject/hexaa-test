package sztaki.hexaa.apicalls.services.entitlementpacks;

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
 * Tests the GET method on the /api/services/{id}/entitlementpacks/requests.
 */
public class ServicesEntitlementpacksRequestTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesEntitlementpacksRequestTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
	 * file and creates a service for each.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "ServicesEntitlementpacksRequestTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] { \"ServicesEntitlementpacksRequestTest_service1\" }); did not succeed");
		}

		organizations = Utility.Create
				.organization("ServicesEntitlementpacksRequestTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"ServicesEntitlementpacksRequestTest_org1\"); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), new String[] {
				"ServicesEntitlementpacksRequestTest_ep1",
				"ServicesEntitlementpacksRequestTest_ep2" });
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] { \"ServicesEntitlementpacksRequestTest_ep1\", \"ServicesEntitlementpacksRequestTest_ep2\" }); did not succeed");
		}

		Utility.Link.entitlementpackToOrgRequest(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesEntitlementpacksRequestTest.class.getSimpleName());
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
	 * Tests the call.
	 */
	@Test
	public void testServicesEntitlementpacksRequest() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ENTITLEMENTPACKS_REQUESTS,
					BasicCall.REST.GET, null,
					services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(organizations.getJSONObject(0).getInt("id"),
					jsonResponse.getJSONObject(0).get("organization_id"));
			assertEquals(entitlementpacks.getJSONObject(0).getInt("id"),
					jsonResponse.getJSONObject(0).get("entitlement_pack_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
