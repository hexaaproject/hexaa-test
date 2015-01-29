package sztaki.hexaa.apicalls.attributevalueprincipals;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the PUT method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsPutTest extends CleanTest {

	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray attributevalues = new JSONArray();

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsPutTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.attributespec(new String[] { "testName1" }, "user");
		attributevalues = Utility.Create
				.attributevalueprincipal("PriValue1", 1);
	}

	/**
	 * Puts the attributevalue than checks it.
	 */
	@Test
	public void testAttributevalueprincipalPut() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("services", new JSONArray());
		jsonTemp.put("value", "PriValueChanged");
		jsonTemp.put("attribute_spec", 1);

		persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.PUT, jsonTemp.toString());

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(AttributevalueprincipalsPutTest.class.getName())
					.log(Level.SEVERE, null, ex);
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
