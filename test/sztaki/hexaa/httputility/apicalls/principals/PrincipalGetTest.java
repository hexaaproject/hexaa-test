package sztaki.hexaa.httputility.apicalls.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/principal/self and /api/principals calls.
 */
public class PrincipalGetTest extends CleanTest {

    /**
     * Creates all the necessary objects for the tests.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * GET info about the current principal.
     */
    @Test
    public void testPrincipalSelfGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_SELF,
                                BasicCall.REST.GET));

        JSONObject jsonResponse = (JSONObject) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(Const.HEXAA_FEDID, jsonResponse.getString("fedid"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET all the principals and search for the admin.
     */
    @Test
    public void testPrincipalsGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPALS,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but a JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        for (int i = 0; i < jsonResponse.length(); i++) {
            try {
                assertEquals(Const.HEXAA_FEDID, jsonResponse.getJSONObject(i).get("fedid"));
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }

    /**
     * GET the principal by fedid.
     */
    @Test
    public void testPrincipalsGetByFedid() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPALS_FEDID,
                                BasicCall.REST.GET,
                                null,
                                1, 1,
                                Const.HEXAA_FEDID));

        JSONObject jsonResponse = (JSONObject) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(Const.HEXAA_FEDID, jsonResponse.getString("fedid"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET the principal by id.
     */
    @Test
    public void testPrincipalsGetById() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPALS_ID,
                                BasicCall.REST.GET,
                                null,
                                1, 1));

        JSONObject jsonResponse = (JSONObject) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(Const.HEXAA_FEDID, jsonResponse.getString("fedid"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
