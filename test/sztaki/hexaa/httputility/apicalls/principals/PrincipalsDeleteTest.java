package sztaki.hexaa.httputility.apicalls.principals;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the DELETE method on the /api/principals call.
 */
public class PrincipalsDeleteTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsDeleteTest.class.getSimpleName() + " ***");
    }

    /**
     * Numbers the test principal, incremented after every delete.
     */
    private static int p = 2;

    /**
     * Creates one principal before each method.
     */
    @Before
    public void setUpMethod() {
        Utility.Create.principal("testPrincipal");
    }

    /**
     * DELETE by fedid and check the status line.
     */
    @Test
    public void testPrincipalsDeleteByFedid() {
        persistent.call(
                Const.Api.PRINCIPALS_FEDID,
                BasicCall.REST.DEL,
                null,
                p, p,
                "testPrincipal");
        p++;

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * DELETE by id and check the status line.
     */
    @Test
    public void testPrincipalsDeleteById() {
        persistent.call(
                Const.Api.PRINCIPALS_ID,
                BasicCall.REST.DEL,
                null,
                p, p);
        p++;

        try {
            assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
