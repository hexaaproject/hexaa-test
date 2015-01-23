package sztaki.hexaa.apicalls.principals;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the GET method on the /api/member/organizations call.
 */
public class PrincipalsMemeberGetOrganizationsTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsMemeberGetOrganizationsTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created organizations.
     */
    private static JSONArray organizations = new JSONArray();

    /**
     * Creates one organization.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organization(new String[]{"testOrgForPrincGet"});
    }

    /**
     * GET the list of organizations where the user is a member.
     */
    @Test
    public void testPrincipalGetMemberOrg() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.MEMBER_ORGANIZATIONS,
                                BasicCall.REST.GET));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(organizations, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
