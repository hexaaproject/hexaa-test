package sztaki.hexaa.apicalls.attributevalueprincipals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * TestSuite for the Attributevalueprincipals related test cases, runs them all
 * and does the starting and finishing utility jobs inherited from
 * BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AttributevalueprincipalsPost.class, })
public class AttributevalueprincipalsSuite extends BasicTestSuite {
}
