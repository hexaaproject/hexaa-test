package sztaki.hexaa.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

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
        Utility.Remove.attributespec(1);
        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            assertEquals("[]", persistent.call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET));
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
