package sztaki.hexaa.apicalls.entitlements;

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
 * Tests the PUT method on the /api/entitlements/{id} call.
 */
public class EntitlementsPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementsPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created entitlements.
	 */
	public static JSONArray entitlements = new JSONArray();

	/**
	 * Creates a service, two entitlementpacks for it, and an entitlement for
	 * the first entitlementpack.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "EntitlementsPutPatchTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service( new String[] {\"EntitlementsPutPatchTest_service1\" }); did not succeed");
		}
		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"), new String[] {
				"EntitlementsPutPatchTest_entitlement1",
				"EntitlementsPutPatchTest_entitlement2" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementsPutPatchTest_entitlement1\", \"EntitlementsPutPatchTest_entitlement2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ EntitlementsPutPatchTest.class.getSimpleName());
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlement(entitlements.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Changes one attribute of the entitlement by put and verifies it by GETing
	 * the entitlement from the server.
	 */
	@Test
	public void testEntitlementsPut() {
		JSONObject jsonTemp = new JSONObject();

		jsonTemp.put("name",
				"EntitlementsPutPatchTest_entitlement1_changedbyput");
		jsonTemp.put("description",
				entitlements.getJSONObject(0).get("description"));
		jsonTemp.put("uri", entitlements.getJSONObject(0).get("uri"));

		persistent.call(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.PUT,
				jsonTemp.toString(),
				entitlements.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ENTITLEMENTS_ID, BasicCall.REST.GET, null,
					entitlements.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(jsonTemp, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Changes one attribute of the entitlement by patch and verifies it by
	 * GETing the entitlement from the server.
	 */
	@Test
	public void testEntitlementsPatch() {
		JSONObject temp = new JSONObject();
		temp.put("name", "EntitlementsPutPatchTest_entitlement1_changedbypatch");

		persistent.call(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.PATCH,
				temp.toString(), entitlements.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ENTITLEMENTS_ID, BasicCall.REST.GET, null,
					entitlements.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(temp.getString("name"), jsonResponse.getString("name"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
