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
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT methods on the /api/entitlementpacks/{id}/entitlements/{eid}
 * call.
 */
public class EntitlementpacksAddEntitlementsTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementpacksAddEntitlementsTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one service, two entitlements and two entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1"});
        Utility.Create.entitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
    }

    /**
     * PUTs the first entitlement to the second pack as well, and verifies this
     * by GETs.
     */
    @Test
    public void testEntitlementpacksAddEntitlements() {
        Utility.Link.entitlementToPack(1, 2);

        checkEntitlementInPacks(new int[]{1}, 2);
    }

    /**
     * Tests the PUT method on the /api/entitlementpacks/{id}/entitlement.
     */
    @Test
    public void testEntitlementpacksAddEntitlementsByArray() {
        Utility.Link.entitlementToPackByArray(new int[]{1, 2}, 1);

        checkEntitlementInPacks(new int[]{1, 2}, 1);
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
                assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonEntitlementArray, jsonResponseArray, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
