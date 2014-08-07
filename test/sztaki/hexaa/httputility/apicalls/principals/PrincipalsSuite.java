package sztaki.hexaa.httputility.apicalls.principals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

/**
 * TestSuite for the Principals related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    PrincipalGetTest.class,
    PrincipalsPostTest.class,
    PrincipalsDeleteSelfTest.class,
    PrincipalsDeleteTest.class,})
public class PrincipalsSuite extends BasicTestSuite {
}
