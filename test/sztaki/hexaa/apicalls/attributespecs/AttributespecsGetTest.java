package sztaki.hexaa.apicalls.attributespecs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

//// TODO: UNECCESSARY CleanTest
/**
 * Tests the GET method on the /api/attributespecs and /api/attributespecs/{id}
 * calls.
 */
public class AttributespecsGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributespecsGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();

	/**
	 * Creates two attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		attributespecs = Utility.Create.attributespec(new String[] {
				"testName1", "differentTestName1" }, "user");
	}

	/**
	 * GET the attributespec object with call /api/attributespecs/{id}.
	 */
	@Test
	public void testAttributespecsGetByID() {
		int testedID = 1;
		// Get the attributespec.
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.GET, null,
					attributespecs.getJSONObject(testedID).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(AttributespecsGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(attributespecs.getJSONObject(testedID),
					jsonResponse, JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the attributespecs array with call /app.php/api/attributespecs.
	 */
	@Test
	public void testAttributespecsArray() {
		// GET the array.
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.ATTRIBUTESPECS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(AttributespecsGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(attributespecs, jsonResponse,
					JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
