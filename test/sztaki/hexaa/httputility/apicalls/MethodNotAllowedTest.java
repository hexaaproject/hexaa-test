package sztaki.hexaa.httputility.apicalls;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

public class MethodNotAllowedTest extends CleanTest {

    /**
     * Bunch of tests to verify that the Method Not Allowed exception drop works
     * fine
     */
    @Test
    public void testMethodNotAllowed() {
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.POST));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                Const.Api.SERVICES,
                BasicCall.REST.PUT));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                Const.Api.SERVICES,
                BasicCall.REST.DELETE));
        assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                Const.Api.SERVICES_ID,
                BasicCall.REST.POST));

    }
}
