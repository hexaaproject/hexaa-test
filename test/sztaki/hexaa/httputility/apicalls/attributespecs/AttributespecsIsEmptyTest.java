package sztaki.hexaa.httputility.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsIsEmptyTest extends CleanTest {

    /**
     * Test the GET calls in Attributespecs on an empty database, they are
     * supposed to return either empty json or 404 not found error
     */
    @Test
    public void testAttributespecsIsEmpty() {
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET,
                    null,
                    1, 0));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.GET,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
