package sztaki.hexaa.apicalls.principals;

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
 * Tests the PUT and PATCH methods on /api/principals/{id}/id calls.
 */
public class PrincipalsPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store principals.
	 */
	public static JSONArray principals = new JSONArray();

	/**
	 * Creates one principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		principals = Utility.Create.principal("PrincipalsPutPatchTest_pri1");
		if (principals.length() < 1) {
			fail("Utility.Create.principal(\"PrincipalsPutPatchTest_pri1\"); did not succeed");
		}
	}

	// / Reverse the effects of the test case if possible, this is necessary for
	// the test to be NormalTest.
	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalsPutPatchTest.class.getSimpleName());
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Changes the test principals fedid by PUT.
	 */
	@Test
	public void testPrincipalsPut() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("fedid", "PrincipalsPutPatchTest_pri1_modified");
		jsonTemp.put("email", principals.getJSONObject(0).get("email"));
		jsonTemp.put("display_name",
				principals.getJSONObject(0).get("display_name"));
		principals.getJSONObject(0).put("fedid",
				"PrincipalsPutPatchTest_pri1_modified");

		persistent.isAdmin = true;
		persistent.call(Const.Api.PRINCIPALS_ID, BasicCall.REST.PUT,
				jsonTemp.toString(), principals.getJSONObject(0).getInt("id"),
				0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PRINCIPALS_ID_ID, BasicCall.REST.GET, null,
					principals.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(principals.getJSONObject(0), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Changes the test principals fedid by PATCH.
	 */
	@Test
	public void testPrincipalsPatch() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("fedid", "PrincipalsPutPatchTest_pri1_modified");
		principals.getJSONObject(0).put("fedid",
				"PrincipalsPutPatchTest_pri1_modified");

		persistent.isAdmin = true;
		persistent.call(Const.Api.PRINCIPALS_ID, BasicCall.REST.PATCH,
				jsonTemp.toString(), principals.getJSONObject(0).getInt("id"),
				2);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PRINCIPALS_ID_ID, BasicCall.REST.GET, null,
					principals.getJSONObject(0).getInt("id"), 2);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(principals.getJSONObject(0), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
