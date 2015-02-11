package sztaki.hexaa.apicalls.invitations;

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
 * Test the PUT method on the /api/invitations/{id} call.
 */
public class InvitationsPutPatchTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ InvitationsPutPatchTest.class.getSimpleName() + " ***");
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
				.organization("InvitationsPutPatchTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"InvitationsPutPatchTest_org1\" ); did not succeed");
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
				+ InvitationsPutPatchTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < invitations.length(); i++) {
			Utility.Remove
					.invitation(invitations.getJSONObject(i).getInt("id"));
		}
	}

	@Test
	public void testInvitationPut() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("message",
				"This is a changed message, it's changed by put.");
		jsonTemp.put("landing_url", invitations.getJSONObject(0).get("landing_url"));
		jsonTemp.put("organization", invitations.getJSONObject(0).get("organization"));

		persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.PUT,
				jsonTemp.toString(), invitations
						.getJSONObject(0).getInt("id"), 0);

		jsonTemp.put("organization_id",
				jsonTemp.remove("organization"));

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
					invitations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(jsonTemp, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	@Test
	public void testInvitationPatch() {
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("message",
				"This is a changed message, it's changed by patch.");

		persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.PATCH,
				jsonTemp.toString(), invitations
						.getJSONObject(0).getInt("id"), 0);

		jsonTemp.put("organization_id",
				jsonTemp.remove("organization"));

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
					invitations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(jsonTemp, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
