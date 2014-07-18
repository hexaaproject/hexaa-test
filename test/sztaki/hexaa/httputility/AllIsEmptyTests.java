package sztaki.hexaa.httputility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for all the IsEmptyTest class, and gives the BasicTestSuite
 * utility.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.principal.PrincipalIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesIsEmptyTest.class,})
public class AllIsEmptyTests extends BasicTestSuite {

}
