package sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements;

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
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Tests the PUT methods on the /api/entitlementpacks/{id}/entitlements/{eid}
 * uri.
 */
public class EntitlementpacksAddEntitlementsTest extends CleanTest {

    /**
     * Uses the Services class utilities to build services, entitlements and
     * entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Services.createServices(new String[] {"testService1"});
        Services.createServiceEntitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});
        Services.createServiceEntitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
    }

    /**
     * PUTs both entitlement in the first pack and the first entitlement to the
     * second pack as well, and verifies this by GETs.
     */
    @Test
    public void testEntitlementpacksAddEntitlements() {

        putEntitlementToPack(1, 1);
        putEntitlementToPack(2, 1);
        putEntitlementToPack(1, 2);

        checkEntitlementInPacks(new int[]{1, 2}, 1);
        checkEntitlementInPacks(new int[]{1}, 2);
    }

    /**
     * PUTs the existing entitlement specified by the entitlementId in the
     * entitlementpack specified by the packId
     *
     * @param entitlementId int: the id of the entitlement
     * @param packId int: id of the entitlementpack
     */
    public void putEntitlementToPack(int entitlementId, int packId) {
        persistent.call(
                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT,
                null,
                entitlementId, packId);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Checks for the entitlements specified by the ids in the entitlementIds in
     * the entitlementpack specified by the packId.
     *
     * @param entitlementIds an int[] with the ids of the entitlements
     * @param packId an int with the id of the entitlementpack
     */
    public void checkEntitlementInPacks(int[] entitlementIds, int packId) {
        JSONArray jsonEntitlementArray = new JSONArray();
        for (int i : entitlementIds) {
            jsonEntitlementArray.put(
                    (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ENTITLEMENTS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    i, 0)));
            try {
                assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }

        JSONArray jsonResponseArray;

        jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                                BasicCall.REST.GET,
                                null,
                                packId, 0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(jsonResponseArray, jsonEntitlementArray, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
