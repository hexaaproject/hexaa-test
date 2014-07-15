package sztaki.hexaa.httputility.apicalls.entitlements;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    EntitlementsGetTest.class,
    EntitlementsIsEmptyTest.class,
    EntitlementsPutTest.class,
    EntitlementsDeleteTest.class,})
/**
 * TestSuite for the Entitlements related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
public class EntitlementsSuite extends BasicTestSuite {

}
