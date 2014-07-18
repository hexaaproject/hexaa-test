package sztaki.hexaa.httputility.apicalls.attributevalueorganizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite to include the tests related to the
 * /api/attributevalueorganizations/{id} calls.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPost.class,})
public class AttributevalueorganizationsSuite extends BasicTestSuite {
}
