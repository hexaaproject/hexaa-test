package sztaki.hexaa.apicalls.news;

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
 * Tests the GET method on the /api/services/{id}/news call.
 */
public class GetServicesNewsTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + GetServicesNewsTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates the necessary objects.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization("ServicesNewsTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"ServicesNewsTest_org1\"); did not succeed");
		}

		services = Utility.Create.service("ServicesNewsTest_service1");
		if (services.length() < 1) {
			fail("Utility.Create.service(\"ServicesNewsTest_service1\"); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), "ServicesNewsTest_ep1");
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpack(services.getJSONObject(0).getInt(\"id\"), \"ServicesNewsTest_ep1\"); did not succeed");
		}

		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ GetServicesNewsTest.class.getSimpleName());
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * Test the method.
	 */
	@Test
	public void testGetServicesNewsWithItems() {
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_NEWS, BasicCall.REST.GET, null,
					services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(3, jsonResponse.length());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Test the method.
	 */
	@Test
	public void testGetServicesNews() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_NEWS, BasicCall.REST.GET, null,
					services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(3, jsonResponse.length());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
