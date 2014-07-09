package sztaki.hexaa.httputility.apicalls;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.attributes.*;
import sztaki.hexaa.httputility.apicalls.services.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    InvitationTest.class,
    MethodNotAllowedTest.class,
    AttributespecsDeleteTest.class,
    AttributespecsGetTest.class,
    AttributespecsInsertTest.class,
    AttributespecsIsEmptyTest.class,
    AttributespecsPutTest.class,
    ServicesEmptyTest.class,
    ServicesInsertTest.class,
    //    ServicesNonEmptyTest.class,

})
public class callTests {
}
