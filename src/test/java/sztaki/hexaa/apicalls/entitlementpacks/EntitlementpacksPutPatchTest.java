package sztaki.hexaa.apicalls.entitlementpacks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the PUT methods on the /api/entitlementpacks/{id} uri.
 */
public class EntitlementpacksPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementpacksPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates one service and two entitlementpacks.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "EntitlementpacksPutPatchTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] {\"EntitlementpacksPutPatchTest_service1\" }); did not succeed");
		}
		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), new String[] {
				"EntitlementpacksPutPatchTest_ep1",
				"EntitlementpacksPutPatchTest_ep2" });
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksPutPatchTest_ep1\", \"EntitlementpacksPutPatchTest_ep2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ EntitlementpacksPutPatchTest.class.getSimpleName());
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Changes one attribute of the entitlementpack by put and verifies it by
	 * GETing the entitlementpack from the server.
	 */
	@Test
	public void testEntitlementpacksPut() {
		JSONObject jsonTemp = new JSONObject();

		jsonTemp.put("name", "EntitlementpacksPutPatchTest_ep1_changedbyput");
		jsonTemp.put("description",
				entitlementpacks.getJSONObject(0).get("description"));
		jsonTemp.put("type", entitlementpacks.getJSONObject(0).get("type"));

		persistent.call(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.PUT,
				jsonTemp.toString(),
				entitlementpacks.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.GET, null,
					entitlementpacks.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(jsonTemp.get("name"), jsonResponse.get("name"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Changes one attribute of the entitlementpack by patch and verifies it by
	 * GETing the entitlementpack from the server.
	 */
	@Test
	public void testEntitlementpacksPatch() {

		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("name", "EntitlementpacksPutPatchTest_ep1_changedbypatch");

		persistent.call(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.PATCH,
				jsonTemp.toString(),
				entitlementpacks.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse = (JSONObject) JSONParser.parseJSON(persistent
				.call(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.GET, null,
						entitlementpacks.getJSONObject(0).getInt("id"), 0));
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(jsonTemp.get("name"), jsonResponse.get("name"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
