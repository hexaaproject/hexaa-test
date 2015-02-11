package sztaki.hexaa.apicalls.entitlementpacks;

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
 * Tests the GET methods on the /api/entitlementpacks/public and
 * /api/entitlementpacks/{id} calls.
 */
public class EntitlementpacksGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementpacksGetTest.class.getSimpleName() + " ***");
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
				.service(new String[] { "EntitlementpacksGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service( new String[] {\"EntitlementpacksGetTest_service1\" }); did not succeed");
		}
		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), new String[] {
				"EntitlementpacksGetTest_ep1", "EntitlementpacksGetTest_ep2" });
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksDeleteTest_ep1\", \"EntitlementpacksDeleteTest_ep2\" }); did not succeed");
		}
		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"),
				new String[] { "EntitlementpacksGetTest_entitlement1" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementpacksGetTest_entitlement1\" }); did not succeed");
		}

		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ EntitlementpacksGetTest.class.getSimpleName());
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
	 * Tests the GET method on /api/entitlementpacks/public call.
	 */
	@Test
	public void testEntitlementpacksPublicGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ENTITLEMENTPACKS_PUBLIC, BasicCall.REST.GET,
					null, entitlementpacks.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponseArray = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert
					.assertEquals(entitlementpacks.getJSONObject(0),
							jsonResponseArray.getJSONObject(0),
							JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the GET method on /api/entitlementpacks/{id}.
	 */
	@Test
	public void testEntitlementpacksGetByID() {
		for (int i = 0; i < 2; i++) {

			JSONObject jsonResponse;
			try {
				jsonResponse = persistent
						.getResponseJSONObject(Const.Api.ENTITLEMENTPACKS_ID,
								BasicCall.REST.GET, null, entitlementpacks
										.getJSONObject(i).getInt("id"), 0);
			} catch (ResponseTypeMismatchException ex) {
				fail(ex.getFullMessage());
				return;
			}

			try {
				assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
				JSONAssert.assertEquals(entitlementpacks.getJSONObject(i),
						jsonResponse, JSONCompareMode.LENIENT);
			} catch (AssertionError e) {
				AssertErrorHandler(e);
			}
		}
	}
}
