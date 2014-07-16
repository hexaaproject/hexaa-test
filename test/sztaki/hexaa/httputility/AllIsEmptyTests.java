/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.entitlementpacks.EntitlementpacksIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.entitlements.EntitlementsIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.principal.PrincipalIsEmptyTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesIsEmptyTest.class,})
/**
 * TestSuite for all the IsEmptyTest class, and gives the BasicTestSuite
 * utility.
 */
public class AllIsEmptyTests extends BasicTestSuite {

}
