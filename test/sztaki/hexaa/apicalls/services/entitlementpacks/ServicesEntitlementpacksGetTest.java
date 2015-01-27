package sztaki.hexaa.apicalls.services.entitlementpacks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/services/{id}/entitlementpacks call with
 * more services and entitlementpacks.
 */
public class ServicesEntitlementpacksGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesEntitlementpacksGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store entitlementpacks.
     */
    private static JSONArray entitlementpacks = new JSONArray();

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void buildUp() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
        entitlementpacks.put(Utility.Create.entitlementpacks(2, new String[]{"testEntitlementpacks3"}).getJSONObject(0));
    }

    /**
     * Calls GET /api/services/{id}/entitlements on the 2 services to get the 3
     * entitlements.
     */
    @Test
    public void testServicesEntitlementsGet() {
        // GETs the first service's entitlementpacks
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
                    BasicCall.REST.GET,
                    null,
                    1, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementpacksGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        JSONArray jsonTemp = new JSONArray();
        jsonTemp.put(entitlementpacks.getJSONObject(0));
        jsonTemp.put(entitlementpacks.getJSONObject(1));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            // GETs the second service's entitlementpacks
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
                    BasicCall.REST.GET,
                    null,
                    2, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementpacksGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        jsonTemp = new JSONArray();
        jsonTemp.put(entitlementpacks.getJSONObject(2));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
