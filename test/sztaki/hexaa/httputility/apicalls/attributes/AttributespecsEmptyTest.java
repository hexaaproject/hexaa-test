package sztaki.hexaa.httputility.apicalls.attributes;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.BasicCall;

public class AttributespecsEmptyTest {
    
    @BeforeClass
    public static void setUpClass() {
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    /**
     * Tests for an empty database (at least from attributespecs point of view)
     */
    @Test
    public void testIsEmpty() {
        assertEquals("[]", new Attributespecs().call(BasicCall.REST.GET));
        assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new Attributespecs_ID().call(BasicCall.REST.GET, 1, 0));
    }
    
    /**
     * Tests for those methods that are not allowed on the /api/attributespecs and /api/attributespecs/{id}
     */
    @Test
    public void testMethodNotAllowed() {
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Attributespecs().call(BasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Attributespecs().call(BasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Attributespecs_ID().call(BasicCall.REST.POST));
        
    }
}
