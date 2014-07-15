package sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements;

import org.json.JSONArray;
import sztaki.hexaa.httputility.apicalls.entitlementpacks.Entitlementpacks;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Tests the GET methods on the /api/entitlementpacks/{id}/entitlements uri.
 */
public class EntitlementpacksGetEntitlementsTest extends Entitlementpacks {

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
     * GETs the entitlements of the entitlementpacks and asserts them with the
     * locally stored entitlements.
     */
    @Test
    public void testEntitlementpacksGetEntitlements() {
        // GET on the first entitlementpack's entitlements
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                                BasicCall.REST.GET,
                                null,
                                1, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    entitlements,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GET on the second entitlementpack's entitlements
        jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                                BasicCall.REST.GET,
                                null,
                                2, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    entitlements.getJSONObject(0),
                    jsonResponse.getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
