package sztaki.hexaa.httputility.apicalls.organizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.*;

/**
 * TestSuite for the Organizations related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    OrganizationPutTest.class,
    OrganizationDeleteTest.class,
    OrganizationPostTest.class,
    OrganizationGetTest.class,
    OrganizationEntitlementpacksSuite.class,})
public class OrganizationSuite extends BasicTestSuite {

}
