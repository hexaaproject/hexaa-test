package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 * Tests the PUT method on the
 * /api/organizations/{id}/entitlementpacks/{token}/token call.
 */
public class OrganizationEntitlementpacksTokenTest extends OrganizationEntitlementpack {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksTokenTest.class.getSimpleName() + " ***");
    }

    /**
     * Connects an entitlementpack to an organization with PUT using the token.
     */
    @Test
    public void testOrganizationEntitlementpacksPut() {
        JSONObject json
                = (JSONObject) JSONParser.parseJSON(persistent.call(
                                Const.Api.ENTITLEMENTPACKS_ID_TOKEN,
                                BasicCall.REST.GET,
                                null,
                                1, 1));
        // GET the token.
        System.out.println(json);
        persistent.setToken(json.getString("token"));
        //persistent.setToken("1");

        persistent.call(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN, BasicCall.REST.PUT);

        try {
            assertEquals(Const.StatusLine.Created, persistent.getStatusLine());
            JSONArray jsonResponseArray
                    = (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                    BasicCall.REST.GET));
            assertEquals(
                    1,
                    jsonResponseArray.getJSONObject(0).getInt("entitlement_pack_id"));
            assertEquals(
                    "accepted",
                    jsonResponseArray.getJSONObject(0).getString("status"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
//        System.out.println(persistent.call(
//                Const.Api.ENTITLEMENTPACKS_PUBLIC,
//                BasicCall.REST.GET));
//        System.out.println(persistent.call(
//                Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
//                BasicCall.REST.GET));
    }
}
