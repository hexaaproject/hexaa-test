package sztaki.hexaa.apicalls.services.entitlements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/services/{id}/entitlements call.
 */
public class ServicesEntitlementsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesEntitlementsGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store entitlements.
	 */
	private static JSONArray entitlements = new JSONArray();

	/**
	 * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
	 * file and creates a service for each.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesEntitlementsGetTest_service1",
				"ServicesEntitlementsGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] { \"ServicesntitlementpacksGetTest_service1\", \"ServicesEntitlementsGetTest_service2\" }); did not succeed");
		}
		entitlements = Utility.Create.entitlements(services
				.getJSONObject(0).getInt("id"), new String[] {
				"ServicesEntitlementsGetTest_ep1",
				"ServicesEntitlementsGetTest_ep2" });
		if (entitlements.length() < 2) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"ServicesEntitlementsGetTest_ep1\", \"ServicesEntitlementsGetTest_ep2\" }); did not succeed");
		}
		entitlements.put(Utility.Create.entitlements(
				services.getJSONObject(1).getInt("id"),
				new String[] { "ServicesEntitlementsGetTest_ep3" })
				.getJSONObject(0));
		if (entitlements.length() < 3) {
			fail("Utility.Create.entitlements(services.getJSONObject(1).getInt(\"id\"),	new String[] { \"ServicesEntitlementsGetTest_ep3\" }).getJSONObject(0)); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesEntitlementsGetTest.class.getSimpleName());
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlementpack(entitlements.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Calls GET /api/services/{id}/entitlements on the 2 services to get the 3
	 * entitlements created in the buildUp().
	 */
	@Test
	public void testServicesEntitlementsGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ENTITLEMENTS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		JSONArray jsonTemp = new JSONArray();
		jsonTemp.put(entitlements.getJSONObject(0));
		jsonTemp.put(entitlements.getJSONObject(1));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ENTITLEMENTS, BasicCall.REST.GET,
					null, services.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		jsonResponse = jsonItems.getJSONArray("items");

		jsonTemp = new JSONArray();
		jsonTemp.put(entitlements.getJSONObject(2));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
