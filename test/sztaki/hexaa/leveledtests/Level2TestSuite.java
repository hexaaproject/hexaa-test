package sztaki.hexaa.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * The second level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelOneTestSuite. Failed tests in the
 * previous test suites may cause additional fails in this suite as well. Failed
 * test in this suite may cause tests in the later suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		sztaki.hexaa.apicalls.attributespecs.AttributespecsDeleteTest.class,
		sztaki.hexaa.apicalls.attributespecs.AttributespecsGetTest.class,
		sztaki.hexaa.apicalls.attributespecs.AttributespecsPutTest.class,
		sztaki.hexaa.apicalls.attributespecs.AttributespecsPatchTest.class,
		sztaki.hexaa.apicalls.invitations.InvitationsPostTest.class,
		sztaki.hexaa.apicalls.organizations.OrganizationDeleteTest.class,
		sztaki.hexaa.apicalls.organizations.OrganizationGetTest.class,
		sztaki.hexaa.apicalls.organizations.principals.OrganizationsMembersAddTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalGetAdminTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalGetTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalsDeleteSelfTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalsDeleteTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalsGetManagersOrganizationsTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalsManagerGetServicesTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalsMemeberGetOrganizationsTest.class,
		sztaki.hexaa.apicalls.roles.RolesPostTest.class,
		sztaki.hexaa.apicalls.services.ServicesDeleteTest.class,
		sztaki.hexaa.apicalls.services.ServicesGetTest.class,
		sztaki.hexaa.apicalls.services.ServicesPutTest.class,
		sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsAddTest.class,
		sztaki.hexaa.apicalls.services.entitlementpacks.ServicesEntitlementpacksPostTest.class,
		sztaki.hexaa.apicalls.services.entitlements.ServicesEntitlementsPostTest.class, })
public class Level2TestSuite extends BasicTestSuite {

}
