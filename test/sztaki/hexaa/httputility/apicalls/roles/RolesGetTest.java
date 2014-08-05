package sztaki.hexaa.httputility.apicalls.roles;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/organizations/{id}/role and /api/roles/{id}
 * calls.
 */
public class RolesGetTest extends CleanTest {

    /**
     * JSONArray to store the created roles.
     */
    public static JSONArray roles = new JSONArray();

    /**
     * Creates one organization and one role for it.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForRole1"});
        roles = Utility.Create.role(new String[]{"testRole1"}, 1);
    }

    /**
     * GET the role in two way.
     */
    @Test
    public void testRolesGet() {
        try {
            JSONAssert.assertEquals(
                    roles,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ROLES,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    roles.getJSONObject(0),
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ROLES_ID,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
