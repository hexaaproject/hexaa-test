package sztaki.hexaa.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * The fourth level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelThirdTestSuite and other previous
 * suites. Failed tests in the previous test suites may cause additional fails
 * in this suite as well. Failed test in this suite may cause tests in the later
 * suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalsDeleteTest.class,
    sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalsGetTest.class,
    sztaki.hexaa.apicalls.attributevalueprincipals.services.AttributevalueprincipalsServicesGetTest.class,
    sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationsDeleteTest.class,
    sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationsGetTest.class,
    sztaki.hexaa.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesGetTest.class,
    sztaki.hexaa.apicalls.consents.ConsentsGetTest.class,
    sztaki.hexaa.apicalls.entitlementpacks.entitlements.EntitlementpacksGetEntitlementsTest.class,
    sztaki.hexaa.apicalls.entitlementpacks.entitlements.EntitlementpacksRemoveEntitlementsTest.class,
    sztaki.hexaa.apicalls.organizations.attributes.OrganizationsAttributesGet.class,
    sztaki.hexaa.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksLinkRequestTest.class,
    sztaki.hexaa.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksAddTest.class,
    sztaki.hexaa.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksTokenTest.class,
    sztaki.hexaa.apicalls.organizations.principals.OrganizationsManagersGetTest.class,
    sztaki.hexaa.apicalls.organizations.principals.OrganizationsManagersRemoveTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalsAttributevaluesGetTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalsRoleGetTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalsAttributespecsPrivateGetTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalsEntitlementsGetTest.class,
    sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsAddTest.class,
    sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsRemoveTest.class,
    sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsGetTest.class,
    sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsSetTest.class,
    sztaki.hexaa.apicalls.services.organizations.ServicesOrganizationsGetTest.class,
    sztaki.hexaa.apicalls.services.managers.ServicesManagersRemoveTest.class,
    sztaki.hexaa.apicalls.services.managers.ServicesManagersGetTest.class,
    sztaki.hexaa.apicalls.services.managers.ServicesManagersCountTest.class,
})
public class LevelFourTestSuite extends BasicTestSuite {

}
