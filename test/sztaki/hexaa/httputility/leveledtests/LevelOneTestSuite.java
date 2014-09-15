package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 72 class + ismepty + methodnotallowed -> 74, 6 here, 68 after.
 * @author ede
 */
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
