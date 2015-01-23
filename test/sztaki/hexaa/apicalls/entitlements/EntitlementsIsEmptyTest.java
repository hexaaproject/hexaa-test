package sztaki.hexaa.apicalls.entitlements;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the entitlement related calls and
 * expecting not found or empty answers.
 */
public class EntitlementsIsEmptyTest extends IsEmptyTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + EntitlementsIsEmptyTest.class.getSimpleName() + " ***");
    }

    /**
     * GET method tests.
     */
    @Test
    public void testEntitlementsIsEmptyGet() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                BasicCall.REST.GET);
    }

    /**
     * PUT method tests.
     */
    @Test
    public void testEntitlementsIsEmptyPut() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                BasicCall.REST.PUT);
    }

    /**
     * DELETE method tests.
     */
    @Test
    public void testEntitlementsIsEmptyDelete() {
        expectingNotFound(Const.Api.ENTITLEMENTS_ID,
                BasicCall.REST.DELETE);
    }
    
    /**
     * PATCH method tests.
     */
    @Test
    public void testEntitlementsIsEmptyPatch() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                BasicCall.REST.PATCH);
    }
}
