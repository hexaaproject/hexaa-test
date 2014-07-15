package sztaki.hexaa.httputility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.MethodNotAllowedTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesSuite.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsSuite.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsSuite.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksSuite.class,})
/**
 * TestSuite that runs with all the test suites and the few classes outside of
 * test suites to make sure that all of them were executed.
 */
public class BasicTests {
}
