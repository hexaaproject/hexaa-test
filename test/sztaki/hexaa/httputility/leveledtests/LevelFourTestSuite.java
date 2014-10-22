package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsGetTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.EntitlementpacksGetEntitlementsTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.EntitlementpacksRemoveEntitlementsTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.attributes.OrganizationsAttributesGet.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksAcceptTest.class,
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
    sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersGetTest.class,})
public class LevelFourTestSuite extends BasicTestSuite {

}
