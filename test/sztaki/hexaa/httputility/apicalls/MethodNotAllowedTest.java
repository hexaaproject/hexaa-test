package sztaki.hexaa.httputility.apicalls;

import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

public class MethodNotAllowedTest extends CleanTest {

    /**
     * Bunch of tests to verify that the Method Not Allowed exception drop works
     * fine, and not allowed methods do nut return any information that they
     * should not
     */
    @Test
    public void testMethodNotAllowed() {
        /* *** REST(GET,POST,PUT,DELETE) boundles for the   *** */
        /* easier use, not complete, feel free to add to it *** */
        BasicCall.REST[] restGetPost = {
            BasicCall.REST.GET,
            BasicCall.REST.POST,};
        BasicCall.REST[] restPost = {
            BasicCall.REST.POST,};
        BasicCall.REST[] restPostPutDelete = {
            BasicCall.REST.POST,
            BasicCall.REST.PUT,
            BasicCall.REST.DELETE};
        BasicCall.REST[] restPutDelete = {
            BasicCall.REST.PUT,
            BasicCall.REST.DELETE};

        /* *** Attributespecs and Attributespecs_ID *** */
        this.testURIMethodPairs(
                new String[]{
                    Const.Api.ATTRIBUTESPECS,},
                restPutDelete);

        this.testURIMethodPairs(
                new String[]{
                    Const.Api.ATTRIBUTESPECS_ID,},
                restPost);

        /* *** Princiapls *** */
        this.testURIMethodPairs(
                new String[]{
                    Const.Api.MANAGER_ORGANIZATIONS,
                    Const.Api.MANAGER_SERVICES,
                    Const.Api.MEMBER_ORGANIZATIONS,
                    Const.Api.PRINCIPAL,
                    Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                    Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                    Const.Api.PRINCIPAL_EMAILINVITATIONS,
                    Const.Api.PRINCIPAL_URLINVITATIONS,
                    Const.Api.PRINCIPALS_ID,
                    Const.Api.PRINCIPAL_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPAL,
                    Const.Api.PRINCIPAL_FEDID,},
                restPostPutDelete);

        this.testURIMethodPairs(
                new String[]{
                    Const.Api.PRINCIPALS,},
                restPutDelete);

        /* *** Services *** */
        this.testURIMethodPairs(
                new String[]{
                    Const.Api.SERVICES_MANAGERS,
                    Const.Api.SERVICES_ATTRIBUTESPECS,},
                restPostPutDelete);

        this.testURIMethodPairs(
                new String[]{
                    Const.Api.SERVICES,
                    Const.Api.SERVICES_ENTITLEMENTPACKS,
                    Const.Api.SERVICES_ENTITLEMENTS,},
                restPutDelete);

        this.testURIMethodPairs(
                new String[]{
                    Const.Api.SERVICES_ID,},
                restPost);

        this.testURIMethodPairs(
                new String[]{
                    Const.Api.SERVICES_ATTRIBUTESPECS_ASID,
                    Const.Api.SERVICES_MANAGERS_PID,},
                restGetPost);

    }

    /**
     * Easy to use testing bench, running all possible uri/call pair from the
     * parameter arrays. All the http calls and asserts are in one method, makes
     * it easier to maintain
     *
     * @param uris String[]: Strings for the uri-s to call, preferably from the
     * Const.Api constants
     * @param calls REST[]: from the BasicCall.REST[], can be GET, POST, PUT,
     * DELETE
     */
    private void testURIMethodPairs(String[] uris, BasicCall.REST[] calls) {
        for (String uri : uris) {
            for (BasicCall.REST method : calls) {
                try {
                    assertEquals(
                            "{\"code\":405,\"message\":\"Method Not Allowed\"}",
                            persistent.call(
                                    uri,
                                    method)
                    );
                    assertEquals("HTTP/1.1 405 Method Not Allowed", persistent.getStatusLine());
                } catch (AssertionError e) {
                    AssertErrorHandler(e);
                }
            }
        }
    }
}
