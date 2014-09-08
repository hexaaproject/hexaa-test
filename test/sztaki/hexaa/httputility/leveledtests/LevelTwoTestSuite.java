/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsPutTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationGetTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPutTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalGetAdmin.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalGetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsDeleteSelfTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsManagerGetOrganizationsTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsManagerGetServicesTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsMemeberGetOrganizationsTest.class,
    sztaki.hexaa.httputility.apicalls.roles.RolesPostTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesDeleteTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesGetTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesPutTest.class,
    sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsAddTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlementpacks.ServicesEntitlementpacksPostTest.class,
    sztaki.hexaa.httputility.apicalls.services.entitlements.ServicesEntitlementsPostTest.class,})
public class LevelTwoTestSuite {

}