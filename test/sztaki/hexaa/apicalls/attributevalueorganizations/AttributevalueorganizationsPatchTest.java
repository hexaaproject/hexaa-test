package sztaki.hexaa.apicalls.attributevalueorganizations;

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
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the PUT method on the /api/attributevalueorganizations/{id} call.
 */
public class AttributevalueorganizationsPatchTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueorganizationsPatchTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	public static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray attributevalues = new JSONArray();

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("AttributevalueorganizationsPatchTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"AttributevalueorganizationsPatchTest_org1\" ); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributevalueorganizationsPatchTest_as1" },
				"manager");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsPatchTest_as1\" }, \"manager\"); did not succeed");
		}
		attributevalues = Utility.Create.attributevalueorganization(
				"AttributevalueorganizationsPatchTest_org_value1",
				attributespecs.getJSONObject(0).getInt("id"), organizations
						.getJSONObject(0).getInt("id"));
		if (attributevalues.length() < 1) {
			fail("Utility.Create.attributevalueorganization(\"AttributevalueorganizationsPatchTest_org_value1\", attributespecs.getJSONObject(0).getInt(\"id\"), organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueorganizationsPutTest.class.getSimpleName());
		for (int i = 0; i < attributevalues.length(); i++) {
			Utility.Remove.attributevalueorganization(attributevalues
					.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * Puts the attributevalue than checks it.
	 */
	@Test
	public void testAttributevalueorganizationPatch() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("services", new JSONArray());
		jsonTemp.put("value", "OrgValueChanged");

		persistent.call(Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
				BasicCall.REST.PATCH, jsonTemp.toString(), attributevalues
						.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
					BasicCall.REST.GET, null, attributevalues
					.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		jsonTemp.put("organization_id", jsonTemp.remove("organization"));
		jsonTemp.put("attribute_spec_id", jsonTemp.remove("attribute_spec"));
		jsonTemp.put("service_ids", jsonTemp.remove("services"));
		jsonTemp.put("organization_id", jsonTemp.remove("organization"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(jsonTemp, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
