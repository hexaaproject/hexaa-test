package sztaki.hexaa.httputility.apicalls.principals;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/principal/invitations call.
 */
public class PrincipalInvitationGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalInvitationGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates an organization and an invitation.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");
        Utility.Create.invitationToOrg(new String[]{"testPrincipal@email.something"}, null, null, 0, 1);
    }

    /**
     * GET the invitation.
     */
    @Test
    public void testPrincipalInvitationGet() {
        JSONArray jsonResponse;
        
        try {
            jsonResponse = persistent.getResponseJSONArray(Const.Api.PRINCIPAL_INVITATIONS, BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(PrincipalInvitationGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
        
        try {
            assertEquals(1, jsonResponse.getJSONObject(0).get("id"));
        } catch(AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
