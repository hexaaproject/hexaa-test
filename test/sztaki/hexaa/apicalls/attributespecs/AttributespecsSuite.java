package sztaki.hexaa.apicalls.attributespecs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * TestSuite for the Attributespecs related test cases, runs them all and does
 * the starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AttributespecsGetTest.class,
    AttributespecsPostTest.class,
    AttributespecsPutTest.class,
    AttributespecsDeleteTest.class,
    AttributespecsServicesGetTest.class,
    AttributespecsPatchTest.class,})
public class AttributespecsSuite extends BasicTestSuite {
}
