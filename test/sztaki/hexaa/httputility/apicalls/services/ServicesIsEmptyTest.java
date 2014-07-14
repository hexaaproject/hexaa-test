package sztaki.hexaa.httputility.apicalls.services;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 * Tests the GET methods on the /api/services , /api/services/{id},
 * /api/services/{id}/attributespecs, /api/services/{id}/entitlementpacks,
 * /api/services/{id}/entitlements and /api/services/{id}/managers calls.
 */
public class ServicesIsEmptyTest extends Services {

    /**
     * Test the GET calls in Services on an empty database, they are supposed to
     * return either empty json or 404 not found error.
     */
    @Test
    public void testServicesIsEmpty() {

        try {
            assertEquals("[]", persistent.call(
                    Const.Api.SERVICES,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        persistent.call(
                Const.Api.SERVICES_ID,
                BasicCall.REST.GET,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        persistent.call(
                Const.Api.SERVICES_ATTRIBUTESPECS,
                BasicCall.REST.GET,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        persistent.call(
                Const.Api.SERVICES_ENTITLEMENTPACKS,
                BasicCall.REST.GET,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        persistent.call(
                Const.Api.SERVICES_ENTITLEMENTS,
                BasicCall.REST.GET,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        persistent.call(
                Const.Api.SERVICES_MANAGERS,
                BasicCall.REST.GET,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
