package sztaki.hexaa.httputility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.apicalls.MethodNotAllowedTest.class,
    sztaki.hexaa.httputility.apicalls.services.ServicesSuite.class,
    sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsSuite.class,})
public class BasicTests {

}
