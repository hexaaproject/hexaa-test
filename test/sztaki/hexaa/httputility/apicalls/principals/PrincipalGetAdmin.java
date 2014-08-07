package sztaki.hexaa.httputility.apicalls.principals;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the GET method on the /api/principal/isadmin call.
 */
public class PrincipalGetAdmin extends CleanTest {

    /**
     * GET the admin info about the principal when the principal is an admin.
     */
    @Test
    public void testPrincipalGetIsAdmin() {
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ISADMIN,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(true, jsonResponse.getBoolean("is_admin"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET the admin info about the principal when the principal is not an
     * admin.
     */
    @Test
    public void testPrincipalGetNotAdmin() {
        new Authenticator().authenticate("admin@is.not");

        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ISADMIN,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(false, jsonResponse.getBoolean("is_admin"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        System.out.println(persistent.call(Const.Api.PRINCIPALS, BasicCall.REST.GET));

        new Authenticator().authenticate(Const.HEXAA_FEDID);
    }
}
