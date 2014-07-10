package sztaki.hexaa.httputility.apicalls;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.attributespecs.*;
import sztaki.hexaa.httputility.apicalls.principal.*;
import sztaki.hexaa.httputility.apicalls.services.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AttributespecsIsEmptyTest.class,
    AttributespecsGetTest.class,
    AttributespecsPostTest.class,
    AttributespecsPutTest.class,
    AttributespecsDeleteTest.class,
    PrincipalIsEmpty.class,
    ServicesIsEmptyTest.class,
    ServicesGetTest.class,
    ServicesPostTest.class,
    MethodNotAllowedTest.class, //    ServicesNonEmptyTest.class,
})

/**
 * Calls the basic test classes that tests the low level api functions, like
 * get/post/delete trivial objects and test for method not found
 */
public class callTests {
}
