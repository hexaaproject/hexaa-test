package sztaki.hexaa.apicalls.services.organizations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/services/{id}/organization call.
 */
public class ServicesOrganizationsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesOrganizationsGetTest.class.getSimpleName() + " ***");
    }

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
        Utility.Link.entitlementpackToOrg(1, new int[]{1});
    }

    /**
     * GET the organization linked to the service.
     */
    @Test
    public void testServicesOrganizationsGet() {
        Object jsonResponse = JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ID_ORGANIZATIONS,
                        BasicCall.REST.GET));
        if (jsonResponse instanceof JSONObject) {
            fail("Response is a JSONObject instead of a JSONArray: " + jsonResponse.toString());
        }
        JSONArray jsonArrayResponse = (JSONArray) jsonResponse;
        try {
            assertEquals(1, jsonArrayResponse.length());
            assertEquals(1, jsonArrayResponse.getJSONObject(jsonArrayResponse.length() - 1).getInt("id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
