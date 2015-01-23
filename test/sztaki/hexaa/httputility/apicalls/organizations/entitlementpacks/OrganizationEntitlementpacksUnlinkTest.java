package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DELETE method on the
 * /api/organizations/{id}/entitlementpacks/{epid} call.
 */
public class OrganizationEntitlementpacksUnlinkTest extends CleanTest {

    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlements = new JSONArray();
    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlementpacks = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksUnlinkTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates a service, an organization, entitlements and entitlementpacks and
     * puts them together to create full entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService"});
        Utility.Create.organization(new String[]{"testOrganization"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});
    }

    /**
     * Creates a pending link and deletes it than creates an accepted link and
     * deletes that also, than asserts it for an empty array on GET
     * .../entitlementpacks to verify.
     */
    @Test
    public void testOrganizationEntitlementpacksDelete() {
        Utility.Link.entitlementpackToOrgRequest(1, 1);

        // Delete a pending link
        Utility.Remove.entitlementpackFromOrg(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        
        Utility.Link.entitlementpackToOrg(1, 1);

        // Delete an accepted link
        Utility.Remove.entitlementpackFromOrg(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    new JSONArray(),
                    
                            persistent.getResponseJSONArray(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(OrganizationEntitlementpacksUnlinkTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
    }
}
