package sztaki.hexaa.httputility.apicalls.roles.entitlements;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/roles/{id}/entitlement call.
 */
public class RolesEntitlementsSetTest extends CleanTest {

    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlements = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesEntitlementsSetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates necessary objects on the server.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.role(new String[]{"testRole1", "testRole2"}, 1);
        Utility.Create.service(new String[]{"testService1"});
        Utility.Create.entitlementpacks(1, "entitlementPack");
        entitlements = Utility.Create.entitlements(1, new String[]{"entitlement1", "entitlement2"});
        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementToPack(2, 1);
        Utility.Link.entitlementpackToOrg(1, 1);
    }

    /**
     * PUT the entitlement to a role as an array.
     */
    @Test
    public void testRolesEntitlementsSet() {
        Utility.Link.entitlementsToRoleByArray(1, new int[]{1, 2});

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONArray jsonResponse;

        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.ROLES_ID_ENTITLEMENTS,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(RolesEntitlementsSetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(
                    entitlements,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
