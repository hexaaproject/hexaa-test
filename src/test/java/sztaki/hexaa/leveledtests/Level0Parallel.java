package sztaki.hexaa.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sztaki.hexaa.BasicTestSuite;

/**
 * The zero level of the differentiated tests. These tests only searching for
 * server disfunctionality of security issues with non existing url's. Failed
 * test in this suite may caused by bad server configuration.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	sztaki.hexaa.AllIsEmptyMethodNotAllowedParallelTest.class, })
public class Level0Parallel extends BasicTestSuite {

}
