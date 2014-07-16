package sztaki.hexaa.httputility.apicalls;

import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall.REST;
import static sztaki.hexaa.httputility.apicalls.CleanTest.persistent;

/**
 * Utility class to be inherited by the IsEmpty test classes. Implements
 * CleanTest and provides two methods for easier testing.
 */
public class IsEmptyTest extends CleanTest {

    /**
     * Calls the rest method on the constApi uri and expects an empty JSON
     * response and 200 OK.
     *
     * @param constApi an URI on the host.
     * @param rest a REST call to be called.
     */
    public void expectingEmpty(String constApi, REST rest) {
        String stringResponse
                = persistent.call(
                        constApi,
                        rest);
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals("[]", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Calls the rest method on the constApi uri and expects an error code
     * response and 404 Not Found.
     *
     * @param constApi an URI on the host.
     * @param rest a REST call to be called.
     */
    public void expectingNotFound(String constApi, REST rest) {
        String stringResponse
                = persistent.call(
                        constApi,
                        rest);
        try {
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Calls the rest method on the constApi uri and expects a fedid response
     * and 200 OK.
     *
     * @param constApi an URI on the host.
     * @param rest a REST call to be called.
     */
    public void expectingFedid(String constApi, REST rest) {
        String stringResponse
                = persistent.call(
                        constApi,
                        rest);
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals("{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
