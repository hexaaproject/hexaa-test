package sztaki.hexaa.apicalls.attributevalueorganizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;
import sztaki.hexaa.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesGetTest;

/**
 * TestSuite for the Attributevalueorganizations related test cases, runs them
 * all and does the starting and finishing utility jobs inherited from
 * BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AttributevalueorganizationsDeleteTest.class,
    AttributevalueorganizationsGetTest.class,
    AttributevalueorganizationsPatchTest.class,
    AttributevalueorganizationsPost.class,
    AttributevalueorganizationsPutTest.class,
    AttributevalueorganizationsServicesGetTest.class,
})
public class AttributevalueorganizationsSuite extends BasicTestSuite {
}
