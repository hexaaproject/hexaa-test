package sztaki.hexaa.apicalls.attributespecs;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

// TODO: UNECCESSARY CleanTest
/**
 * Tests the DELETE method on the /api/attributespecs/{id} call.
 */
public class AttributespecsDeleteTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributespecsDeleteTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		attributespecs = Utility.Create.attributespec(
				new String[] { "testName1" }, "user");
	}

	/**
	 * DELETEs the attributespec and checks that none exists.
	 */
	@Test
	public void testAttributespecsDelete() {
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[]{\"testName1\"}, \"user\") did not succeed");
		}
		// The DELETE call.
		Utility.Remove.attributespec(attributespecs.getJSONObject(0).getInt(
				"id"));
		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		persistent.call(Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.GET, null,
				attributespecs.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
