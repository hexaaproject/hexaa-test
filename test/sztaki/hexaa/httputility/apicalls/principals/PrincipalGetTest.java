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

/**
 * Tests the GET method on the /api/organization/{id}/role and /api/role/{id}
 * calls.
 */
public class PrincipalGetTest extends CleanTest {

    public static JSONArray managers = new JSONArray();
    public static JSONArray organizations = new JSONArray();
    public static JSONArray services = new JSONArray();
    public static JSONArray attributespecs = new JSONArray();
    public static JSONArray attributevalue = new JSONArray();
    public static JSONArray entitlements = new JSONArray();
    public static JSONArray roles = new JSONArray();

    /**
     * Creates all the necessary objects for the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        organizations = Utility.Create.organization(new String[]{"testOrgForPrincGet"});
        Utility.Link.memberToOrganization(1, 1);

        services = Utility.Create.service(new String[]{"testServForPrincGet1", "testServForPrincGet2"});

        attributespecs = Utility.Create.attributespec(new String[]{"testAttrSpec1", "testAttrSpec2"}, "user");
        Utility.Link.attributespecsPublicToService(1, new int[]{1});
        Utility.Link.attributespecsPrivateToService(2, new int[]{2});

        roles = Utility.Create.role(new String[]{"role1"}, 1);

        entitlements = Utility.Create.entitlements(2, new String[]{"entitlementServ2"});
        Utility.Create.entitlementpacks(2, new String[]{"entPackServ2"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpackToOrg(1, new int[]{1});
        Utility.Link.entitlementsToRole(1, new int[]{1});

        Utility.Link.principalToRole(1, new int[]{1});

        attributevalue = Utility.Create.attributevalueprincipals(new String[]{"testValue"}, 1);

        managers = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.PRINCIPALS,
                        BasicCall.REST.GET));
    }

    /**
     * GET the list of organizations where the user is at least a manager.
     */
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

    /**
     * GET the list of services where the user is manager.
     */
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
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(organizations, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET the available (public) attributespecs (as the principal is not a
     * member of any role).
     */
    @Test
    public void testPrincipalGetPublicAttributespecs() {

        Utility.Remove.principalFromRole(1, new int[]{1});
        Utility.Remove.entitlementpackFromOrg(1, 1);

        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                                BasicCall.REST.GET));

        JSONArray publicAttributespecs = new JSONArray();

        publicAttributespecs.put(attributespecs.getJSONObject(0));
        
        System.out.println(jsonResponse.toString());
        System.out.println(persistent.call(Const.Api.PRINCIPAL_ROLES, BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(publicAttributespecs, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        Utility.Link.principalToRole(1, new int[]{1});
        Utility.Link.entitlementpackToOrg(1, new int[]{1});
        Utility.Link.entitlementsToRole(1, new int[]{1});
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
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(attributespecs, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET all attributevalues.
     */
    @Test
    public void testPrincipalGetAttributevalues() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(attributevalue, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
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
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(entitlements, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

    }

    /**
     * GET the admin info about the principal when the principal is an admin.
     */
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

    /**
     * GET the admin info about the principal when the principal is not an
     * admin.
     */
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

    /**
     * GET all the roles of the current principal.
     */
    @Test
    public void testPrincipalRolesGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ROLES,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(roles, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testPrincipalSelfGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPAL_ROLES,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(roles, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

//TODO
}
