package sztaki.hexaa.httputility;

import sztaki.hexaa.Authenticator;
import sztaki.hexaa.DatabaseManipulator;
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

    /**
     * Loads the .properties file and resets the database.
     */
    @BeforeClass
    public static void setUpClass() {
        System.out.println("BeforeClass @ BasicTests");
        new Authenticator().loadProperties();
        new DatabaseManipulator().dropCache();
    }
}
