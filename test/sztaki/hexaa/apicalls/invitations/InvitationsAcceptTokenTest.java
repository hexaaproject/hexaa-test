package sztaki.hexaa.apicalls.invitations;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the GET method on the /api/invitations/{token}/accept/token call.
 */
public class InvitationsAcceptTokenTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ InvitationsAcceptTokenTest.class.getSimpleName() + " ***");
	}

	/**
	 * Creates an invitation, gets the token and authenticate a new user to
	 * accept it.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("TestOrgName1");
		Utility.Create.invitationToOrg(null, "http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation to organization.", 0, 1);

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException e) {
			fail(e.getFullMessage());
			return;
		}

		Utility.Create.principal("invitationAcceptTokenTestFedid");
		new Authenticator().authenticate("invitationAcceptTokenTestFedid");

		persistent.setToken(jsonResponse.getString("token"));
	}

	/**
	 * Resets the fedid to the default.
	 */
	@AfterClass
	public static void tearDownClass() {
		new Authenticator().authenticate(Const.HEXAA_FEDID);
	}

	@Test
	public void testInvitationsAcceptToken() {
		// Object response =
		// JSONParser.parseJSON(persistent.call(Const.Api.INVITATIONS_ID,
		// BasicCall.REST.GET));
		//
		// if (response instanceof JSONArray) {
		// fail("Got JSONArray for unknown reason: " + response.toString());
		// }

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
