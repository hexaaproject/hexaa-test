package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.entitlementpacks.Entitlementpacks;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;
import static sztaki.hexaa.httputility.apicalls.organizations.Organization.createOrganization;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Abstract utility class for the calls on
 * /api/organizations/{id}/entitlementpacks/{epid} uri.
 */
public abstract class OrganizationEntitlementpack extends Organization {

    public static JSONArray entitlements = new JSONArray();

    /**
     * Creates a service, an organization, entitlements and entitlementpacks and
     * puts them together to create full entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Services.createServices(new String[]{"testService"});
        createOrganization(new String[]{"testOrganization"});
        entitlements = Services.createServiceEntitlements(1, new String[]{"testEntitlement1", "testEntitlement2"});
        Services.createServiceEntitlementpacks(1, new String[]{"testEntitlementpack1"});
        Entitlementpacks.putEntitlementToPack(1, 1);
        Entitlementpacks.putEntitlementToPack(2, 1);
    }

    /**
     * Creates a link between the organization(orgId) and the
     * entitlementpacks(packIds), and checks that it is a pending link.
     *
     * @param orgId single organization to link to.
     * @param packIds entitlementpacks' ids to link.
     */
    protected void createPendingLink(int orgId, int[] packIds) {
        for (int pack : packIds) {
            // Connect one entitlementpack to an organization
            persistent.call(
                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                    BasicCall.REST.PUT,
                    null,
                    orgId, pack);
            // Create a JSON representation of the connection for easy compare
            JSONObject tempObject = new JSONObject();
            tempObject.put("organization_id", Integer.toString(orgId));
            tempObject.put("entitlement_pack_id", Integer.toString(pack));
            tempObject.put("status", "pending");
            JSONArray tempArray = new JSONArray();
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
        }
    }

    /**
     * Accepts the link between the organization(orgId) and the
     * entitlementpacks(packIds), and checks that it is a pending link.
     *
     * @param orgId single organization to link to.
     * @param packIds entitlementpacks' ids to link.
     */
    protected void acceptPendingLink(int orgId, int[] packIds) {
        for (int pack : packIds) {
            // Accept the entitlementpack
            persistent.call(
                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
                    BasicCall.REST.PUT,
                    null,
                    orgId, pack);
            // Create a JSON representation of the connection for easy compare
            JSONObject tempObject = new JSONObject();
            tempObject.put("organization_id", Integer.toString(orgId));
            tempObject.put("entitlement_pack_id", Integer.toString(pack));
            tempObject.put("status", "accepted");
            JSONArray tempArray = new JSONArray();
            tempArray.put(tempObject);
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

    /**
     * Deletes the link between the organization(orgId) and the
     * entitlementpacks(packIds), but there are no checks between.
     *
     * @param orgId single organization to delete the link.
     * @param packIds entitlementpacks' ids to delete the link.
     */
    protected void deleteLink(int orgId, int[] packIds) {
        for (int pack : packIds) {
            persistent.call(
                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                    BasicCall.REST.DEL,
                    null,
                    orgId, pack);
        }
    }
}