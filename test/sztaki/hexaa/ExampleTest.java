package sztaki.hexaa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.apicalls.attributespecs.AttributespecsGetTest;

/**
 * This class is an example for best practices using the hexaa-test libraries
 * and test cases.
 */
public class ExampleTest extends NormalTest {

	// / Be sure to use a class information @BeforeClass method to display the
	// starting test case.
	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributespecsGetTest.class.getSimpleName() + " ***");
	}

	// / Save all the possible changes to one or more array if the test case
	// requires it, or the test case is not too complicated to be reversible
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();

	// / Create everything that's only impact on the specific test case is if it
	// fails. Generally use the Utility methods if possible. Whenever you can
	// make
	// sure that you fail the test cases in this phase, it's mostly possible by
	// checking the result of the return values, but do not use assertions in
	// this method.
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

	// / Reverse the effects of the test case if possible, this is necessary for
	// the test to be NormalTest.
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

	// / Use simple test cases for unit tests.
	/**
	 * GET the attributespec object with call /api/attributespecs/{id}.
	 */
	@Test
	public void testAttributespecsGetByID() {
		int testedID = 1;
		// / make sure not to use json parsers in test cases, as they can
		// produce many errors, use the implemented getResponseJSONObject and
		// getResponseJSONArray methods whenever its possible
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.GET, null,
					attributespecs.getJSONObject(testedID).getInt("id"), 0);
			// / make sure to catch the ResponseTypeMismatchException
			// exceptions, as these are clear fails. You can generate an easy to
			// read fail message from the exception.getFullMessage. You should
			// stop the run of the test method with return as well.
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		// / Use the proper JSONAssert methods in JSONCompareMode.LENIENT as the
		// server may return additional information. Only use the strict methods
		// if you are sure of the servers response. Also check for status lines,
		// as these can also indicate fails easier than the jsonasserts.
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributespecs.getJSONObject(testedID),
					jsonResponse, JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

}
