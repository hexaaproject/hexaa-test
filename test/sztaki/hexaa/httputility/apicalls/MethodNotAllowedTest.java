/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sztaki.hexaa.httputility.apicalls;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 *
 * @author Bana Tibor
 */
public class MethodNotAllowedTest {
    
    @Test
    public void testMethodNotAllowed() {
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(Const.Api.ATTRIBUTESPECS,BasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(Const.Api.ATTRIBUTESPECS,BasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID,BasicCall.REST.POST));
        
    }
}
