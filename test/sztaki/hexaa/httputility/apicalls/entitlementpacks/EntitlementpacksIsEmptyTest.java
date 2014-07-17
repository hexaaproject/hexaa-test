package sztaki.hexaa.httputility.apicalls.entitlementpacks;

import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DEL methods on the /api/entitlementpacks/public ,
 * /api/entitlementpacks/{id} and /api/entitlementpacks/{id}/entitlements calls.
 */
public class EntitlementpacksIsEmptyTest extends IsEmptyTest {

    /**
     * Tests the GET methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementpacksIsEmptyGet() {
        expectingEmpty(
                Const.Api.ENTITLEMENTPACKS_PUBLIC,
                BasicCall.REST.GET);

        expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID,
                BasicCall.REST.GET
        );

        expectingNotFound(
                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
                BasicCall.REST.GET
        );
    }

    /**
     * Tests the PUT methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementpacksIsEmptyPut() {
        expectingNotFound(
                Const.Api.ENTITLEMENTPACKS_ID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT);
    }

    /**
     * Tests the DEL methods on an empty database, 404 errors and empty JSONs
     * are expected.
     */
    @Test
    public void testEntitlementpacksIsEmptyDelete() {
        expectingNotFound(
                Const.Api.ENTITLEMENTPACKS_ID,
                BasicCall.REST.DEL);
        expectingNotFound(
                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                BasicCall.REST.DEL);
    }
}
