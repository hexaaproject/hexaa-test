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
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/attributevalueorganizations/{id} call.
 */
public class AttributevalueorganizationsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueorganizationsGetTest.class.getSimpleName()
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
				.organization("AttributevalueorganizationsGetTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"AttributevalueorganizationsGetTest_org1\" ); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributevalueorganizationsGetTest_as1" },
				"manager");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsGetTest_as1\" }, \"user\"); did not succeed");
		}
		attributevalues = Utility.Create.attributevalueorganization(
				"AttributevalueorganizationsGetTest_org_value1", attributespecs
						.getJSONObject(0).getInt("id"), organizations
						.getJSONObject(0).getInt("id"));
		if (attributevalues.length() < 1) {
			fail("Utility.Create.attributevalueorganization(\"AttributevalueorganizationsGetTest_org_value1\", attributespecs.getJSONObject(0).getInt(\"id\"), organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueorganizationsGetTest.class.getSimpleName());
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
	 * Gets the attributevalue and checks it to the stored one.
	 */
	@Test
	public void testAttributevalueorganizationGet() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
					BasicCall.REST.GET, null, attributevalues.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONObject jsonTemp = attributevalues.getJSONObject(0);
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
