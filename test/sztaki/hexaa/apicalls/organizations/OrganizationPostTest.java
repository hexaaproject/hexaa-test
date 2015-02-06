package sztaki.hexaa.apicalls.organizations;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the POST method on the /api/organizations call.
 */
public class OrganizationPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationPostTest.class.getSimpleName() + " ***");
	}
	
	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationPostTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
	}

	/**
	 * Test for creating a new organization and verify its existence.
	 */
	@Test
	public void testOrganizationPost() {
		organizations = Utility.Create.organization("OrganizationPostTest_org1");
		// Verifies the POST with a GET
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
