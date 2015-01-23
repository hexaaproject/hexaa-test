package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the
 * /api/organizations/{id}/entitlementpacks/{epid}/accept call.
 */
public class OrganizationEntitlementpacksLinkRequestTest extends CleanTest {
    
    /**
     * JSONArray to store the created entitlements.
     */
    public static JSONArray entitlementpacks = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksLinkRequestTest.class.getSimpleName() + " ***");
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
     * Creates a pending link between an organization and an entitlementpack.
     */
    @Test
    public void testOrganizationEntitlementpacksLinkRequest() {
        Utility.Link.entitlementpackToOrgRequest(1, 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(OrganizationEntitlementpacksLinkRequestTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(
                    entitlementpacks.getJSONObject(0).getInt("id"),
                    jsonResponse.getJSONObject(0).get("organization_id"));
            assertEquals(
                    "pending",
                    jsonResponse.getJSONObject(0).get("status"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
