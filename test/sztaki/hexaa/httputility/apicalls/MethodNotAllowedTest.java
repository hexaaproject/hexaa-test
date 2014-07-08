package sztaki.hexaa.httputility.apicalls;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

public class MethodNotAllowedTest extends CleanTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Bunch of tests to verify that the Method Not Allowed exception drop works
     * fine
     */
    @Test
    public void testMethodNotAllowed() {
        try {
            /* *** Attributespecs and Attributespecs_ID *** */
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.PUT));
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.DELETE));
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.ATTRIBUTESPECS_ID,
                    BasicCall.REST.POST));

            /* *** Services *** */
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.SERVICES,
                    BasicCall.REST.PUT));
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.SERVICES,
                    BasicCall.REST.DELETE));
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.SERVICES_ID,
                    BasicCall.REST.POST));

            /* *** Princiapls *** */
            String[] sA = {
                Const.Api.MANAGER_ORGANIZATIONS,
                Const.Api.MANAGER_SERVICES,
                Const.Api.MEMBER_ORGANIZATIONS,
                Const.Api.PRINCIPAL,
                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                Const.Api.PRINCIPAL_EMAILINVITATIONS,
                Const.Api.PRINCIPAL_URLINVITATIONS,
                Const.Api.PRINCIPALS_ID,
                // Removed until not fixed or proper assertation error handling is implemented
                Const.Api.PRINCIPAL_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPAL,
                // Removed until not fixed or proper assertation error handling is implemented
                Const.Api.PRINCIPAL_FEDID,};
            BasicCall.REST[] rA = {
                BasicCall.REST.POST,
                BasicCall.REST.PUT,
                BasicCall.REST.DELETE};

            this.testURIMethodPairs(sA, rA);
            assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}", new BasicCall().call(
                    Const.Api.SERVICES_ID,
                    BasicCall.REST.POST));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Something is wrong"
                    + "with the calls expecting Method Not Allowed!");
            collector.addError(e);
        }
    }

    private void testURIMethodPairs(String[] uris, BasicCall.REST[] calls) {
        for (String uri : uris) {
            for (BasicCall.REST method : calls) {
                try {
                    assertEquals("{\"code\":405,\"message\":\"Method Not Allowed\"}",
                            new BasicCall().call(
                                    uri,
                                    method)
                    );
                } catch (AssertionError e) {
                    System.out.println(e.getLocalizedMessage());
                    System.out.println("Something is wrong with the calls"
                            + " expecting Method Not Allowed with the Principals!");
                    collector.addError(e);
                }
            }
        }
    }
}
