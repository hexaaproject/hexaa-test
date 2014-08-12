package sztaki.hexaa.httputility.apicalls.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the DELETE method on the /api/principal call.
 */
public class PrincipalsDeleteSelfTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsDeleteSelfTest.class.getSimpleName() + " ***");
    }

    /**
     * Re-Authenticates as testPrincipal for the test.
     */
    @BeforeClass
    public static void setUpClass() {
        new Authenticator().authenticate("testPrincipal@something.test");
    }

    /**
     * GET the test principal, DELETE it, than test it for forbidden and against
     * the list of principals.
     */
    @Test
    public void testPrincipalDeleteSelf() {
        JSONObject testPrincipal
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_SELF,
                                BasicCall.REST.GET));

        persistent.call(Const.Api.PRINCIPAL, BasicCall.REST.DEL);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        persistent.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET);

        try {
            assertEquals("HTTP/1.1 403 Forbidden", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        new Authenticator().authenticate(Const.HEXAA_FEDID);

        Object response
                = JSONParser.parseJSON(persistent.call(Const.Api.PRINCIPALS, BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        for (int i = 0; i < jsonResponse.length(); i++) {
            try {
                JSONAssert.assertNotEquals(testPrincipal, jsonResponse.getJSONObject(i), JSONCompareMode.LENIENT);
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }
}
