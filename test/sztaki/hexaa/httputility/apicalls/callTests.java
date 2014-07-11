package sztaki.hexaa.httputility.apicalls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.apicalls.attributespecs.*;
import sztaki.hexaa.httputility.apicalls.principal.*;
import sztaki.hexaa.httputility.apicalls.services.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AttributespecsIsEmptyTest.class,
    AttributespecsGetTest.class,
    AttributespecsPostTest.class,
    AttributespecsPutTest.class,
    AttributespecsDeleteTest.class,
    PrincipalIsEmpty.class,
    ServicesIsEmptyTest.class,
    ServicesGetTest.class,
    ServicesPostTest.class,
    MethodNotAllowedTest.class, //    ServicesNonEmptyTest.class,
})

/**
 * Calls the basic test classes that tests the low level api functions, like
 * get/post/delete trivial objects and test for method not found
 */
public class callTests {

    public static boolean CLEANUP_NEEDED = true;

    @BeforeClass
    public static void checkReacable() {
        try {
            InetAddress address;
            address = InetAddress.getByName(Const.HEXAA_HOST);
            Assume.assumeTrue(address.isReachable(5000));
        } catch (UnknownHostException ex) {
            Logger.getLogger(CleanTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CleanTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AssumptionViolatedException e) {
            CLEANUP_NEEDED = false;
            System.out.println(
                    "The host could not be reached, check the connection"
                    + " and/or the sserver constants at"
                    + " /sztaki/hexaa/httputility/Const");
            fail("Host unreachable.");
        }

    }

    @AfterClass
    public static void cleanUp() {
        if (CLEANUP_NEEDED) {
            new DatabaseManipulator().dropDatabase();
            new Authenticator().authenticate();
        }
    }
}
