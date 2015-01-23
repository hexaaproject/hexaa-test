package sztaki.hexaa.apicalls.organizations.entitlementpacks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for the OrganizationEntitlements related test cases, runs them all
 * and does the starting and finishing utility jobs inherited from
 * BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    OrganizationEntitlementpacksLinkRequestTest.class,
    OrganizationEntitlementpacksUnlinkTest.class,
    OrganizationEntitlementpacksGetTest.class,
    OrganizationEntitlementpacksTokenTest.class,
    OrganizationEntitlementpacksAddTest.class,
})
public class OrganizationEntitlementpacksSuite {
}
