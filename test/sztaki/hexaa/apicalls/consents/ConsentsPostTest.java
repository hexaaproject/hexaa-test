package sztaki.hexaa.apicalls.consents;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the POST method on the /api/consents call.
 */
public class ConsentsPostTest extends CleanTest{

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ConsentsPostTest.class.getSimpleName() + " ***");
    }
    
    /**
     * Creates the necessary objects on the server to begin the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service("testService1");
        Utility.Create.attributespec("randomOID", "user");
        Utility.Link.attributespecsToService(1, 1, true);
    }

    /**
     * Creates a consent and verifies it from the status line.
     */
    @Test
    public void testConsentsPost() {
        Utility.Create.consent(true, new int[] {1}, 1);
        
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
