package sztaki.hexaa;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Abstract class for the TestSuites to inherit from, it implements the
 * checkReachable and cleanUp methods for utility.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({})
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
		CoverageChecker.Init();
		new DatabaseManipulator().dropCache();
		if (!new DataProp().getString("HEXAA_HOST").equals("localhost")) {
			try {
				InetAddress address;
				address = InetAddress.getByName(new DataProp().getString("HEXAA_HOST"));
				Assume.assumeTrue(address.isReachable(5000));
			} catch (UnknownHostException ex) {
//				Logger.getLogger(CleanTest.class.getName()).log(Level.SEVERE,
//						null, ex);
			} catch (IOException ex) {
//				Logger.getLogger(CleanTest.class.getName()).log(Level.SEVERE,
//						null, ex);
			} catch (AssumptionViolatedException e) {
				CLEANUP_NEEDED = false;
				System.out
						.println("The host could not be reached, check the connection"
								+ " and/or the sserver constants at"
								+ " /sztaki/hexaa/httputility/Const");
				fail("Host unreachable.");
			}
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
//		if (CLEANUP_NEEDED) {
//			new DatabaseManipulator().dropDatabase();
			CoverageChecker.printout();
//		}
	}
}
