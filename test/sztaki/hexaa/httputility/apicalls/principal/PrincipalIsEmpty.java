package sztaki.hexaa.httputility.apicalls.principal;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class PrincipalIsEmpty extends CleanTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void isEmpty() {
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.MANAGER_ORGANIZATIONS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.MANAGER_SERVICES,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.MEMBER_ORGANIZATIONS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}",
                    persistent.call(
                            Const.Api.PRINCIPAL,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", persistent.call(
                    Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", persistent.call(
                    Const.Api.PRINCIPAL_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPAL,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.PRINCIPAL_EMAILINVITATIONS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.PRINCIPAL_URLINVITATIONS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("[{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}]",
                    persistent.call(
                            Const.Api.PRINCIPALS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals("{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}",
                    persistent.call(
                            Const.Api.PRINCIPALS_ID,
                            BasicCall.REST.GET,
                            null,
                            1, 0));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

    }
}
