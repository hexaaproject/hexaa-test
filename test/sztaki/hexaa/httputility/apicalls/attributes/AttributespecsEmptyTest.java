package sztaki.hexaa.httputility.apicalls.attributes;

import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;

public class AttributespecsEmptyTest {

    /**
     * Tests for an empty database (at least from attributespecs point of view)
     */
    @Test
    public void testIsEmpty() {
        assertEquals("[]", new Attributespecs().call(HttpUtilityBasicCall.REST.GET));
        assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 1, 0));
    }
    
    /**
     * Tests for those methods that are not allowed on the /api/attributespecs and /api/attributespecs/{id}
     */
    @Test
    public void testMethodNotAllowed() {
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Attributespecs().call(HttpUtilityBasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Attributespecs().call(HttpUtilityBasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new Attributespecs_ID().call(HttpUtilityBasicCall.REST.POST));
        
    }
}
