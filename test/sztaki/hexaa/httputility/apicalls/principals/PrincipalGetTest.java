package sztaki.hexaa.httputility.apicalls.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;
// TODO commentek
/**
 * Tests the GET method on the /api/organization/{id}/roles and /api/roles/{id}
 calls.
 */
public class PrincipalGetTest extends CleanTest {

    public static JSONArray managers = new JSONArray();
    public static JSONArray organizations = new JSONArray();
    public static JSONArray services = new JSONArray();
    public static JSONArray attributespecs = new JSONArray();

    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organization(new String[]{"testOrgForPrincGet"});
        services = Utility.Create.services(new String[]{"testServForPrincGet1", "testServForPrincGet2"});
        attributespecs = Utility.Create.attributespecs(new String[]{"testAttrSpec1", "testAttrSpec2"});
        Utility.Link.attributespecsToService(1, new int[]{1});
        Utility.Link.attributespecsToService(2, new int[]{2});
        Utility.Create.roles(new String[]{"role1"}, 1);
        Utility.Create.entitlements(2, new String[]{"entitlementServ2"});
        Utility.Create.entitlementpacks(2, new String[]{"entPackServ2"});
        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpacksToOrg(1, new int[] {1});
        Utility.Link.entitlementsToRole(1, new int[]{1});

        managers = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.PRINCIPALS,
                        BasicCall.REST.GET));
    }

    @Test
    public void testPrincipalGetManagerOrg() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.MANAGER_ORGANIZATIONS,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(organizations, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testPrincipalGetManagerService() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.MANAGER_SERVICES,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(services, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testPrincipalGetMember() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.MEMBER_ORGANIZATIONS,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(organizations, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

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

    @Test
    public void testPrincipalGetPrivateAttributespecs() {
        Utility.Link.principalToRole(1, new int[]{1});

        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(attributespecs, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testPrincipalGetIsAdmin() {
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ISADMIN,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(true, jsonResponse.getBoolean("is_admin"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testPrincipalGetNotAdmin() {
        new Authenticator().authenticate("admin@is.not");

        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ISADMIN,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(false, jsonResponse.getBoolean("is_admin"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        System.out.println(persistent.call(Const.Api.PRINCIPALS, BasicCall.REST.GET));

        new Authenticator().authenticate(Const.HEXAA_FEDID);
    }
}
