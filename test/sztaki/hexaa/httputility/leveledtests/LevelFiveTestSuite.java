package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPutTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPutTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksUnlinkTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsRemoveTest.class,
})
public class LevelFiveTestSuite {

}
