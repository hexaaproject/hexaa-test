package sztaki.hexaa.httputility.apicalls.attributevalueorganizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for the Attributevalueorganizations related test cases, runs them
 * all and does the starting and finishing utility jobs inherited from
 * BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPost.class,})
public class AttributevalueorganizationsSuite extends BasicTestSuite {
}
