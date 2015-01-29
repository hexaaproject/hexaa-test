package sztaki.hexaa.apicalls.consents;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the PUT and PATCH method on the /api/consents/{id} call.
 */
public class ConsentsPutPatchTest extends CleanTest {

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray consents = new JSONArray();

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ConsentsPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * Creates the necessary objects on the server to begin the tests.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.service("testService1");
		Utility.Create.attributespec("randomOID1", "user");
		Utility.Link.attributespecsToService(1, 1, true);
		consents = Utility.Create.consent(true, new int[] { 1 }, 1);

		Utility.Create.service("testService2");
		Utility.Create.attributespec("randomOID2", "user");
		Utility.Link.attributespecsToService(2, 2, true);
		consents.put(Utility.Create.consent(true, new int[] { 2 }, 2)
				.getJSONObject(0));
	}

	/**
	 * Changes the enable_entitlements key to false and verifies it by getting
	 * the consent.
	 */
	@Test
	public void testConsentsPut() {
		JSONObject json = new JSONObject();
		json.put("enable_entitlements", false);
		json.put("enabled_attribute_specs", new int[] { 1 });
		json.put("service", 1);
		consents.getJSONObject(0).put("enable_entitlements", false);

		persistent.call(Const.Api.CONSENTS_ID, BasicCall.REST.PUT,
				json.toString(), 1, 1);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.CONSENTS_ID, BasicCall.REST.GET, null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(consents.getJSONObject(0), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Changes the enable_entitlements key to false and verifies it by getting
	 * the consent.
	 */
	@Test
	public void testConsentsPatch() {
		JSONObject json = new JSONObject();
		json.put("enable_entitlements", false);
		consents.getJSONObject(1).put("enable_entitlements", false);

		persistent.call(Const.Api.CONSENTS_ID, BasicCall.REST.PATCH,
				json.toString(), 2, 2);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.CONSENTS_ID, BasicCall.REST.GET, null, 2, 2);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(consents.getJSONObject(1), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
