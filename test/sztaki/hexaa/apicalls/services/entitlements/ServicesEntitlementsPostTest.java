package sztaki.hexaa.apicalls.services.entitlements;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.*;
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
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTS,
                    BasicCall.REST.GET,
                    null,
                    1, 0);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementsPostTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(entitlemenets.getJSONObject(0), jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(entitlemenets.getJSONObject(1), jsonResponse.getJSONObject(1), JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
