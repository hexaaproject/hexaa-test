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
import sztaki.hexaa.httputility.apicalls.entitlementpacks.Entitlementpacks;
import sztaki.hexaa.httputility.apicalls.services.Services;

public class OrganizationEntitlementpacks extends Organization {

    public static JSONArray entitlements = new JSONArray();
    public static JSONArray entitlementpacks = new JSONArray();
    public static JSONArray organizations = new JSONArray();
    public static JSONArray services = new JSONArray();

    /**
     * Creates a service, an organization, entitlements and entitlementpacks and
     * puts them together to create full entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Services.createServices(new String[]{"testService"});
        organizations = createOrganization(new String[]{"testOrganization"});
        Services.createServiceEntitlements(1, new String[]{"testEntitlement1", "testEntitlement2"});
        Services.createServiceEntitlementpacks(1, new String[]{"testEntitlementpack1", "testEntitlementpack2"});
        Entitlementpacks.putEntitlementToPack(1, 1);
        Entitlementpacks.putEntitlementToPack(2, 1);
    }

    /**
     * Connects an entitlementpack to an organization with PUT, verifies its
     * pending status than accepts it and verifies the accepted status.
     */
    @Test
    public void testOrganizationEntitlementpacksGetPutAndAccept() {
        // Connect one entitlementpack to an organization
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                BasicCall.REST.PUT,
                null,
                1, 1);
        // Create a JSON representation of the connection for easy compare
        JSONArray tempArray = new JSONArray();
        JSONObject tempObject = new JSONObject();
        tempObject.put("organization_id", "1");
        tempObject.put("entitlement_pack_id", "1");
        tempObject.put("status", "pending");
        tempArray.put(tempObject);
        // GET the details of this connection and check that it is pending
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    tempArray,
                    (JSONArray) JSONParser.parseJSON(persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        // Accept the entitlementpack
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
                BasicCall.REST.PUT,
                null,
                1, 1);
        // Modify the JSON representation to accepted
        tempObject.put("status", "accepted");
        // GET the details of this connection and check that it is accepted
        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    tempArray,
                    (JSONArray) JSONParser.parseJSON(persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
