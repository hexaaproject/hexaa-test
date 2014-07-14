package sztaki.hexaa.httputility.apicalls.entitlements;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall.REST;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the empty database for the calls on the /api/entitlements/{id} uri.
 */
public class EntitlementsIsEmptyTest extends CleanTest {

    /**
     * Tests the /api/entitlements/{id} with GET, PUT, DELETE methods for the
     * required response of the 404.
     */
    @Test
    public void testEntitlementsIsEmpty() {
        // Enumerate down the 3 calls on the uri
        for (REST method : new REST[]{REST.GET, REST.PUT, REST.DELETE}) {
            persistent.call(
                    Const.Api.ENTITLEMENTS_ID,
                    method,
                    null,
                    1, 0);
            try {
                assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }

    }

}
