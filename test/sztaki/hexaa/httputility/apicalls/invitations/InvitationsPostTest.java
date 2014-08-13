package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the POST method on the /api/invitations call.
 */
public class InvitationsPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsPostTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store created organizations.
     */
    public static JSONArray organizations = new JSONArray();

    /**
     * Creates two organizations.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organization(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    /**
     * POST one invitation.
     */
    @Test
    public void testInvitationsPost() {
        JSONObject json = new JSONObject();
        json.put("email", "testmail@testsztaki.test");
        json.put("landing_url", "http://test.something.test");
        json.put("message", "This is a test invitation.");
        json.put("organization", 1);

        persistent.call(
                Const.Api.INVITATIONS,
                BasicCall.REST.POST,
                json.toString(),
                0, 0);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
