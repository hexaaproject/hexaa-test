package sztaki.hexaa.httputility.apicalls.roles;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for the Roles related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.roles.RolesPostTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesPutTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsPutTest.class,})
public class RolesSuite extends BasicTestSuite {
}
