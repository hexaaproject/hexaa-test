package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;

/**
 * Tests the DELETE method on the
 * /api/organizations/{id}/entitlementpacks/{epid} call.
 */
public class OrganizationEntitlementpacksUnlinkTest extends OrganizationEntitlementpack {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationEntitlementpacksUnlinkTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates a pending link and deletes it than creates an accepted link and
     * deletes that also, than asserts it for an empty array on GET
     * .../entitlementpacks to verify.
     */
    @Test
    public void testOrganizationEntitlementpacksDelete() {
        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[]{1});

        // Delete a pending link
        Utility.Remove.entitlementpackFromOrg(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Creates a link between an organization and an entitlementpack, and checks that it is pending
        this.createPendingLink(1, new int[]{1});
        // Accepts the pending link
        this.acceptPendingLink(1, new int[]{1});

        // Delete an accepted link
        Utility.Remove.entitlementpackFromOrg(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    new JSONArray(),
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

    }
}
