package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite to include the tests related to the
 * /api//api/attributevalueprincipals/{id} calls.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPost.class,})
public class AttributevalueprincipalsSuite extends BasicTestSuite {
}
