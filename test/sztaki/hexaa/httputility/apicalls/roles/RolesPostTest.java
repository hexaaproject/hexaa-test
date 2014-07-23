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
 * Tests the POST method on the /api/organizations/{id}/roles call.
 */
public class RolesPostTest extends CleanTest {

    /**
     * Creates one organization.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organizations(new String[]{"testOrgForRole1"});
    }

    /**
     * POSTs a new role.
     */
    @Test
    public void testRolesPost() {
        // Creates the JSON object
        JSONObject json = new JSONObject();
        json.put("name", "testPrimeLeader1");
        json.put("start_date", LocalDate.now(ZoneId.of("UTC")).toString());
        // POST the role
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ROLES,
                BasicCall.REST.POST,
                json.toString(),
                1, 1);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
