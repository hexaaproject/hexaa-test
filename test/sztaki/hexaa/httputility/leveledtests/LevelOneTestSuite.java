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
    sztaki.hexaa.httputility.AllIsEmptyTests.class,
    sztaki.hexaa.httputility.apicalls.MethodNotAllowedTest.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsPostTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPostTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsPostTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesPostTest.class,

})
public class LevelOneTestSuite {

}
