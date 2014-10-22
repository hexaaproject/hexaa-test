package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsServicesGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPost.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPost.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.EntitlementpacksAddEntitlementsTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksGetTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksPutTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsPutTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.roles.OrganizationsRolesGetTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsMembersRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsManagersGetTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsManagersRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsMembersGetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsAttributespecsPublicGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesPutTest.class,
    sztaki.hexaa.httputility.apicalls.roles.principals.RolesPrincipalsAddTest.class,
    sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlements.ServicesEntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersAddTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlementpacks.ServicesEntitlementpacksGetTest.class,})
public class LevelThreeTestSuite extends BasicTestSuite{

}
