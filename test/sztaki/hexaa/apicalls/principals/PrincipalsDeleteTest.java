package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Test the DELETE method on the /api/principals call.
 */
public class PrincipalsDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsDeleteTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates one principal before each method.
	 */
	@Before
	public void setUpMethod() {
		principals = Utility.Create.principal("PrincipalsDeleteTest_pri_respawn");
	}

	/**
	 * DELETE by fedid and check the status line.
	 */
	@Test
	public void testPrincipalsDeleteByFedid() {
		Utility.Remove.principal(principals.getJSONObject(0).getString("fedid"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * DELETE by id and check the status line.
	 */
	@Test
	public void testPrincipalsDeleteById() {
		Utility.Remove.principal(principals.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
