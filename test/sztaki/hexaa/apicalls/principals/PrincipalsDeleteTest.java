package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the DELETE method on the /api/principals call.
 */
public class PrincipalsDeleteTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsDeleteTest.class.getSimpleName() + " ***");
	}

	/**
	 * Numbers the test principal, incremented after every delete.
	 */
	private static int p = 2;

	/**
	 * Creates one principal before each method.
	 */
	@Before
	public void setUpClass() {
		Utility.Create.principal("testPrincipal");
	}

	/**
	 * DELETE by fedid and check the status line.
	 */
	@Test
	public void testPrincipalsDeleteByFedid() {
		Utility.Remove.principal("testPrincipal");
		p++;

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
		Utility.Remove.principal(p);
		p++;

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
