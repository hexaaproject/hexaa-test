package sztaki.hexaa.httputility.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST method on the /api/attributespecs call.
 */
public class AttributespecsPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributespecsPostTest.class.getSimpleName() + " ***");
    }

    /**
     * POST 2 different attributespecs.
     */
    @Test
    public void testAttributespecsPost() {
        Utility.Create.attributespec("1", "user");
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        Utility.Create.attributespec("2", "user");
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
