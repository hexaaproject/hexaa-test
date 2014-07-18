package sztaki.hexaa.httputility.apicalls.organizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPutTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPostTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksSuite.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationGetTest.class,})
/**
 * TestSuite within the BasicTest test suite to include the tests related to the
 * /api/organizations calls.
 */
public class OrganizationSuite extends BasicTestSuite {

}
