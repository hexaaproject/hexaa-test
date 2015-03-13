package sztaki.hexaa.apicalls.invitations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Test the DELETE method on the /api/invitations/{id} call.
 */
public class InvitationsDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ InvitationsDeleteTest.class.getSimpleName() + " ***");
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
	 * Creates an invitation.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("InvitationsDeleteTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"InvitationsDeleteTest_org1\" ); did not succeed");
		}
		invitations.put(Utility.Create.invitationToOrg(null,
				"http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation to organization.", 0, organizations
						.getJSONObject(0).getInt("id")));
		if (invitations.length() < 1) {
			fail("Utility.Create.invitationToOrg(null, \"http://hexaa.eduid.hu/hexaaui\", \"This is a test invitation to organization.\", 0, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ InvitationsDeleteTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < invitations.length(); i++) {
			Utility.Remove
					.invitation(invitations.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Call a DELETE to remove the invitation and call a GET to verify that the
	 * invitaion was removed.
	 */
	@Test
	public void testInvitationDelete() {
		persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.DELETE, null,
				invitations.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
				invitations.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
