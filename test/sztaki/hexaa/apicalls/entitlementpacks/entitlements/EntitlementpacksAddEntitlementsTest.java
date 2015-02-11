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
 * Tests the PUT methods on the /api/entitlementpacks/{id}/entitlements/{eid}
 * call.
 */
public class EntitlementpacksAddEntitlementsTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementpacksAddEntitlementsTest.class.getSimpleName()
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
				.service(new String[] { "EntitlementpacksAddEntitlementsTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service( new String[] {\"EntitlementpacksAddEntitlementsTest_service1\" }); did not succeed");
		}
		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), new String[] {
				"EntitlementpacksAddEntitlementsTest_ep1", "EntitlementpacksAddEntitlementsTest_ep2" });
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksDeleteTest_ep1\", \"EntitlementpacksDeleteTest_ep2\" }); did not succeed");
		}
		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"),
				new String[] { "EntitlementpacksAddEntitlementsTest_entitlement1", "EntitlementpacksAddEntitlementsTest_entitlement2" });
		if (entitlements.length() < 2) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksAddEntitlementsTest_entitlement1\", \"EntitlementpacksAddEntitlementsTest_entitlement2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ EntitlementpacksAddEntitlementsTest.class.getSimpleName());
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
	 * PUTs the first entitlement to the second pack as well, and verifies this
	 * by GETs.
	 */
	@Test
	public void testEntitlementpacksAddEntitlements() {
		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(1).getInt("id"));

		checkEntitlementInPacks(new int[] { entitlements.getJSONObject(0)
				.getInt("id") }, entitlementpacks.getJSONObject(1).getInt("id"));
	}

	/**
	 * Tests the PUT method on the /api/entitlementpacks/{id}/entitlement.
	 */
	@Test
	public void testEntitlementpacksAddEntitlementsByArray() {
		Utility.Link.entitlementToPackByArray(new int[] {
				entitlements.getJSONObject(0).getInt("id"),
				entitlements.getJSONObject(1).getInt("id") }, entitlementpacks
				.getJSONObject(0).getInt("id"));

		checkEntitlementInPacks(new int[] {
				entitlements.getJSONObject(0).getInt("id"),
				entitlements.getJSONObject(1).getInt("id") }, entitlementpacks
				.getJSONObject(0).getInt("id"));
	}

	/**
	 * Checks for the entitlements specified by the ids in the entitlementIds in
	 * the entitlementpack specified by the packId.
	 *
	 * @param entitlementIds
	 *            an int[] with the ids of the entitlements
	 * @param packId
	 *            an int with the id of the entitlementpack
	 */
	public void checkEntitlementInPacks(int[] entitlementIds, int packId) {
		JSONArray jsonEntitlementArray = new JSONArray();
		for (int i : entitlementIds) {
			try {
				jsonEntitlementArray.put(persistent.getResponseJSONObject(Const.Api.ENTITLEMENTS_ID,
								BasicCall.REST.GET, null, i, 0));
			} catch (ResponseTypeMismatchException ex) {
				fail(ex.getFullMessage());
				return;
			}
			
			try {
				assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			} catch (AssertionError e) {
				AssertErrorHandler(e);
			}
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS, BasicCall.REST.GET,
					null, packId, 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponseArray = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(jsonEntitlementArray, jsonResponseArray,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
