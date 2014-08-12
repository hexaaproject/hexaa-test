package sztaki.hexaa.httputility.apicalls.entitlements;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall.REST;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

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
                REST.GET);
    }

    /**
     * PUT method tests.
     */
    @Test
    public void testEntitlementsIsEmptyPut() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                REST.PUT);
    }

    /**
     * DELETE method tests.
     */
    @Test
    public void testEntitlementsIsEmptyDelete() {
        expectingNotFound(
                Const.Api.ENTITLEMENTS_ID,
                REST.DEL);
    }
}
