package sztaki.hexaa.apicalls.attributevalueprincipals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the DELETE method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsDeleteTest.class.getSimpleName()
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
				new String[] { "AttributevalueprincipalsDeleteTest_as1" },
				"user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueprincipalsDeleteTest_as1\" }, \"user\"); did not succeed");
		}
		attributevalueprincipals = Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsDeleteTest_pri_value1", attributespecs
						.getJSONObject(0).getInt("id"));
		if (attributevalueprincipals.length() < 1) {
			fail("Utility.Create.attributevalueprincipal( \"AttributevalueprincipalsDeleteTest_pri_value1\", attributespecs.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueprincipalsDeleteTest.class.getSimpleName());
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
	 * DELETEs the attributespec and checks that none exists.
	 */
	@Test
	public void testAttributevalueprincipalsDelete() {
		// The DELETE call.
		Utility.Remove.attributevalueprincipal(attributevalueprincipals
				.getJSONObject(0).getInt("id"));
		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.GET, null, attributevalueprincipals
						.getJSONObject(0).getInt("id"), 0);
		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
