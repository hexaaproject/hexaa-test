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
@Suite.SuiteClasses({
		sztaki.hexaa.apicalls.MethodNotAllowedTest.class,
		sztaki.hexaa.AllIsEmptyTests.class, })
public class Level0TestSuite extends BasicTestSuite {

}
