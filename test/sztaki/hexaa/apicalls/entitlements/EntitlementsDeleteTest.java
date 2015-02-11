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
 * Tests the DELETE method on the /api/entitlements/{id} call.
 */
public class EntitlementsDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementsDeleteTest.class.getSimpleName() + " ***");
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
				.service(new String[] { "EntitlementsDeleteTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service( new String[] {\"EntitlementsDeleteTest_service1\" }); did not succeed");
		}
		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"), new String[] {
				"EntitlementsDeleteTest_entitlement1",
				"EntitlementsDeleteTest_entitlement2" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"EntitlementsDeleteTest_entitlement1\", \"EntitlementsDeleteTest_entitlement2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ EntitlementsDeleteTest.class.getSimpleName());
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlement(entitlements.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * DELETEs the first entitlement and checks that only the second one exists.
	 */
	@Test
	public void testEntitlementsDelete() {
		Utility.Remove.entitlement(entitlements.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		
		persistent.call(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.GET,
				null, entitlements.getJSONObject(0).getInt("id"), 0);
		
		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.GET,
					null, entitlements.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		try {
				JSONAssert.assertEquals(entitlements.getJSONObject(1),
						jsonResponse, JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
