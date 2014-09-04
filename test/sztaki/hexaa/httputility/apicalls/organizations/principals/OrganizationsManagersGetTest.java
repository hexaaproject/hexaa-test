package sztaki.hexaa.httputility.apicalls.organizations.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/organizations/{id}/managers call.
 */
public class OrganizationsManagersGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + OrganizationsManagersGetTest.class.getSimpleName() + " ***");
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
     * Tests the GET method.
     */
    @Test
    public void testOrganizationManagersGet() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_MANAGERS,
                                BasicCall.REST.GET,
                                null,
                                1, 1));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(principals, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
