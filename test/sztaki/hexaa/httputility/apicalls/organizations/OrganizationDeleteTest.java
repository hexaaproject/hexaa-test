package sztaki.hexaa.httputility.apicalls.organizations;

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
import static sztaki.hexaa.httputility.apicalls.organizations.Organization.createOrganization;
import static sztaki.hexaa.httputility.apicalls.organizations.Organization.organizations;

/**
 * Tests the DEL method on the /api/organizations/{id} call.
 */
public class OrganizationDeleteTest extends CleanTest {

    /**
     * Creates 2 organizations.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = createOrganization(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    /**
     * Tests the DEL method to remove one of the organizations, than verifies it
     * by GETing the organization.
     */
    @Test
    public void testOrganizationDelete() {
        // Calling DELETE
        persistent.call(Const.Api.ORGANIZATIONS_ID, BasicCall.REST.DEL, null, 1, 1);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            persistent.call(
                    Const.Api.ORGANIZATIONS_ID,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
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
