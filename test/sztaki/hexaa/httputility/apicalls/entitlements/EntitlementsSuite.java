package sztaki.hexaa.httputility.apicalls.entitlements;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for the Entitlements related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    EntitlementsGetTest.class,
    EntitlementsPutTest.class,
    EntitlementsDeleteTest.class,})
public class EntitlementsSuite extends BasicTestSuite {

}
