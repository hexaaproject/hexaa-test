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
 * Tests the GET method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsGetTest extends CleanTest {

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
				+ AttributevalueprincipalsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.principal("Org1");
		Utility.Create.attributespec(new String[] { "testName1" }, "user");
		attributevalues = Utility.Create
				.attributevalueprincipal("OrgValue1", 1);
		System.out.println(Utility.persistent.getStatusLine());
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
					null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(AttributevalueprincipalsGetTest.class.getName())
					.log(Level.SEVERE, null, ex);
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
