package sztaki.hexaa.apicalls.attributevalueprincipals;

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
 * Tests the PUT method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsPatchTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributevalueprincipals = new JSONArray();

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributevalueprincipalsPatchTest_as1" },
				"user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueprincipalsPatchTest_as1\" }, \"user\"); did not succeed");
		}
		attributevalueprincipals = Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsPatchTest_pri_value1", attributespecs
						.getJSONObject(0).getInt("id"));
		if (attributevalueprincipals.length() < 1) {
			fail("Utility.Create.attributevalueprincipal( \"AttributevalueprincipalsPatchTest_pri_value1\", attributespecs.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueprincipalsPatchTest.class.getSimpleName());
		for (int i = 0; i < attributevalueprincipals.length(); i++) {
			Utility.Remove.attributevalueprincipal(attributevalueprincipals
					.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
	}

	/**
	 * Puts the attributevalue than checks it.
	 */
	@Test
	public void testAttributevalueprincipalPatch() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("services", new JSONArray());
		jsonTemp.put("value",
				"AttributevalueprincipalsPatchTest_pri_value1_modified");

		persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.PATCH, jsonTemp.toString(),
				attributevalueprincipals.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID, BasicCall.REST.GET,
					null, attributevalueprincipals.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		jsonTemp.put("organization_id", jsonTemp.remove("organization"));
		jsonTemp.put("attribute_spec_id", jsonTemp.remove("attribute_spec"));
		jsonTemp.put("service_ids", jsonTemp.remove("services"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(jsonTemp, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
