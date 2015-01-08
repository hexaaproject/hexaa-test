package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * The first level of the differentiated tests. These tests only rely on basic
 * server functionality and the existence and availability of entityids. Failed
 * test in this suite may cause tests in the later suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.other.PropertiesGetTest.class,
    sztaki.hexaa.httputility.AllIsEmptyTests.class,
    sztaki.hexaa.httputility.apicalls.MethodNotAllowedTest.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsPostTest.class,
    sztaki.hexaa.httputility.apicalls.organizations.OrganizationPostTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalsPostTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesPostTest.class,
})
public class LevelOneTestSuite extends BasicTestSuite {

}
