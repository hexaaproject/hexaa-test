package sztaki.hexaa.httputility.apicalls.entitlementpacks;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/entitlementpacks/public ,
 * /api/entitlementpacks/{id} and /api/entitlementpacks/{id}/entitlements calls.
 */
public class EntitlementpacksIsEmptyTest extends CleanTest {

    /**
     * Tests the GET methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementpacksIsEmpty() {
        String stringResponse
                = persistent.call(
                        Const.Api.ENTITLEMENTPACKS,
                        BasicCall.REST.GET);
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals("[]", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        stringResponse
                = persistent.call(
                        Const.Api.ENTITLEMENTPACKS_ID,
                        BasicCall.REST.GET,
                        null,
                        1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        stringResponse
                = persistent.call(
                        Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                        BasicCall.REST.GET,
                        null,
                        1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
