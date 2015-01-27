package sztaki.hexaa.apicalls.services.entitlements;

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
 * Tests the GET method on the /api/services/{id}/entitlements call.
 */
public class ServicesEntitlementsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesEntitlementsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store entitlements.
     */
    private static JSONArray entitlements = new JSONArray();

    /**
     * Creates 2 services and 3 entitlements.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlements1", "testEntitlements2"});
        entitlements.put(Utility.Create.entitlements(2, new String[]{"testEntitlements3"}).getJSONObject(0));
    }

    /**
     * Calls GET /api/services/{id}/entitlements on the 2 services to get the 3
     * entitlements created in the buildUp().
     */
    @Test
    public void testServicesEntitlementsGet() {
        // GETs the first services entitlements
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTS,
                    BasicCall.REST.GET,
                    null,
                    1, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementsGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        JSONArray jsonTemp = new JSONArray();
        jsonTemp.put(entitlements.getJSONObject(0));
        jsonTemp.put(entitlements.getJSONObject(1));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            // GETs the second services entitlement
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTS,
                    BasicCall.REST.GET,
                    null,
                    2, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementsGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        jsonTemp = new JSONArray();
        jsonTemp.put(entitlements.getJSONObject(2));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, false);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
