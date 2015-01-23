package sztaki.hexaa.httputility.apicalls.principals;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/principal/services/related call.
 */
public class PrincipalServicesRelatedTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalServicesRelatedTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created attributevalues.
     */
    private static JSONArray services = new JSONArray();

    /**
     * Creates the necessary objects for the test.
     */
    @BeforeClass
    public static void setUpClass() {

        services = Utility.Create.service(new String[]{"testServForPrincRelated1", "testServForPrincRelated2"});

        Utility.Create.organization("testOrg");

        Utility.Create.entitlementpacks(1, new String[]{"testPack1"});
        Utility.Create.entitlements(1, new String[]{"testEntitlement1"});
        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpackToOrg(1, 1);
        Utility.Create.entitlementpacks(2, new String[]{"testPack2"});
        Utility.Create.entitlements(2, new String[]{"testEntitlement2"});
        Utility.Link.entitlementToPack(2, 2);
        Utility.Link.entitlementpackToOrg(1, 2);

        Utility.Create.role("testRole", 1);
        Utility.Create.principal("testPrincipal");
        Utility.Link.memberToOrganization(1, 2);
        Utility.Link.principalToRole(1, 2);

        Utility.Link.entitlementsToRole(1, new int[]{1});
    }

    /**
     * Checks the returned id-s.
     */
    @Test
    public void testPrincipalServicesRelated() {
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(Const.Api.PRINCIPAL_SERVICES_RELATED, BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(PrincipalServicesRelatedTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals(services.length(), jsonResponse.length());
            for (int i = 0; i < services.length(); i++) {
                assertEquals(services.getJSONObject(i).get("id"), jsonResponse.getJSONObject(i).get("id"));
            }
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
