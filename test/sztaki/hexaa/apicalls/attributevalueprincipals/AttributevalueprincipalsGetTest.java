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
 * Tests the GET method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created principals.
	 */
	public static JSONArray principals = new JSONArray();
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
		principals = Utility.Create
				.principal("AttributevalueprincipalsGetTest_pri1");
		if (principals.length() < 1) {
			fail("Utility.Create.principal( \"AttributevalueprincipalsGetTest_pri1\" ); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributevalueprincipalsGetTest_as1" }, "user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueprincipalsGetTest_as1\" }, \"user\"); did not succeed");
		}
		attributevalues = Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsGetTest_org_value1", attributespecs
						.getJSONObject(0).getInt("id"));
		if (attributevalues.length() < 1) {
			fail("Utility.Create.attributevalueprincipal( \"AttributevalueprincipalsGetTest_org_value1\" ); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueprincipalsGetTest.class.getSimpleName());
		for (int i = 0; i < attributevalues.length(); i++) {
			Utility.Remove.attributevalueprincipal(attributevalues
					.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Gets the attributevalue and checks it to the stored one.
	 */
	@Test
	public void testAttributevalueprincipalGet() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID, BasicCall.REST.GET,
					null, attributevalues.getJSONObject(0).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONObject jsonTemp = attributevalues.getJSONObject(0);
		jsonTemp.put("principal_id", jsonTemp.remove("principal"));
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
