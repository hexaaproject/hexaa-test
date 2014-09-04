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
 * Tests the GET methods on the /api/entitlementpacks/{id}/entitlements call.
 */
public class EntitlementpacksGetEntitlementsTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementpacksGetEntitlementsTest.class.getSimpleName() + " ***");
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    entitlements.getJSONObject(0),
                    jsonResponse.getJSONObject(0),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
