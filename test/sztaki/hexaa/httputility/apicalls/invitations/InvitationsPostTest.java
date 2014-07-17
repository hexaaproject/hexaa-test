package sztaki.hexaa.httputility.apicalls.invitations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import static sztaki.hexaa.httputility.apicalls.organizations.Organization.createOrganization;
import static sztaki.hexaa.httputility.apicalls.organizations.Organization.organizations;

public class InvitationsPostTest extends CleanTest {

    @BeforeClass
    public static void setUpClass() {
        organizations = createOrganization(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

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