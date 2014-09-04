package sztaki.hexaa.httputility.apicalls.principals;

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
 * Test the GET method on the /api/principal/entitlements call.
 */
public class PrincipalsEntitlementsGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsEntitlementsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created entitlements.
     */
    private static JSONArray entitlements = new JSONArray();

    /**
     * Creates one organization, one role, services, entitlements and packs and
     * links them properly.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForPrincGet"});
        Utility.Link.memberToOrganization(1, 1);

        Utility.Create.role(new String[]{"role1"}, 1);

        Utility.Create.service(new String[]{"testServForPrincGet1", "testServForPrincGet2"});

        entitlements = Utility.Create.entitlements(2, new String[]{"entitlementServ2"});
        Utility.Create.entitlementpacks(2, new String[]{"entPackServ2"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpackToOrg(1, new int[]{1});
        Utility.Link.entitlementsToRole(1, new int[]{1});

        Utility.Link.principalToRole(1, new int[]{1});
    }

    /**
     * GET all entitlements of user.
     */
    @Test
    public void testPrincipalGetEntitlements() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ENTITLEMENTS,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(entitlements, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
