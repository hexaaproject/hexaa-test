package sztaki.hexaa.apicalls.services;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the POST methods on the /api/services.
 */
public class ServicesPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ServicesPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	public static JSONArray services = new JSONArray();
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: " + ServicesPostTest.class.getSimpleName());
		Utility.Remove.service(services.getJSONObject(0).getInt("id"));
	}

	/**
	 * Test for the /app.php/api/services POST call, creates a new Service on
	 * the sample entityid and verifies the success.
	 */
	@Test
	public void testServicePost() {
		// Creates the json object to be POSTed on the server
		services = Utility.Create.service("ServicesPostTest_service1");

		// Checks the creation by Status Line
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
