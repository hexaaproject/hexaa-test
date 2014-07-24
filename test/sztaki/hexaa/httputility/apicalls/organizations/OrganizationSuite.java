package sztaki.hexaa.httputility.apicalls.organizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for the Organizations related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPutTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPostTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksSuite.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationGetTest.class,})
public class OrganizationSuite extends BasicTestSuite {

}
