package sztaki.hexaa.apicalls.attributevalueorganizations;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the DELETE method on the /api/attributevalueorganizations/{id} call.
 */
public class AttributevalueorganizationsDeleteTest extends CleanTest {

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
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("Org1");
		Utility.Create.attributespec(new String[] { "testName1" }, "user");
		Utility.Create.attributevalueorganization("OrgValue1", 1, 1);
	}

	/**
	 * DELETEs the attributespec and checks that none exists.
	 */
	@Test
	public void testAttributevalueorganizationsDelete() {
		// The DELETE call.
		Utility.Remove.attributevalueorganization(1);
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
