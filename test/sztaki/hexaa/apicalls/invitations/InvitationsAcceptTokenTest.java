package sztaki.hexaa.apicalls.invitations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Test the GET method on the /api/invitations/{token}/accept/token call.
 */
public class InvitationsAcceptTokenTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ InvitationsAcceptTokenTest.class.getSimpleName() + " ***");
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
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates an invitation, gets the token and authenticate a new user to
	 * accept it.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("InvitationsAcceptTokenTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"InvitationsAcceptTokenTest_org1\"); did not succeed");
		}
		invitations.put(Utility.Create.invitationToOrg(null,
				"http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation to organization.", 0, organizations
						.getJSONObject(0).getInt("id")));
		if (invitations.length() < 1) {
			fail("Utility.Create.invitationToOrg(null,\"http://hexaa.eduid.hu/hexaaui\", \"This is a test invitation to organization.\", 0, organizations.getJSONObject(0).getInt(\"id\"))); did not succeed");
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
					invitations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException e) {
			fail(e.getFullMessage());
			return;
		}

		principals = Utility.Create
				.principal("InvitationsAcceptTokenTest_pri1");
		if (principals.length() < 1) {
			fail("Utility.Create.principal(\"InvitationsAcceptTokenTest_pri1\"); did not succeed");
		}
		new Authenticator().authenticate("InvitationsAcceptTokenTest_pri1");

		persistent.setToken(jsonResponse.getString("token"));
	}

	/**
	 * Resets the fedid to the default.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ InvitationsAcceptTokenTest.class.getSimpleName());
		new Authenticator().authenticate(Const.HEXAA_FEDID);
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < invitations.length(); i++) {
			Utility.Remove
					.invitation(invitations.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	@Test
	public void testInvitationsAcceptToken() {

		persistent.call(Const.Api.INVITATIONS_TOKEN_ACCEPT_TOKEN,
				BasicCall.REST.GET);

		try {
			assertEquals(Const.StatusLine.RedirectFound,
					persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		if (persistent.getHeader("Location") == null) {
			fail("no location header; ");
			return;
		}

		try {
			assertEquals("http://hexaa.eduid.hu/hexaaui",
					persistent.getHeader("Location").getValue());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
