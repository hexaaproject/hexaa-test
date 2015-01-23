package sztaki.hexaa.httputility.apicalls.principals;

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
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the GET method on the /api/principal/attributespecs call.
 */
public class PrincipalsAttributespecsPrivateGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsAttributespecsPrivateGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created attributespecs.
     */
    private static JSONArray attributespecs = new JSONArray();

    /**
     * Creates one organization, two services, one role, one entitlement and one
     * entitlementpack, and links them together.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForPrincGet"});
        Utility.Link.memberToOrganization(1, 1);

        Utility.Create.service(new String[]{"testServForPrincGet1", "testServForPrincGet2"});

        attributespecs = Utility.Create.attributespec(new String[]{"testAttrSpec1", "testAttrSpec2"}, "user");
        Utility.Link.attributespecsPublicToService(1, new int[]{1});
        Utility.Link.attributespecsPrivateToService(2, new int[]{2});

        Utility.Create.role(new String[]{"role1"}, 1);

        Utility.Create.entitlements(2, new String[]{"entitlementServ2"});
        Utility.Create.entitlementpacks(2, new String[]{"entPackServ2"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpackToOrg(1, new int[]{1});
        Utility.Link.entitlementsToRole(1, new int[]{1});

        Utility.Link.principalToRole(1, new int[]{1});

    }

    /**
     * GET all available attributespecs.
     */
    @Test
    public void testPrincipalGetPrivateAttributespecs() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                                BasicCall.REST.GET));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(attributespecs, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
