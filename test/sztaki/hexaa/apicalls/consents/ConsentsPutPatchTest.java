package sztaki.hexaa.apicalls.consents;

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
 * Tests the PUT and PATCH method on the /api/consents/{id} call.
 */
public class ConsentsPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ConsentsPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray consents = new JSONArray();

	/**
	 * Creates the necessary objects on the server to begin the tests.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ConsentsPutPatchTest_service1", "ConsentsPutPatchTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ConsentsPutPatchTest_service1\", \"ConsentsPutPatchTest_service1\" }); did not succeed");
		}

		attributespecs = Utility.Create.attributespec(new String[] {
				"ConsentsPutPatchTest_as1", "ConsentsPutPatchTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"ConsentsPutPatchTest_as1\", \"ConsentsPutPatchTest_as2\" }, \"user\"); did not succeed");
		}

		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), attributespecs
						.getJSONObject(0).getInt("id"), true);
		Utility.Link.attributespecsToService(
				services.getJSONObject(1).getInt("id"), attributespecs
						.getJSONObject(1).getInt("id"), true);
		consents = Utility.Create.consent(true, new int[] { attributespecs
				.getJSONObject(0).getInt("id") }, services.getJSONObject(0)
				.getInt("id"));
		if (consents.length() < 1) {
			fail("Utility.Create.consent(true, new int[] { attributespecs.getJSONObject(0).getInt(\"id\") }, services.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
		consents.put(Utility.Create.consent(true,
				new int[] { attributespecs.getJSONObject(1).getInt("id") },
				services.getJSONObject(1).getInt("id")).getJSONObject(0));
		if (consents.length() < 2) {
			fail("Utility.Create.consent(true, new int[] { attributespecs.getJSONObject(1).getInt(\"id\") }, services.getJSONObject(1).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ConsentsPutPatchTest.class.getSimpleName());
		for (int i = 0; i < consents.length(); i++) {
			Utility.Remove.consent(consents.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Changes the enable_entitlements key to false and verifies it by getting
	 * the consent.
	 */
	@Test
	public void testConsentsPut() {
		JSONObject json = new JSONObject();
		json.put("enable_entitlements", false);
		json.put("enabled_attribute_specs", new int[] { attributespecs.getJSONObject(0).getInt("id") });
		json.put("service", services.getJSONObject(0).getInt("id"));
		consents.getJSONObject(0).put("enable_entitlements", false);

		persistent.call(Const.Api.CONSENTS_ID, BasicCall.REST.PUT,
				json.toString(), consents.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.CONSENTS_ID, BasicCall.REST.GET, null, consents.getJSONObject(0).getInt("id"), 0);
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
				json.toString(), consents.getJSONObject(1).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.CONSENTS_ID, BasicCall.REST.GET, null, consents.getJSONObject(1).getInt("id"), 0);
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
