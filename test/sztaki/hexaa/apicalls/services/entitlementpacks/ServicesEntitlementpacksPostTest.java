package sztaki.hexaa.apicalls.services.entitlementpacks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the POST method on the /api/services/{id}/entitlementpacks call.
 */
public class ServicesEntitlementpacksPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesEntitlementpacksPostTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store entitlementpacks.
     */
    private static JSONArray entitlemenetpacks = new JSONArray();

    /**
     * Creates two services.
     */
    @BeforeClass
    public static void buildUp() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
    }

    /**
     * POSTs 2 different entitlement over one of the services and verifies them
     * by GETting them on /api/services/{id}/entitlements.
     */
    @Test
    public void testServiceEntitlementpacksPosts() {
        // Creating the first entitlement object
        entitlemenetpacks = Utility.Create.entitlementpacks(1, "testEntitlementpackName1");
        // Checks the status line
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Creating the second entitlement object
        entitlemenetpacks.put(Utility.Create.entitlementpacks(1, "testEntitlementpackName2").get(0));
        // Checks the status line
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs the entitlements from the server
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
                    BasicCall.REST.GET,
                    null,
                    1, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementpacksPostTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenetpacks.getJSONObject(0), jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(entitlemenetpacks.getJSONObject(1), jsonResponse.getJSONObject(1), JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
