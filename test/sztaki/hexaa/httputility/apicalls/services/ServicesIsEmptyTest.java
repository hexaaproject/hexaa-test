package sztaki.hexaa.httputility.apicalls.services;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesIsEmptyTest extends CleanTest {

    @Test
    public void testServicesIsEmpty() {
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.SERVICES,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            collector.addError(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.SERVICES_ID,
                    BasicCall.REST.GET,
                    null,
                    1, 0));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            collector.addError(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.SERVICES_ATTRIBUTESPECS,
                    BasicCall.REST.GET,
                    null,
                    1, 0));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            collector.addError(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.SERVICES_ENTITLEMENTPACKS,
                    BasicCall.REST.GET,
                    null,
                    1, 0));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            collector.addError(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.SERVICES_ENTITLEMENTS,
                    BasicCall.REST.GET,
                    null,
                    1, 0));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            collector.addError(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.SERVICES_MANAGERS,
                    BasicCall.REST.GET,
                    null,
                    1, 0));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            collector.addError(e);
        }
        
    }
}
