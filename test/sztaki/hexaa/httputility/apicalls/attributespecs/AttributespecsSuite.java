package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AttributespecsIsEmptyTest.class,
    AttributespecsGetTest.class,
    AttributespecsPostTest.class,
    AttributespecsPutTest.class,
    AttributespecsDeleteTest.class,})

public class AttributespecsSuite extends BasicTestSuite {

}
