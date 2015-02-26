package sztaki.hexaa.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * The first level of the differentiated tests. These tests only rely on basic
 * server functionality and the existence and availability of entityids. Failed
 * test in this suite may cause tests in the later suites to fail.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ sztaki.hexaa.apicalls.other.PropertiesGetTest.class,
		sztaki.hexaa.AllIsEmptyTests.class,
		sztaki.hexaa.apicalls.MethodNotAllowedTest.class,
		sztaki.hexaa.apicalls.attributespecs.AttributespecsPostTest.class,
		sztaki.hexaa.apicalls.organizations.OrganizationPostTest.class,
		sztaki.hexaa.apicalls.principals.PrincipalsPostTest.class,
		sztaki.hexaa.apicalls.services.ServicesPostTest.class,
		sztaki.hexaa.apicalls.securitydomain.SecuritydomainPostTest.class, })
public class Level1TestSuite extends BasicTestSuite {

}
