package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class InvitationsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + InvitationsGetTest.class.getSimpleName() + " ***");
    }
    
    public static JSONObject json = new JSONObject();

    @BeforeClass
    public static void setUpClass() {
        
        json.put("email", "testmail@testsztaki.test");
        json.put("landing_url", "http://test.something.test");
        json.put("message", "This is a test invitation.");
        json.put("organization", 1);

        persistent.call(
                Const.Api.INVITATIONS,
                BasicCall.REST.POST);
    }

    @Test
    public void testInvitationGet() {
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.INVITATIONS_ID,
                                BasicCall.REST.GET,
                                null,
                                1, 1));
        System.out.println(jsonResponse.toString());
        
        try {
            JSONAssert.assertEquals(jsonResponse, json, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
