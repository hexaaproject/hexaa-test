package sztaki.hexaa.httputility.apicalls.services;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DEL methods on the /api/services , /api/services/{id},
 * /api/services/{id}/attributespecs, /api/services/{id}/entitlementpacks,
 * /api/services/{id}/entitlements and /api/services/{id}/managers calls.
 */
public class ServicesIsEmptyTest extends IsEmptyTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesIsEmptyTest.class.getSimpleName() + " ***");
    }

    /**
     * Test the GET calls in Services on an empty database, they are supposed to
     * return either empty json or 404 not found error.
     */
    @Test
    public void testServicesIsEmptyGet() {

        expectingEmpty(
                Const.Api.SERVICES,
                BasicCall.REST.GET);

        expectingNotFound(
                Const.Api.SERVICES_ID,
                BasicCall.REST.GET);

        expectingNotFound(
                Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                BasicCall.REST.GET);

        expectingNotFound(
                Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
                BasicCall.REST.GET);

        expectingNotFound(
                Const.Api.SERVICES_ID_ENTITLEMENTS,
                BasicCall.REST.GET);
        
        expectingNotFound(
                Const.Api.SERVICES_ID_INVITATIONS,
                BasicCall.REST.GET);

        expectingNotFound(
                Const.Api.SERVICES_ID_MANAGERS,
                BasicCall.REST.GET);

        expectingNotFound(
                Const.Api.SERVICES_ID_ORGANIZATIONS,
                BasicCall.REST.GET);
    }

    /**
     * Test the PUT calls in Services on an empty database, they are supposed to
     * return either empty json or 404 not found error.
     */
    @Test
    public void testServicesIsEmptyPut() {

        expectingNotFound(
                Const.Api.SERVICES_ID,
                BasicCall.REST.PUT);

        expectingNotFound(
                Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
                BasicCall.REST.PUT);

        expectingNotFound(
                Const.Api.SERVICES_ID_MANAGERS_PID,
                BasicCall.REST.PUT);
    }

    /**
     * Test the DEL calls in Services on an empty database, they are supposed to
     * return either empty json or 404 not found error.
     */
    @Test
    public void testServicesIsEmptyDelete() {

        expectingNotFound(
                Const.Api.SERVICES_ID,
                BasicCall.REST.DEL);

        expectingNotFound(
                Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
                BasicCall.REST.DEL);

        expectingNotFound(
                Const.Api.SERVICES_ID_MANAGERS_PID,
                BasicCall.REST.DEL);
    }
    
    /**
     * PATCH method tests.
     */
    @Test
    public void testServicesIsEmptyPatch() {
        expectingNotFound(
                Const.Api.SERVICES_ID,
                BasicCall.REST.PATCH);
    }
}
