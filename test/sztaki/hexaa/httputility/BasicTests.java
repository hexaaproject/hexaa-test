package sztaki.hexaa.httputility;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite that runs with all the test suites and the few classes outside of
 * test suites to make sure that all of them were executed.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.AllIsEmptyTests.class,
    sztaki.hexaa.httputility.apicalls.MethodNotAllowedTest.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsSuite.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsSuite.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsSuite.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsSuite.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksSuite.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationSuite.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsSuite.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesSuite.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesSuite.class,})
public class BasicTests {
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("BeforeClass @ BasicTests");
        new DatabaseManipulator().dropCache();
    }
}
