package sztaki.hexaa.httputility.apicalls.organizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.*;
import sztaki.hexaa.httputility.apicalls.organizations.principals.*;
import sztaki.hexaa.httputility.apicalls.organizations.roles.*;


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
    OrganizationsManagersAddTest.class,
    OrganizationsMembersAddTest.class,
    OrganizationsManagersGetTest.class,
    OrganizationsMembersGetTest.class,
    OrganizationsManagersRemoveTest.class,
    OrganizationsMembersRemoveTest.class,
    OrganizationsRolesGetTest.class,
    OrganizationEntitlementpacksSuite.class,})
public class OrganizationSuite extends BasicTestSuite {

}
