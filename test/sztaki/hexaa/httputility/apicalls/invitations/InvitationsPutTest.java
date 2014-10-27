/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 * @author ede
 */
public class InvitationsPutTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsPutTest.class.getSimpleName() + " ***");
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
                "http://test.something.test",
                "This is a test invitation to organization.",
                0,
                1);
    }

    @Test
    public void testInvitationPut() {
        invitation.put("message", "This is a changed message, it's changed by put.");
        
        persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.PUT, invitation.toString(), 1, 1);
        
        invitation.put("organization_id", invitation.remove("organization"));
        
        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch(AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
