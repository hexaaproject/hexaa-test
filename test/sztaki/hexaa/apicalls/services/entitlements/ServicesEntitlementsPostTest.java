package sztaki.hexaa.apicalls.services.entitlements;

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
 * Test for the POST /app.php/api/services/{id}/entitlements call.
 */
public class ServicesEntitlementsPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesEntitlementsPostTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store entitlements.
	 */
	private static JSONArray entitlements = new JSONArray();
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
				"ServicesEntitlementsPostTest_service1",
				"ServicesEntitlementsPostTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesEntitlementsPostTest_service1\", \"ServicesEntitlementsPostTest_service2\" }); did not succeed");
		}
	}
	
	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesEntitlementsPostTest.class.getSimpleName());
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlement(entitlements.getJSONObject(i)
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
	public void testServiceEntitlementsPosts() {
		// Creating the first entitlement object
		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"), "ServicesEntitlementsPostTest_entitlement1");
		// Checks the status line
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// Creating the second entitlement object
		entitlements.put(Utility.Create.entitlements(
				services.getJSONObject(0).getInt("id"),
				"ServicesEntitlementsPostTest_entitlement2").get(0));
		// Checks the status line
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;

		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ENTITLEMENTS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(entitlements.getJSONObject(0),
					jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
			JSONAssert.assertEquals(entitlements.getJSONObject(1),
					jsonResponse.getJSONObject(1), JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
