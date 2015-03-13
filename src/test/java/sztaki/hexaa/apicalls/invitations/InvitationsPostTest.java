package sztaki.hexaa.apicalls.invitations;

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
 * Test the POST method on the /api/invitations call.
 */
public class InvitationsPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + InvitationsPostTest.class.getSimpleName()
				+ " ***");
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
	 * Creates an organizations.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization("InvitationsPostTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"InvitationsPostTest_org1\" ); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ InvitationsPostTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < invitations.length(); i++) {
			Utility.Remove.invitation(invitations.getJSONObject(i)
					.getInt("id"));
		}
	}


	/**
	 * POST one invitation.
	 */
	@Test
	public void testInvitationsPost() {
		invitations = new JSONArray();
		invitations.put(Utility.Create.invitationToOrg(null, "http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation.", 0, organizations.getJSONObject(0).getInt("id")));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
