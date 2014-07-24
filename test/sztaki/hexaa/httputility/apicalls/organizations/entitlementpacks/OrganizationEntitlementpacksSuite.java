package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for the OrganizationEntitlements related test cases, runs them all
 * and does the starting and finishing utility jobs inherited from
 * BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    OrganizationEntitlementpacksPutTest.class,
    OrganizationEntitlementpacksAcceptTest.class,
    OrganizationEntitlementpacksDeleteTest.class,
    OrganizationEntitlementpacksGetTest.class,
    OrganizationEntitlementpacksTokenTest.class,})
public class OrganizationEntitlementpacksSuite {
}
