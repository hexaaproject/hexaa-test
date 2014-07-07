package sztaki.hexaa.httputility.apicalls.services;

import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;

public class ServicesEmptyTest {
    
    @Test
    public void testIsEmpty() {
        assertEquals("[]", new Services().call(HttpUtilityBasicCall.REST.GET));
        assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new Services_ID().call(HttpUtilityBasicCall.REST.GET, 1, 0));
    }
    
    @Test
    public void testMethodNotAllowed() {
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Services().call(HttpUtilityBasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Services().call(HttpUtilityBasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Services_ID().call(HttpUtilityBasicCall.REST.POST, 0, 0));
    }
}
