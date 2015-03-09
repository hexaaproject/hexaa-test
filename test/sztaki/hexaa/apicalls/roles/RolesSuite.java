package sztaki.hexaa.apicalls.roles;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sztaki.hexaa.BasicTestSuite;
import sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsAddTest;
import sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsGetTest;
import sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsRemoveTest;
import sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsAddTest;
import sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsGetTest;
import sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsRemoveTest;

/**
 * TestSuite for the Roles related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ RolesPostTest.class, RolesDeleteTest.class,
		RolesPutTest.class, RolesGetTest.class, RolesEntitlementsAddTest.class,
		RolesEntitlementsGetTest.class, RolesEntitlementsRemoveTest.class,
		RolesPrincipalsAddTest.class, RolesPrincipalsGetTest.class,
		RolesPrincipalsRemoveTest.class, })
public class RolesSuite extends BasicTestSuite {
}
