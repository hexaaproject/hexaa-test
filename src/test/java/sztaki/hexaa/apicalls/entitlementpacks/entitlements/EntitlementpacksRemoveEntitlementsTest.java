package sztaki.hexaa.apicalls.entitlementpacks.entitlements;

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
 * Tests the DELETE methods on the /api/entitlementpacks/{id}/entitlements/{eid}
 * call.
 */
public class EntitlementpacksRemoveEntitlementsTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementpacksRemoveEntitlementsTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	public static JSONArray entitlementpacks = new JSONArray();
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
				.service(new String[] { "EntitlementpacksRemoveEntitlementsTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service( new String[] {\"EntitlementpacksRemoveEntitlementsTest_service1\" }); did not succeed");
		}
		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), new String[] {
				"EntitlementpacksRemoveEntitlementsTest_ep1",
				"EntitlementpacksRemoveEntitlementsTest_ep2" });
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksDeleteTest_ep1\", \"EntitlementpacksDeleteTest_ep2\" }); did not succeed");
		}
		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"), new String[] {
				"EntitlementpacksRemoveEntitlementsTest_entitlement1",
				"EntitlementpacksRemoveEntitlementsTest_entitlement2" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksRemoveEntitlementsTest_entitlement1\", \"EntitlementpacksRemoveEntitlementsTest_entitlement2\" }); did not succeed");
		}

		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(1).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(1).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ EntitlementpacksRemoveEntitlementsTest.class.getSimpleName());
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlement(entitlements.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Removes the first entitlement of both packs and verifies it with proper
	 * GETs.
	 */
	@Test
	public void testEntitlementpacksRemoveEntitlements() {
		Utility.Remove.entitlementFromPack(entitlementpacks.getJSONObject(0)
				.getInt("id"), entitlements.getJSONObject(0).getInt("id"));
		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		Utility.Remove.entitlementFromPack(entitlementpacks.getJSONObject(1)
				.getInt("id"), entitlements.getJSONObject(0).getInt("id"));
		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONArray jsonTemp = new JSONArray();
		jsonTemp.put(entitlements.getJSONObject(1));
		
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
					BasicCall.REST.GET, null, entitlementpacks.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			JSONAssert.assertEquals(jsonTemp, jsonResponse,
					JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
					BasicCall.REST.GET, null, entitlementpacks.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		try {
			assertEquals(0,jsonItems.getInt("item_number"));
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
