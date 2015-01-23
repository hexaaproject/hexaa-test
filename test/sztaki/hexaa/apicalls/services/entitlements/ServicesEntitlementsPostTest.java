package sztaki.hexaa.apicalls.services.entitlements;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test for the POST /app.php/api/services/{id}/entitlements call.
 */
public class ServicesEntitlementsPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesEntitlementsPostTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store entitlements.
     */
    private static JSONArray entitlemenets = new JSONArray();

    /**
     * Creates two services.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
    }

    /**
     * POSTs 2 different entitlement over one of the services and verifies them
     * by GETting them on /api/services/{id}/entitlements.
     */
    @Test
    public void testServiceEntitlementsPosts() {
        // Creating the first entitlement object
        entitlemenets = Utility.Create.entitlements(1, "testEntitlementName1");
        // Checks the status line
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Creating the second entitlement object
        entitlemenets.put(Utility.Create.entitlements(1, "testEntitlementName2").get(0));
        // Checks the status line
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs the entitlements from the server
        Object response = JSONParser.parseJSON(
                persistent.call(
                        Const.Api.SERVICES_ID_ENTITLEMENTS,
                        BasicCall.REST.GET,
                        null,
                        1, 0));
        if (response instanceof JSONObject) {
            fail("Got error obejct instead of array response: " + response.toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenets.getJSONObject(0), jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(entitlemenets.getJSONObject(1), jsonResponse.getJSONObject(1), JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
