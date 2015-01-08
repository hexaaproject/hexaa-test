package sztaki.hexaa.httputility.apicalls.organizations.entitlementpacks;

import org.json.JSONArray;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 */
public class OrganizationEntitlementpacksAddTest extends CleanTest {

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
        System.out.println("***\t " + OrganizationEntitlementpacksAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates a service, an organization, entitlements and entitlementpacks and
     * puts them together to create full entitlementpacks.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService"});
        Utility.Create.organization(new String[]{"testOrganization"});
        entitlementpacks = Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1", "testEntitlementpack2"});
    }

    @Test
    public void testOrganizationEntitlementpacksAdd() {
        Utility.Link.entitlementpackToOrg(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
    
    @Test
    public void testOrganizationEntitlementpacksAddByArray() {
        Utility.Link.entitlementpackToOrgByArray(1, new int[]{1,2});

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
