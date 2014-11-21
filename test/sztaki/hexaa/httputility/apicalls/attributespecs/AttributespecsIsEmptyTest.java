package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the attributespecs related calls and
 * expecting not found or empty answers.
 */
public class AttributespecsIsEmptyTest extends IsEmptyTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributespecsIsEmptyTest.class.getSimpleName() + " ***");
    }

    /**
     * GET method tests.
     */
    @Test
    public void testAttributespecsIsEmptyGet() {
        expectingEmpty(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.GET);
    }

    /**
     * PUT method tests.
     */
    @Test
    public void testAttributespecsIsEmptyPut() {
        expectingNotFound(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.PUT);
    }

    /**
     * DELETE method tests.
     */
    @Test
    public void testAttributespecsIsEmptyDelete() {
        expectingNotFound(Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.DELETE);
    }
    
    /**
     * PATCH method tests.
     */
    @Test
    public void testAttributespecsIsEmptyPatch() {
        expectingNotFound(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.PATCH);
    }
}
