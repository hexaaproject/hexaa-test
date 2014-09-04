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
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DEL methods on the /api/entitlementpacks/{id}/entitlements/{eid}
 * call.
 */
public class EntitlementpacksRemoveEntitlementsTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementpacksRemoveEntitlementsTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlements = new JSONArray();

    /**
     * Uses the Services class utilities to build services, entitlements and
     * entitlementpacks. Also PUTs entitlements to the packs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1"});

        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});

        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementToPack(2, 1);
        Utility.Link.entitlementToPack(1, 2);
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
                    Const.StatusLine.NoContent,
                    persistent.getStatusLine());
            persistent.call(
                    Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                    BasicCall.REST.DEL,
                    null,
                    2, 1);
            assertEquals(
                    Const.StatusLine.NoContent,
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
                    Const.StatusLine.OK,
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
                    Const.StatusLine.OK,
                    persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
