package sztaki.hexaa.apicalls.services.entitlementpacks;

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
 * Tests the POST method on the /api/services/{id}/entitlementpacks call.
 */
public class ServicesEntitlementpacksPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesEntitlementpacksPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store entitlementpacks.
	 */
	private static JSONArray entitlemenetpacks = new JSONArray();
	/**
	 * JSONArray to store services.
	 */
	private static JSONArray services = new JSONArray();

	/**
	 * Creates two services.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesEntitlementpacksPostTest_service1",
				"ServicesEntitlementpacksPostTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesEntitlementpacksPostTest_service1\", \"ServicesEntitlementpacksPostTest_service2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesEntitlementpacksPostTest.class.getSimpleName());
		for (int i = 0; i < entitlemenetpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlemenetpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * POSTs 2 different entitlement over one of the services and verifies them
	 * by GETting them on /api/services/{id}/entitlements.
	 */
	@Test
	public void testServiceEntitlementpacksPosts() {
		// Creating the first entitlement object
		entitlemenetpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				"ServicesEntitlementpacksPostTest_pack1");
		// Checks the status line
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// Creating the second entitlement object
		entitlemenetpacks.put(Utility.Create.entitlementpacks(
				services.getJSONObject(0).getInt("id"),
				"ServicesEntitlementpacksPostTest_pack2").get(0));
		// Checks the status line
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;

		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ENTITLEMENTPACKS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(entitlemenetpacks.getJSONObject(0),
					jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
			JSONAssert.assertEquals(entitlemenetpacks.getJSONObject(1),
					jsonResponse.getJSONObject(1), JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

}
