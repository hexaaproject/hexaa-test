package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 * Tests the DELETE method on the /api/organizations/{id}/entitlementpacks and
 * /api/organizations/{id}/entitlements call.
 */
public class OrganizationEntitlementpacksGetTest extends OrganizationEntitlementpack {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksGetTest.class.getSimpleName() + " ***");
    }

    /**
     * GETs the Entitlementpack.
     */
    @Test
    public void testOrganizationEntitlementpacksGetPacks() {
        createPendingLink(1, new int[]{1});
        acceptPendingLink(1, new int[]{1});
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                BasicCall.REST.GET,
                                null,
                                1, 1));
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals(1, jsonResponseArray.getJSONObject(0).getInt("entitlement_pack_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        deleteLink(1, new int[]{1});
    }

    /**
     * GETs the entitlements.
     */
    @Test
    public void testOrganizationEntitlementpacksGetEntitlements() {
        createPendingLink(1, new int[]{1});
        acceptPendingLink(1, new int[]{1});
//
//        System.out.println(persistent.call(
//                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
//                BasicCall.REST.GET));
//        System.out.println(persistent.call(
//                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
//                BasicCall.REST.GET));

        try {
            JSONAssert.assertEquals(
                    entitlements,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
                                    BasicCall.REST.GET)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        deleteLink(1, new int[]{1});
    }
}
