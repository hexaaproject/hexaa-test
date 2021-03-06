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

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the PATCH method on the /api/attributespecs/{id} call.
 */
public class AttributespecsPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributespecsPatchTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();

	/**
	 * Creates one attributespec.
	 */
	@BeforeClass
	public static void setUpClass() {
		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributespecsPatchTest_as1" }, "user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributespecsPatchTest_as1\" }, \"user\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributespecsPatchTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
	}

	/**
	 * PATCH a changed attributespec and verify it.
	 */
	@Test
	public void testAttributespecsPatch() {
		// Change and PUT
		JSONObject json = new JSONObject();
		json.put("uri", "oidByPut");
		attributespecs.getJSONObject(0).put("uri", "oidByPut");

		persistent.setAdmin();
		persistent.call(Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.PATCH,
				json.toString(), attributespecs.getJSONObject(0).getInt("id"),
				0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// Verify
		JSONObject jsonResponse;
		try {
			persistent.setAdmin();
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.GET, null,
					attributespecs.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(AttributespecsPatchTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals("oidByPut", jsonResponse.get("uri"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
