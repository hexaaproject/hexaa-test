package sztaki.hexaa.httputility.apicalls.attributevalueorganizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.attributevalueorganizations.services.*;

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
    AttributevalueServicesGetTest.class,
})
public class AttributevalueorganizationsSuite extends BasicTestSuite {
}
