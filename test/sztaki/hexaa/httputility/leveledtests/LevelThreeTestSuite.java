package sztaki.hexaa.httputility.leveledtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsServicesGetTest.class,
    sztaki.hexaa.httputility.apicalls.attributevalueorganizations.AttributevalueorganizationsPost.class,
    sztaki.hexaa.httputility.apicalls.attributevalueprincipals.AttributevalueprincipalsPost.class,

})
public class LevelThreeTestSuite {

}
