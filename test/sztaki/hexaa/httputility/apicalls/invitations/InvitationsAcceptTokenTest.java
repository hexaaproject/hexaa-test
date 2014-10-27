/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the GET method on the /api/invitations/{token}/accept/token call.
 */
public class InvitationsAcceptTokenTest extends CleanTest{
    
    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsAcceptTokenTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates an invitation.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("TestOrgName1");
        Utility.Create.invitationToOrg(
                null,
                "http://hexaa.eduid.hu/hexaaui",
                "This is a test invitation to organization.",
                0,
                1);
    }
    
    @Test
    public void testInvitationsAcceptToken() {
        Object response = JSONParser.parseJSON(persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.GET));
        
        if (response instanceof JSONArray) {
            fail("Got JSONArray for unknown reason: " + response.toString());
        }
        
        JSONObject jsonResponse = (JSONObject) response;
        
        persistent.setToken(jsonResponse.getString("token"));
        
        System.out.println(persistent.call(Const.Api.INVITATIONS_TOKEN_ACCEPT_TOKEN, BasicCall.REST.GET));
        
        try {
            assertEquals(Const.StatusLine.RedirectFound, persistent.getStatusLine());
            assertEquals("http://hexaa.eduid.hu/hexaaui", persistent.getHeader("Location").getValue());
        } catch(AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
