package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the POST method on the /app.php/api/principals call.
 */
public class PrincipalsPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + PrincipalsPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalsPostTest.class.getSimpleName());
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * POST a new principal and verify it.
	 */
	@Test
	public void testPrincipalsPost() {
		Utility.persistent.setAdmin();
		principals = Utility.Create.principal("PrincipalsPostTest_pri1");

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
