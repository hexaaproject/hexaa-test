package sztaki.hexaa.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sztaki.hexaa.BasicTestSuite;

/**
 * The fifth level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelFourTestSuite and other previous
 * suites. Failed tests in the previous test suites may cause additional fails
 * in this suite as well. Failed test in this suite may cause tests in the later
 * suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalsPutTest.class,
		sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalsPatchTest.class,
		sztaki.hexaa.apicalls.attributevalueprincipals.services.AttributevalueprincipalsServicesPutTest.class,
		sztaki.hexaa.apicalls.attributevalueprincipals.services.AttributevalueprincipalsServicesDeleteTest.class,
		sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationsPutTest.class,
		sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationsPatchTest.class,
		sztaki.hexaa.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesPutTest.class,
		sztaki.hexaa.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesDeleteTest.class,
		sztaki.hexaa.apicalls.consents.ConsentsPutPatchTest.class,
		sztaki.hexaa.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksUnlinkTest.class,
		sztaki.hexaa.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksGetTest.class,
		sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsGetTest.class,
		sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsRemoveTest.class,
		sztaki.hexaa.apicalls.roles.entitlements.RolesEntitlementsSetTest.class,
		sztaki.hexaa.apicalls.roles.principals.RolesPrincipalsSetTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalGetServicesRelatedTest.class,
		sztaki.hexaa.apicalls.services.entitlementpacks.ServicesEntitlementpacksRequestTest.class,

		sztaki.hexaa.apicalls.news.OrganizationsNewsTest.class,
		sztaki.hexaa.apicalls.news.GetServicesNewsTest.class, })
public class Level5TestSuite extends BasicTestSuite {

}
