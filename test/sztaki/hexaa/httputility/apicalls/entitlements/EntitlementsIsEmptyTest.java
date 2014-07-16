package sztaki.hexaa.httputility.apicalls.entitlements;

import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall.REST;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the empty database for the GET,PUT,DEL calls on the
 * /api/entitlements/{id} uri.
 */
public class EntitlementsIsEmptyTest extends IsEmptyTest {

    /**
     * Tests the GET methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementsIsEmptyGet() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                REST.GET);
    }

    /**
     * Tests the GET methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementsIsEmptyPut() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                REST.PUT);
    }

    /**
     * Tests the GET methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementsIsEmptyDelete() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                REST.DEL);
    }
}
