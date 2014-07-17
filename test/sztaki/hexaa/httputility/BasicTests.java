package sztaki.hexaa.httputility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.AllIsEmptyTests.class,
    sztaki.hexaa.httputility.apicalls.MethodNotAllowedTest.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsSuite.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsSuite.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsSuite.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksSuite.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationSuite.class,
    sztaki.hexaa.httputility.apicalls.principal.PrincipalSuite.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesSuite.class,})
/**
 * TestSuite that runs with all the test suites and the few classes outside of
 * test suites to make sure that all of them were executed.
 */
public class BasicTests {
}
