package sztaki.hexaa.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * The third level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelTwoTestSuite and other previous suites.
 * Failed tests in the previous test suites may cause additional fails in this
 * suite as well. Failed test in this suite may cause tests in the later suites
 * to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.apicalls.attributespecs.AttributespecsServicesGetTest.class,
    sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationsPost.class,
    sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalsPost.class,
    sztaki.hexaa.apicalls.consents.ConsentsPostTest.class,
    sztaki.hexaa.apicalls.entitlementpacks.entitlements.EntitlementpacksAddEntitlementsTest.class,
    sztaki.hexaa.apicalls.entitlementpacks.EntitlementpacksDeleteTest.class,
    sztaki.hexaa.apicalls.entitlementpacks.EntitlementpacksGetTest.class,
    sztaki.hexaa.apicalls.entitlementpacks.EntitlementpacksPutPatchTest.class,
    sztaki.hexaa.apicalls.entitlements.EntitlementsDeleteTest.class,
    sztaki.hexaa.apicalls.entitlements.EntitlementsGetTest.class,
    sztaki.hexaa.apicalls.entitlements.EntitlementsPutPatchTest.class,
    sztaki.hexaa.apicalls.invitations.InvitationsDeleteTest.class,
    sztaki.hexaa.apicalls.invitations.InvitationsGetTest.class,
    sztaki.hexaa.apicalls.invitations.InvitationsPutTest.class,
    sztaki.hexaa.apicalls.invitations.InvitationsPatchTest.class,
    sztaki.hexaa.apicalls.invitations.InvitationsAcceptTokenTest.class,
    sztaki.hexaa.apicalls.organizations.OrganizationInvitationGetTest.class,
    sztaki.hexaa.apicalls.organizations.OrganizationPutPatchTest.class,
    sztaki.hexaa.apicalls.organizations.roles.OrganizationsRolesGetTest.class,
    sztaki.hexaa.apicalls.organizations.principals.OrganizationsManagersAddTest.class,
    sztaki.hexaa.apicalls.organizations.principals.OrganizationsMembersRemoveTest.class,
    sztaki.hexaa.apicalls.organizations.principals.OrganizationsMembersGetTest.class,
    sztaki.hexaa.apicalls.organizations.principals.OrganizationPrincipalsCountTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalInvitationGetTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalsAttributespecsPublicGetTest.class,
    sztaki.hexaa.apicalls.principals.PrincipalsPutPatchTest.class,
    sztaki.hexaa.apicalls.roles.RolesGetTest.class,
    sztaki.hexaa.apicalls.roles.RolesDeleteTest.class,
    sztaki.hexaa.apicalls.roles.RolesPutTest.class,
    sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsAddTest.class,
    sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsGetTest.class,
    sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsRemoveTest.class,
    sztaki.hexaa.apicalls.services.entitlements.ServicesEntitlementsGetTest.class,
    sztaki.hexaa.apicalls.services.managers.ServicesManagersAddTest.class,
    sztaki.hexaa.apicalls.services.managers.ServicesManagersAddByArrayTest.class,
    sztaki.hexaa.apicalls.services.entitlementpacks.ServicesEntitlementpacksGetTest.class,
})
public class LevelThreeTestSuite extends BasicTestSuite {

}
