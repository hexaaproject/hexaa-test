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
 * Test the GET method on the /api/invitations/{id} call.
 */
public class InvitationsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + InvitationsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created role.
	 */
	private static JSONArray roles = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created invitations.
	 */
	private static JSONArray invitations = new JSONArray();

	/**
	 * Creates invitations to test the organization, role and service
	 * invitations.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organization("InvitationsGetTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"InvitationsGetTest_org1\"); did not succeed");
		}

		roles = Utility.Create.role("InvitationsGetTest_role1", organizations
				.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.role(new String[] {\"InvitationsGetTest_role1\", organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}

		services = Utility.Create.service("InvitationsGetTest_service1");
		if (services.length() < 1) {
			fail("Utility.Create.service(\"InvitationsGetTest_service1\"); did not succeed");
		}

		invitations.put(Utility.Create.invitationToOrg(null,
				"http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation to organization.", 0, organizations
						.getJSONObject(0).getInt("id")));
		if (invitations.length() < 1) {
			fail("Utility.Create.invitationToOrg(null, \"http://hexaa.eduid.hu/hexaaui\", \"This is a test invitation to organization.\", 0, organizations.getJSONObject(0).getInt(\"id\"))); did not succeed");
		}

		invitations.put(Utility.Create.invitationToOrg(null,
				"http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation to role.", roles.getJSONObject(0)
						.getInt("id"),
				organizations.getJSONObject(0).getInt("id")));
		if (invitations.length() < 2) {
			fail("Utility.Create.invitationToOrg(null, \"http://hexaa.eduid.hu/hexaaui\", \"This is a test invitation to role.\", roles.getJSONObject(0).getInt(\"id\"), organizations.getJSONObject(0).getInt(\"id\"))); did not succeed");
		}

		invitations.put(Utility.Create.invitationToService(null,
				"http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation to service.", services
						.getJSONObject(0).getInt("id")));
		if (invitations.length() < 3) {
			fail("Utility.Create.invitationToService(null, \"http://hexaa.eduid.hu/hexaaui\", \"This is a test invitation to service.\", services.getJSONObject(0).getInt(\"id\"))); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ InvitationsGetTest.class.getSimpleName());
		for (int i = 0; i < invitations.length(); i++) {
			Utility.Remove.invitation(invitations.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
	}

	/**
	 * GET the invitation for organization.
	 */
	@Test
	public void testInvitationGetOrganization() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
					invitations.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		invitations.getJSONObject(0).put("organization_id",
				invitations.getJSONObject(0).remove("organization"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(invitations.getJSONObject(0), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the invitation for role.
	 */
	@Test
	public void testInvitationGetRole() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
					invitations.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		invitations.getJSONObject(1).put("organization_id",
				invitations.getJSONObject(1).remove("organization"));
		invitations.getJSONObject(1).put("role_id",
				invitations.getJSONObject(1).remove("role"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(invitations.getJSONObject(1), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the invitation for service.
	 */
	@Test
	public void testInvitationGetService() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.INVITATIONS_ID, BasicCall.REST.GET, null,
					invitations.getJSONObject(2).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		invitations.getJSONObject(2).put("service_id",
				invitations.getJSONObject(2).remove("service"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(invitations.getJSONObject(2), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
