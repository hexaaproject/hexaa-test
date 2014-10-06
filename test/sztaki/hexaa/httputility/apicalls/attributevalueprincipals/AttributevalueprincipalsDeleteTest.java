package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DELETE method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueprincipalsDeleteTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.principal("Org1");
        System.out.println(Utility.persistent.getStatusLine());
        Utility.Create.attributespec(new String[]{"testName1"}, "user");
        System.out.println(Utility.persistent.getStatusLine());
        Utility.Create.attributevalueprincipal("PriValue1", 1);
        System.out.println(Utility.persistent.getStatusLine());
    }

    /**
     * DELETEs the attributespec and checks that none exists.
     */
    @Test
    public void testAttributevalueprincipalsDelete() {
        // The DELETE call.
        Utility.Remove.attributevalueprincipal(1);
        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        persistent.call(
                Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
                BasicCall.REST.GET);
        try {
            assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
