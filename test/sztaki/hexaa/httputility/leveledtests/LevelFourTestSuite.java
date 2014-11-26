package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * The fourth level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelThirdTestSuite and other previous
 * suites. Failed tests in the previous test suites may cause additional fails
 * in this suite as well. Failed test in this suite may cause tests in the later
 * suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.services.AttributevalueprincipalsServicesGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesGetTest.class,
    sztaki.hexaa.httputility.apicalls.consents.ConsentsGetTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.EntitlementpacksGetEntitlementsTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.EntitlementpacksRemoveEntitlementsTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.attributes.OrganizationsAttributesGet.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksLinkRequestTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksAddTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksTokenTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsAttributevaluesGetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsRoleGetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsAttributespecsPrivateGetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsEntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsAddTest.class,
    sztaki.hexaa.httputility.apicalls.roles.principals.RolesPrincipalsRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.roles.principals.RolesPrincipalsGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.organizations.ServicesOrganizationsGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersGetTest.class,
})
public class LevelFourTestSuite extends BasicTestSuite {

}
