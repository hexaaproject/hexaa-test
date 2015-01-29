package sztaki.hexaa.apicalls.organizations;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/organizations/{id}/invitations call.
 */
public class OrganizationInvitationGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationInvitationGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * Creates one organization and an invitation.
	 */
	@BeforeClass
	public static void setUpClass() {
		invitations = new JSONArray();
		Utility.Create.organization("testOrg");
		invitations.put(Utility.Create.invitationToOrg(null,
				"http://hexaa.eduid.hu/hexaaui", "This is a test invitation.",
				0, 1));
		for (int i = 0; i < invitations.length(); i++) {
			invitations.getJSONObject(i).put("organization_id",
					invitations.getJSONObject(i).remove("organization"));
		}
	}

	/**
	 * JSONArray to store the created invitations.
	 */
	public static JSONArray invitations;

	/**
	 * Gets all the invitations of the organization and checks it with the
	 * stored one.
	 */
	@Test
	public void testOrganizationInvitationGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.ORGANIZATIONS_ID_INVITATIONS, BasicCall.REST.GET,
					null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationInvitationGetTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(invitations, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
