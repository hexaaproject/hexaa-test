package sztaki.hexaa.apicalls.services.organizations;

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
 * Tests the GET method on the /api/services/{id}/organization call.
 */
public class ServicesOrganizationsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesOrganizationsGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store services.
	 */
	public static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store organizations.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store entitlementpacks.
	 */
	public static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates two services an organization an entitlementpack and links the
	 * pack to the organization.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesOrganizationsGetTest_service1",
				"ServicesOrganizationsGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] { \"ServicesOrganizationsGetTest_service1\", \"ServicesOrganizationsGetTest_service2\" }); did not succeed");
		}

		organizations = Utility.Create
				.organization(new String[] { "ServicesOrganizationsGetTest_org1" });
		if (services.length() < 2) {
			fail("Utility.Create.organization(new String[] { \"ServicesOrganizationsGetTest_org1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				new String[] { "ServicesOrganizationsGetTest_ep1" });
		if (services.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] { \"ServicesOrganizationsGetTest_ep1\" }); did not succeed");
		}

		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] { entitlementpacks.getJSONObject(0)
				.getInt("id") });
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesOrganizationsGetTest.class.getSimpleName());
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
	 * GET the organization linked to the service.
	 */
	@Test
	public void testServicesOrganizationsGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ORGANIZATIONS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(1, jsonResponse.length());
			assertEquals(organizations.getJSONObject(0).getInt("id"),
					jsonResponse.getJSONObject(0).getInt("id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
