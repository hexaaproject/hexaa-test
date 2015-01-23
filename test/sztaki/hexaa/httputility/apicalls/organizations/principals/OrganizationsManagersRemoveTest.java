package sztaki.hexaa.httputility.apicalls.organizations.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the DELETE method on the /api/organizations/{id}/managers/{pid} call.
 */
public class OrganizationsManagersRemoveTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationsManagersRemoveTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created principals.
     */
    public static JSONArray principals = new JSONArray();

    /**
     * Creates one organization and one principal and links them.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");
        principals = Utility.Create.principal("testPrincipal");
        Utility.Link.managerToOrganization(1, 2);

        principals.put((JSONObject) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.PRINCIPAL_SELF,
                        BasicCall.REST.GET)));
    }

    /**
     * Tests the DELETE method.
     */
    @Test
    public void testOrganizationManagersRemove() {
        Utility.Remove.managers(1, 2);

        principals.remove(0);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    principals,
                    (JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_MANAGERS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)),
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
