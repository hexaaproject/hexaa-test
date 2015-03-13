package sztaki.hexaa.apicalls.securitydomain;

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

public class SecuritydomainPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ SecuritydomainPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray domains = new JSONArray();

	/**
	 * Creates two attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		domains = Utility.Create
				.securitydomain("SecuritydomainsPutPatchTest_sd1",
						"otherMasterKey",
						"This is a security domain to test the capability of posting one.");
		if (domains.length() < 1) {
			fail("Utility.Create.securitydomain(\"SecuritydomainsPutPatchTest_sd1\", \"otherMasterKey\", \"This is a security domain to test the capability of posting one.\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ SecuritydomainPutPatchTest.class.getSimpleName());
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove
					.securitydomain(domains.getJSONObject(i).getInt("id"));
		}
	}

	@Test
	public void testSecuritydomainsPut() {
		JSONObject json = new JSONObject();
		json.put("name", "SecuritydomainsPutPatchTest_sd1_changedByPut");
		domains.getJSONObject(0).remove("name");
		domains.getJSONObject(0).put("name",
				"SecuritydomainsPutPatchTest_sd1_changedByPut");
		json.put("scoped_key", "otherMasterKey");
		json.put("description",
				domains.getJSONObject(0).getString("description"));

		persistent.setAdmin();
		persistent.call(Const.Api.SECURITYDOMAINS_ID, BasicCall.REST.PUT,
				json.toString(), domains.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			persistent.setAdmin();
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.SECURITYDOMAINS_ID, BasicCall.REST.GET, domains
							.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(domains.getJSONObject(0), jsonResponse,
					false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	@Test
	public void testSecuritydomainsPatch() {
		JSONObject json = new JSONObject();
		json.put("name", "SecuritydomainsPutPatchTest_sd1_changedByPut");
		domains.getJSONObject(0).remove("name");
		domains.getJSONObject(0).put("name",
				"SecuritydomainsPutPatchTest_sd1_changedByPut");

		persistent.setAdmin();
		persistent.call(Const.Api.SECURITYDOMAINS_ID, BasicCall.REST.PATCH,
				json.toString(), domains.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			persistent.setAdmin();
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.SECURITYDOMAINS_ID, BasicCall.REST.GET, domains
							.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(domains.getJSONObject(0), jsonResponse,
					false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
