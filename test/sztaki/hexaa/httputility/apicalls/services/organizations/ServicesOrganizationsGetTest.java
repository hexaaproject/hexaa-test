package sztaki.hexaa.httputility.apicalls.services.organizations;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/services/{id}/organization call.
 */
public class ServicesOrganizationsGetTest extends CleanTest {

    /**
     * JSONArray to store managers.
     */
    public static JSONArray organizations = new JSONArray();

    /**
     * Creates two services an organization an entitlementpack and links the
     * pack to the organization.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        organizations = Utility.Create.organization(new String[]{"testOrgSrv1"});
        Utility.Create.entitlementpacks(1, new String[]{"testPack1"});
        Utility.Link.entitlementpacksToOrg(1, new int[]{1});
    }

    /**
     * GET the organization linked to the service.
     */
    @Test
    public void testServicesOrganizationsGet() {
        try {
            assertEquals(
                    "1",
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_ORGANIZATIONS,
                                    BasicCall.REST.GET)))
                    .getJSONObject(0)
                    .getString("organization_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
