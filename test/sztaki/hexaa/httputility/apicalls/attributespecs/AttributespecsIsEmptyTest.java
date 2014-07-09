package sztaki.hexaa.httputility.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsIsEmptyTest extends CleanTest {

    /**
     * Tests for an empty database from attributespecs point of view
     */
    @Test
    public void testAttributespecsIsEmpty() {
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET));

        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.ATTRIBUTESPECS_ID,
                    BasicCall.REST.GET,
                    null,
                    1,
                    0));
        } catch (AssertionError e) {
            collector.addError(e);
        }
    }
}
