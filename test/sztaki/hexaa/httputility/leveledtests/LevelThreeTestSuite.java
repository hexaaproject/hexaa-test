package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * The third level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelTwoTestSuite and other previous suites.
 * Failed tests in the previous test suites may cause additional fails in this
 * suite as well. Failed test in this suite may cause tests in the later suites
 * to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsServicesGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPost.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPost.class,
    sztaki.hexaa.httputility.apicalls.consents.ConsentsPostTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.EntitlementpacksAddEntitlementsTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksGetTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksPutPatchTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsPutPatchTest.class,
    sztaki.hexaa.httputility.apicalls.invitations.InvitationsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.invitations.InvitationsGetTest.class,
    sztaki.hexaa.httputility.apicalls.invitations.InvitationsPutTest.class,
    sztaki.hexaa.httputility.apicalls.invitations.InvitationsPatchTest.class,
    sztaki.hexaa.httputility.apicalls.invitations.InvitationsAcceptTokenTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationInvitationGetTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.roles.OrganizationsRolesGetTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsManagersAddTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsMembersRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationsMembersGetTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.principals.OrganizationPrincipalsCountTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsAttributespecsPublicGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesPutTest.class,
    sztaki.hexaa.httputility.apicalls.roles.principals.RolesPrincipalsAddTest.class,
    sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlements.ServicesEntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersAddTest.class,
    sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersAddByArrayTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlementpacks.ServicesEntitlementpacksGetTest.class,
})
public class LevelThreeTestSuite extends BasicTestSuite {

}
