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
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.DatabaseManipulator;

@RunWith(Suite.class)
@Suite.SuiteClasses({})

/**
 * Abstract class for the testsuites to inherit from, it implements the
 * checkReachable and cleanUp methods for utility.
 */
public abstract class BasicTestSuite {

    /**
     * If the test Suite failed there is no need (and chance) for cleanUp, this
     * prevents the cleanUp method to run.
     */
    public static boolean CLEANUP_NEEDED = true;

    /**
     * Checks if the target host (specified in core.Const) is reachable or not.
     * If not (or not in 5000ms) the Suite fails.
     */
    @BeforeClass
    public static void checkReachable() {
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

    /**
     * If the @BeforeClass method failed than there is no need and chance for
     * the method to run properly, if it did not fail the method drops the
     * database, so the test data won't cause problems by lingering on in the
     * database.
     */
    @AfterClass
    public static void cleanUp() {
        if (CLEANUP_NEEDED) {
            new DatabaseManipulator().dropDatabase();
        }
    }
}
