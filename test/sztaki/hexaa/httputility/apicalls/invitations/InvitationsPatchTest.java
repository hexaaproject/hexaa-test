package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the PATCH method on the /api/invitations/{id} call.
 */
public class InvitationsPatchTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsPatchTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONObject to store the invitation for organization.
     */
    public static JSONObject invitation = new JSONObject();

    /**
     * Creates an invitation to test the organization invitation.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("TestOrgName1");
        invitation = Utility.Create.invitationToOrg(
                null,
                "http://hexaa.eduid.hu/hexaaui",
                "This is a test invitation to organization.",
                0,
                1);
    }

    @Test
    public void testInvitationPut() {
        JSONObject json = new JSONObject();
        json.put("message", "This is a changed message, it's changed by put.");
        
        persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.PATCH, json.toString(), 1, 1);
        
        invitation.put("organization_id", invitation.remove("organization"));
        invitation.put("message", json.getString("message"));
        
        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch(AssertionError e) {
            AssertErrorHandler(e);
        }
        
        Object response = JSONParser.parseJSON(persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.GET));
        
        if (response instanceof JSONArray) {
            fail("Response is a JSONArray for unknown reason: " + response.toString());
        }
        
        JSONObject jsonResponse = (JSONObject) response;
        
        try {
            JSONAssert.assertEquals(invitation, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
