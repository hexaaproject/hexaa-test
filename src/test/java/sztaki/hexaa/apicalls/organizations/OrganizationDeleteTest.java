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
 * Tests the DELETE method on the /api/organization/{id} call.
 */
public class OrganizationDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationDeleteTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organization.
	 */
	public static JSONArray organizations = new JSONArray();

	/**
	 * Creates 2 organization.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "OrganizationDeleteTest_org1",
						"OrganizationDeleteTest_org2," });
		if (organizations.length() < 2) {
			fail("Utility.Create.organization(new String[] {\"OrganizationDeleteTest_org1\", \"OrganizationDeleteTest_org2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationDeleteTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * DELETEs the first organization and checks both.
	 */
	@Test
	public void testOrganizationDelete() {
		// The DELETE call.
		Utility.Remove
				.organization(organizations.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		persistent.call(Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET, null,
				organizations.getJSONObject(0).getInt("id"), 0);

		try {
			// GET the first one (the DELETEd one).
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET, null, organizations
					.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
			try {
				JSONAssert.assertEquals(organizations.getJSONObject(1),
						jsonResponse, JSONCompareMode.LENIENT);
				assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			} catch (AssertionError e) {
				AssertErrorHandler(e);
			}
	}
}
