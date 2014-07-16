package sztaki.hexaa.httputility.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DEL methods on the /api/attributespecs and
 * /api/attributespecs/{id} calls.
 */
public class AttributespecsIsEmptyTest extends IsEmptyTest {

    /**
     * Test the GET calls in Attributespecs on an empty database, they are
     * supposed to return either empty json or 404 not found error.
     */
    @Test
    public void testAttributespecsIsEmptyGet() {
        expectingEmpty(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.GET);
    }

    /**
     * Test the PUT calls in Attributespecs on an empty database, they are
     * supposed to return either empty json or 404 not found error.
     */
    @Test
    public void testAttributespecsIsEmptyPut() {
        expectingNotFound(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.PUT);
    }

    /**
     * Test the DEL calls in Attributespecs on an empty database, they are
     * supposed to return either empty json or 404 not found error.
     */
    @Test
    public void testAttributespecsIsEmptyDelete() {
        expectingNotFound(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.DEL);
    }
}
