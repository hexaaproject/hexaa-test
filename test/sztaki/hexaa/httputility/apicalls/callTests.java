package sztaki.hexaa.httputility.apicalls;

import sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsPutTest;
import sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsIsEmptyTest;
import sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsDeleteTest;
import sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsPostTest;
import sztaki.hexaa.httputility.apicalls.attributespecs.AttributespecsGetTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.services.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AttributespecsIsEmptyTest.class,
    AttributespecsGetTest.class,
    AttributespecsPostTest.class,
    AttributespecsPutTest.class,
    AttributespecsDeleteTest.class,
    ServicesIsEmptyTest.class,
    ServicesPostTest.class,
    InvitationTest.class,
    MethodNotAllowedTest.class,
    //    ServicesNonEmptyTest.class,

})
public class callTests {
}
