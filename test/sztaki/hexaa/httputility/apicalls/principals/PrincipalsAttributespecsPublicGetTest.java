package sztaki.hexaa.httputility.apicalls.principals;

import org.json.JSONArray;
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
 * Test the GET method on the /api/principal/attributespecs call for public
 * attributespecs.
 */
public class PrincipalsAttributespecsPublicGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributespecs.
     */
    private static JSONArray attributespecs = new JSONArray();

    /**
     * Creates one organization, two services and two attributespecs and links
     * them.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForPrincGet"});
        Utility.Link.memberToOrganization(1, 1);

        Utility.Create.service(new String[]{"testServForPrincGet1", "testServForPrincGet2"});

        attributespecs = Utility.Create.attributespec(new String[]{"testAttrSpec1", "testAttrSpec2"}, "user");
        Utility.Link.attributespecsPublicToService(1, new int[]{1});
        Utility.Link.attributespecsPrivateToService(2, new int[]{2});
    }

    /**
     * GET the available (public) attributespecs (as the principal is not a
     * member of any role).
     */
    @Test
    public void testPrincipalGetPublicAttributespecs() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                                BasicCall.REST.GET));

        JSONArray publicAttributespecs = new JSONArray();

        publicAttributespecs.put(attributespecs.getJSONObject(0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(publicAttributespecs, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
