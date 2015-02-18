package sztaki.hexaa.apicalls.organizations;

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
 * Tests the GET method on the /api/organizations/{id}/invitations call.
 */
public class OrganizationInvitationGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationInvitationGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created invitations.
	 */
	private static JSONArray invitations = new JSONArray();
	
	/**
	 * Creates one organization and an invitation.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization("OrganizationInvitationGetTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"OrganizationInvitationGetTest_org1\"); did not succeed");
		}
		
		invitations.put(Utility.Create.invitationToOrg(null,
				"http://hexaa.eduid.hu/hexaaui", "This is a test invitation.",
				0, organizations.getJSONObject(0).getInt("id")));
		if (invitations.length() < 1) {
			fail("Utility.Create.invitationToOrg(null, \"http://hexaa.eduid.hu/hexaaui\", \"This is a test invitation.\", 0, organizations.getJSONObject(0).getInt(\"id\"))); did not succeed");
		}
		
		for (int i = 0; i < invitations.length(); i++) {
			invitations.getJSONObject(i).put("organization_id",
					invitations.getJSONObject(i).remove("organization"));
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationInvitationGetTest.class.getSimpleName());
		for (int i = 0; i < invitations.length(); i++) {
			Utility.Remove.invitation(invitations.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
	}
	
	/**
	 * Gets all the invitations of the organization and checks it with the
	 * stored one.
	 */
	@Test
	public void testOrganizationInvitationGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_INVITATIONS, BasicCall.REST.GET,
					null, organizations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			JSONAssert.assertEquals(invitations, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
