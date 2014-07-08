package sztaki.hexaa.httputility.apicalls.services;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesEmptyTest extends CleanTest {

    @Test
    public void testIsEmpty() {
        assertEquals("[]", new Services().call(BasicCall.REST.GET));
        assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new Services_ID().call(BasicCall.REST.GET, 1, 0));
    }
}
