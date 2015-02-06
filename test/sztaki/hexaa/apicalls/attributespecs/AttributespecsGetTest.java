package sztaki.hexaa.apicalls.attributespecs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

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

//// TODO: UNECCESSARY CleanTest
/**
 * Tests the GET method on the /api/attributespecs and /api/attributespecs/{id}
 * calls.
 */
public class AttributespecsGetTest extends NormalTest {

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
				"AttributespecsGetTest_as1", "AttributespecsGetTest_as2" },
				"user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"AttributespecsGetTest_as1\", \"AttributespecsGetTest_as2\" }, \"user\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributespecsGetTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
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
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributespecs.getJSONObject(testedID),
					jsonResponse, JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the attributespecs array with call /app.php/api/attributespecs.
	 */
	@Test
	public void testAttributespecsGetArray() {
		// GET the array.
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTESPECS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(AttributespecsGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			JSONAssert.assertEquals(attributespecs, jsonResponse,
					JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
