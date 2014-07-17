package sztaki.hexaa.httputility.apicalls.principal;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;
import sztaki.hexaa.httputility.apicalls.services.Services;

public class PrincipalGetTest extends CleanTest {

    public static JSONArray organizations = new JSONArray();
    public static JSONArray services = new JSONArray();

    @BeforeClass
    public static void setUpClass() {
        organizations = Organization.createOrganization(new String[]{"testOrgForPrincGet"});
        services = Services.createServices(new String[]{"testServForPrincGet"});

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
        }catch(AssertionError e) {
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
        }catch(AssertionError e) {
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
        }catch(AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testPrincipalGet() {
        
    }
}
