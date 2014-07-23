package sztaki.hexaa.httputility.apicalls.roles;

import java.time.LocalDate;
import java.time.ZoneId;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST and GET method on the /api/organizations/{id}/roles call and
 * the GET method on the /api/roles/{id}.
 */
public class RolesPostGetTest extends CleanTest {

    /**
     * Creates one organization.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForRole1"});
    }

    /**
     * POSTs a new role and checks it with /api/organizations/{id}/roles and
     * /api/role/{id} as well.
     */
    @Test
    public void testRolesPost() {
        // Creates the JSON object
        JSONObject json = new JSONObject();
        json.put("name", "testPrimeLeader1");
        json.put("start_date", LocalDate.now(ZoneId.of("UTC")).toString());
        // POSTs the role
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ROLES,
                BasicCall.REST.POST,
                json.toString(),
                1, 1);
        // Just to match the servers format
        json.put("start_date", json.getString("start_date").concat("T00:00:00+0000"));

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ROLES,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
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
