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
    sztaki.hexaa.httputility.apicalls.roles.RolesPostGetTest.class,})
public class RolesSuite extends BasicTestSuite {
}
