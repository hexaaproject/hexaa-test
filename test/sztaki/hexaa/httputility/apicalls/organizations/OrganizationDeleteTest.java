package sztaki.hexaa.httputility.apicalls.organizations;

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
 * Tests the DELETE method on the /api/organizations/{id} call.
 */
public class OrganizationDeleteTest extends CleanTest {

    /**
     * JSONArray to store the created organizations.
     */
    public static JSONArray organizations = new JSONArray();

    /**
     * Creates 2 organizations.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organizations(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    /**
     * DELETEs the first organization and checks both.
     */
    @Test
    public void testOrganizationDelete() {
        // The DELETE call.
        persistent.call(
                Const.Api.ORGANIZATIONS_ID,
                BasicCall.REST.DEL);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            // GET the first one (the DELETEd one).
            persistent.call(
                    Const.Api.ORGANIZATIONS_ID,
                    BasicCall.REST.GET);
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
            // GET the second one.
            JSONAssert.assertEquals(
                    organizations.getJSONObject(1),
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    2, 2)),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
