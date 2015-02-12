package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/principal/invitations call.
 */
public class PrincipalInvitationGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalInvitationGetTest.class.getSimpleName() + " ***");
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
	 * Creates an organization and an invitation.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization("PrincipalInvitationGetTest_org1");
		invitations.put(Utility.Create.invitationToOrg(
				null, "http://hexaa.eduid.hu/hexaaui", "This is an invitation.",
				0, organizations.getJSONObject(0).getInt("id")));
	}
	
	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalInvitationGetTest.class.getSimpleName());
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
	 * GET the invitation.
	 */
	@Test
	public void testPrincipalInvitationGet() {
		JSONObject jsonItems;

		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_INVITATIONS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(invitations.getJSONObject(0).getInt("id"), jsonResponse.getJSONObject(0).get("id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
