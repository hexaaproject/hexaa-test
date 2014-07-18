package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite within the OrganizationSuite to include the tests related to the
 * /api/organization/{id}/entitlementpacks calls.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksPut.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksAccept.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksDelete.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksGet.class,})
public class OrganizationEntitlementpacksSuite {
}
