package sztaki.hexaa.apicalls.attributevalueorganizations;

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
 * Tests the DELETE method on the /api/attributevalueorganizations/{id} call.
 */
public class AttributevalueorganizationsDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueorganizationsDeleteTest.class.getSimpleName()
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
				.organization("AttributevalueorganizationsDeleteTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"AttributevalueorganizationsDeleteTest_org1\" ); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributevalueorganizationsDeleteTest_as1" },
				"manager");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsDeleteTest_as1\" }, \"user\"); did not succeed");
		}
		attributevalues = Utility.Create.attributevalueorganization(
				"AttributevalueorganizationsDeleteTest_org_value1",
				attributespecs.getJSONObject(0).getInt("id"), organizations
						.getJSONObject(0).getInt("id"));
		if (attributevalues.length() < 1) {
			fail("Utility.Create.attributevalueorganization(\"AttributevalueorganizationsDeleteTest_org_value1\", attributespecs.getJSONObject(0).getInt(\"id\"), organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueorganizationsDeleteTest.class.getSimpleName());
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
	 * DELETEs the attributespec and checks that none exists.
	 */
	@Test
	public void testAttributevalueorganizationsDelete() {
		// The DELETE call.
		Utility.Remove.attributevalueorganization(attributevalues
				.getJSONObject(0).getInt("id"));
		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		persistent.call(Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
				BasicCall.REST.GET);
		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
