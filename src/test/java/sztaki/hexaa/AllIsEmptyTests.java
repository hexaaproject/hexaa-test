package sztaki.hexaa;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for the IsEmpty related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationIsEmptyTest.class,
		sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalIsEmptyTest.class,
		sztaki.hexaa.apicalls.attributespecs.AttributespecsIsEmptyTest.class,
		sztaki.hexaa.apicalls.entitlementpacks.EntitlementpacksIsEmptyTest.class,
		sztaki.hexaa.apicalls.entitlements.EntitlementsIsEmptyTest.class,
		sztaki.hexaa.apicalls.organizations.OrganizationIsEmptyTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalIsEmptyTest.class,
		sztaki.hexaa.apicalls.roles.RolesIsEmptyTest.class,
		sztaki.hexaa.apicalls.services.ServicesIsEmptyTest.class, })
public class AllIsEmptyTests extends BasicTestSuite {
	/**
	 * Connects to the server via Runtime.exec and executes a server side script
	 * that drops the database, recreates it and reinitializes it. This method
	 * runs before every child class, this way there is no interference between
	 * classes.
	 */
	@BeforeClass
	public static void cleanTestBasicSetUpClass() {
		System.out.println("BeforeClass @ CleanTest");
		new DatabaseManipulator().dropDatabase();
	}
}
