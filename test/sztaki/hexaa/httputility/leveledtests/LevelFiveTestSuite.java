/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author ede
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksUnlinkTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks.OrganizationEntitlementpacksGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsGetTest.class,
    sztaki.hexaa.httputility.apicalls.roles.entitlements.RolesEntitlementsRemoveTest.class,
})
public class LevelFiveTestSuite {

}
