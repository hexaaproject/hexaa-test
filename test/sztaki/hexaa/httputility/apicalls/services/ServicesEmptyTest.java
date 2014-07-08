package sztaki.hexaa.httputility.apicalls.services;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.BasicCall;

public class ServicesEmptyTest {
    
    @BeforeClass
    public static void setUpClass() {
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }
    
    @Test
    public void testIsEmpty() {
        assertEquals("[]", new Services().call(BasicCall.REST.GET));
        assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new Services_ID().call(BasicCall.REST.GET, 1, 0));
    }
    
    @Test
    public void testMethodNotAllowed() {
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Services().call(BasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Services().call(BasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Services_ID().call(BasicCall.REST.POST, 0, 0));
    }
}
