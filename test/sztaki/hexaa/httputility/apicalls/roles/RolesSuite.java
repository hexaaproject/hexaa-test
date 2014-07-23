package sztaki.hexaa.httputility.apicalls.roles;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for the Role related calls, extends BasicTestSuite to provide some
 * utility.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.roles.RolesPostTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesPutTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsPutTest.class,})
public class RolesSuite extends BasicTestSuite {
}
