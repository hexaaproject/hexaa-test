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

public class OrganizationEntitlementpacksGet extends OrganizationEntitlementpack {

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
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals("1", jsonResponseArray.getJSONObject(0).getString("entitlement_pack_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        deleteLink(1, new int[]{1});
    }

    @Test
    public void testOrganizationEntitlementpacksGetEntitlements() {
        createPendingLink(1, new int[]{1});
        acceptPendingLink(1, new int[]{1});

        System.out.println(persistent.call(
                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
                BasicCall.REST.GET));
        System.out.println(persistent.call(
                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                BasicCall.REST.GET));

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
