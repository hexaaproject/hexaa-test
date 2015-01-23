package sztaki.hexaa.apicalls.invitations;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the DELETE method on the /api/invitations/{id} call.
 */
public class InvitationsDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsDeleteTest.class.getSimpleName() + " ***");
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

    /**
     * Call a DELETE to remove the invitation and call a GET to verify that the
     * invitaion was removed.
     */
    @Test
    public void testInvitationDelete() {
        persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.DELETE);

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.GET);

        try {
            assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
