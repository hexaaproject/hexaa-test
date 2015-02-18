package sztaki.hexaa.apicalls.organizations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/organizations and /api/organizations/{id}
 * call.
 */
public class OrganizationGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + OrganizationGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	public static JSONArray organizations = new JSONArray();

	/**
	 * Creates 2 organizations.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization(new String[] {
				"OrganizationGetTest_org1", "OrganizationGetTest_org2,", });
		if (organizations.length() < 2) {
			fail("Utility.Create.organization(new String[] {\"OrganizationGetTest_org1\", \"OrganizationGetTest_org2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationGetTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * Test the GET method to get an array of all the organizations.
	 */
	@Test
	public void testOrganizationGetArray() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(Const.Api.ORGANIZATIONS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the GET method to get an object of specific id.
	 */
	@Test
	public void testOrganizationGetById() {
		for (int i = 0; i < organizations.length(); i++) {
			JSONObject jsonResponse;
			try {
				jsonResponse = persistent.getResponseJSONObject(Const.Api.ORGANIZATIONS_ID,
								BasicCall.REST.GET, null, organizations.getJSONObject(i).getInt("id"), 0);
			} catch (ResponseTypeMismatchException ex) {
				fail(ex.getFullMessage());
				return;
			}
			try {
				JSONAssert.assertEquals(organizations.getJSONObject(i),
						jsonResponse, JSONCompareMode.LENIENT);
			} catch (AssertionError e) {
				AssertErrorHandler(e);
			}
		}
	}
}
