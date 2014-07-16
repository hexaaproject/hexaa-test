package sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.entitlementpacks.Entitlementpacks;
import static sztaki.hexaa.httputility.apicalls.entitlementpacks.Entitlementpacks.entitlements;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Tests the DEL methods on the /api/entitlementpacks/{id}/entitlements/{eid}
 * uri.
 */
public class EntitlementpacksRemoveEntitlementsTest extends CleanTest {

    /**
     * Uses the Services class utilities to build services, entitlements and
     * entitlementpacks. Also PUTs entitlements to the packs.
     */
    @BeforeClass
    public static void setUpClass() {
        Services.resetEntitlements();
        Services.resetEntitlementpacks();

        Services.createServices(1);

        entitlements = Services.createServiceEntitlements(1, 2);

        Services.createServiceEntitlementpacks(1, 2);

        Entitlementpacks.putEntitlementToPack(1, 1);
        Entitlementpacks.putEntitlementToPack(2, 1);
        Entitlementpacks.putEntitlementToPack(1, 2);
    }

    /**
     * Removes the first entitlement of both packs and verifies it with proper
     * GETs.
     */
    @Test
    public void testEntitlementpacksRemoveEntitlements() {
        // DELETEs the first entitlements in both packs
        try {
            persistent.call(
                    Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                    BasicCall.REST.DEL,
                    null,
                    1, 1);
            assertEquals(
                    "HTTP/1.1 204 No Content",
                    persistent.getStatusLine());
            persistent.call(
                    Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                    BasicCall.REST.DEL,
                    null,
                    2, 1);
            assertEquals(
                    "HTTP/1.1 204 No Content",
                    persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs both packs entitlements
        JSONArray json = new JSONArray();
        json.put(entitlements.getJSONObject(1));
        try {
            JSONAssert.assertEquals(
                    json,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 0)),
                    JSONCompareMode.LENIENT);
            assertEquals(
                    "HTTP/1.1 200 OK",
                    persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "[]",
                    persistent.call(
                            Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                            BasicCall.REST.GET,
                            null,
                            2, 0));
            assertEquals(
                    "HTTP/1.1 200 OK",
                    persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
