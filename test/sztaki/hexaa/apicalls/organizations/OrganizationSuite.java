package sztaki.hexaa.apicalls.organizations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;
import sztaki.hexaa.apicalls.organizations.attributes.OrganizationsAttributesGet;
import sztaki.hexaa.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksSuite;
import sztaki.hexaa.apicalls.organizations.principals.*;
import sztaki.hexaa.apicalls.organizations.roles.OrganizationsRolesGetTest;


/**
 * TestSuite for the Organizations related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    OrganizationPutPatchTest.class,
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
    OrganizationsAttributesGet.class,
    OrganizationEntitlementpacksSuite.class,})
public class OrganizationSuite extends BasicTestSuite {

}
