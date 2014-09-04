package sztaki.hexaa.httputility.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DELETE method on the /api/attributespecs/{id} call.
 */
public class AttributespecsDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributespecsDeleteTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespec(new String[]{"testName1"}, "user");
    }

    /**
     * DELETEs the attributespec and checks that none exists.
     */
    @Test
    public void testAttributespecsDelete() {
        // The DELETE call.
        persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.DEL,
                null,
                1, 0);
        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
            assertEquals("[]", persistent.call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET));
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
