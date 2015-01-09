package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * The fifth level of the differentiated tests. These tests only rely on
 * functions that are tested in the LevelFourTestSuite and other previous
 * suites. Failed tests in the previous test suites may cause additional fails
 * in this suite as well. Failed test in this suite may cause tests in the later
 * suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPutTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPatchTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.services.AttributevalueprincipalsServicesPutTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.services.AttributevalueprincipalsServicesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPutTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPatchTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesPutTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.consents.ConsentsPutPatchTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksUnlinkTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsRemoveTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsSetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.principals.RolesPrincipalsSetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalServicesRelatedTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlementpacks.ServicesEntitlementpacksRequestTest.class,
})
public class LevelFiveTestSuite extends BasicTestSuite {

}
