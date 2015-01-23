package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the GET method on the /api/invitations/{id} call.
 */
public class InvitationsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONObject to store the invitation for organization.
     */
    public static JSONObject invitationOrganization = new JSONObject();
    /**
     * JSONObject to store the invitation for role.
     */
    public static JSONObject invitationRole = new JSONObject();
    /**
     * JSONObject to store single the invitation for service.
     */
    public static JSONObject invitationService = new JSONObject();

    /**
     * Creates invitations to test the organization, role and service
     * invitations.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("TestOrgName1");
        Utility.Create.role("TestRole1", 1);

        Utility.Create.service("TestService1");

        invitationOrganization = Utility.Create.invitationToOrg(
                null,
                "http://hexaa.eduid.hu/hexaaui",
                "This is a test invitation to organization.",
                0,
                1);

        invitationRole = Utility.Create.invitationToOrg(
                null,
                "http://hexaa.eduid.hu/hexaaui",
                "This is a test invitation to role.",
                1,
                1);

        invitationService = Utility.Create.invitationToService(
                null,
                "http://hexaa.eduid.hu/hexaaui",
                "This is a test invitation to service.",
                1);
    }

    /**
     * GET the invitation for organization.
     */
    @Test
    public void testInvitationGetOrganization() {
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.INVITATIONS_ID,
                                BasicCall.REST.GET,
                                null,
                                1, 1));
        System.out.println(jsonResponse.toString());

        invitationOrganization.put("organization_id", invitationOrganization.remove("organization"));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(invitationOrganization, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET the invitation for role.
     */
    @Test
    public void testInvitationGetRole() {
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.INVITATIONS_ID,
                                BasicCall.REST.GET,
                                null,
                                2, 2));
        System.out.println(jsonResponse.toString());

        invitationRole.put("organization_id", invitationRole.remove("organization"));
        invitationRole.put("role_id", invitationRole.remove("role"));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(invitationRole, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET the invitation for service.
     */
    @Test
    public void testInvitationGetService() {
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.INVITATIONS_ID,
                                BasicCall.REST.GET,
                                null,
                                3, 3));
        System.out.println(jsonResponse.toString());

        invitationService.put("service_id", invitationService.remove("service"));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(invitationService, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
