package sztaki.hexaa.apicalls.consents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the POST method on the /api/consents call.
 */
public class ConsentsPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ConsentsPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray consents = new JSONArray();

	/**
	 * Creates the necessary objects on the server to begin the tests.
	 */
	@BeforeClass
	public static void setUpClass() {services = Utility.Create.service(new String[] {
			"ConsentsPostTest_service1" });
	if (services.length() < 1) {
		fail("Utility.Create.service(new String[] {\"ConsentsPostTest_service1\", \"ConsentsPostTest_service1\" }); did not succeed");
	}

	attributespecs = Utility.Create.attributespec(new String[] {
			"ConsentsPostTest_as1" }, "user");
	if (attributespecs.length() < 1) {
		fail("Utility.Create.attributespec(new String[] {\"ConsentsPostTest_as1\", \"ConsentsPostTest_as2\" }, \"user\"); did not succeed");
	}

	Utility.Link.attributespecsToService(
			services.getJSONObject(0).getInt("id"), attributespecs
					.getJSONObject(0).getInt("id"), true);
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ConsentsPostTest.class.getSimpleName());
		for (int i = 0; i < consents.length(); i++) {
			Utility.Remove.consent(consents.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Creates a consent and verifies it from the status line.
	 */
	@Test
	public void testConsentsPost() {
		consents = Utility.Create.consent(true, new int[] { attributespecs
				.getJSONObject(0).getInt("id") }, services.getJSONObject(0)
				.getInt("id"));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
