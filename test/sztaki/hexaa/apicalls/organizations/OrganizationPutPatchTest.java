package sztaki.hexaa.apicalls.organizations;

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
 * Tests the PUT method on the /api/organization/{id} call.
 */
public class OrganizationPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationPutPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	public static JSONArray organizations = new JSONArray();

	/**
	 * Creates 2 organizations.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization(new String[] {
				"OrganizationPutPatchTest_org1",
				"OrganizationPutPatchTest_org2,", });
		if (organizations.length() < 2) {
			fail("Utility.Create.organization(new String[] {\"OrganizationPutPatchTest_org1\", \"OrganizationPutPatchTest_org2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationPutPatchTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * Modifies one of the two existing organization by put and verifies the
	 * change of its name, and the unchanged second one as well.
	 */
	@Test
	public void testOrganizationPut() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("name", organizations.getJSONObject(0).getString("name")
				+ "_modifiedbyput");

		persistent.call(Const.Api.ORGANIZATIONS_ID, BasicCall.REST.PUT,
				jsonTemp.toString(), organizations.getJSONObject(0)
						.getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET, null,
					organizations.getJSONObject(0).getInt("id"), 0);
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
		
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET, null,
					organizations.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations.getJSONObject(1), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Modifies one of the two existing organization by patch and verifies the
	 * change of its name, and the unchanged second one as well.
	 */
	@Test
	public void testOrganizationPatch() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("name", organizations.getJSONObject(0).getString("name")
				+ "_modifiedbypatch");

		persistent.call(Const.Api.ORGANIZATIONS_ID, BasicCall.REST.PATCH,
				jsonTemp.toString(), organizations.getJSONObject(0)
				.getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET, null,
					organizations.getJSONObject(0).getInt("id"), 0);
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
		
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET, null,
					organizations.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations.getJSONObject(1), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
