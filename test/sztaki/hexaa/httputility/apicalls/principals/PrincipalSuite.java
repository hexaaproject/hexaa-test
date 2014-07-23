package sztaki.hexaa.httputility.apicalls.principals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.principals.PrincipalGetTest.class,
    sztaki.hexaa.httputility.apicalls.principals.PrincipalPostTest.class,})
public class PrincipalSuite extends BasicTestSuite {

}
