package sztaki.hexaa;

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
		sztaki.hexaa.AllIsEmptyTests.class,
		sztaki.hexaa.apicalls.MethodNotAllowedTest.class,
		sztaki.hexaa.apicalls.attributespecs.AttributespecsSuite.class,
		sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationsSuite.class,
		sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalsSuite.class,
		sztaki.hexaa.apicalls.entitlements.EntitlementsSuite.class,
		sztaki.hexaa.apicalls.entitlementpacks.EntitlementpacksSuite.class,
		sztaki.hexaa.apicalls.organizations.OrganizationSuite.class,
		sztaki.hexaa.apicalls.principals.PrincipalsSuite.class,
		sztaki.hexaa.apicalls.roles.RolesSuite.class,
		sztaki.hexaa.apicalls.services.ServicesSuite.class, })
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
