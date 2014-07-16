package sztaki.hexaa.httputility.apicalls.organizations;

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

public class OrganizationGetTest extends Organization {

    @BeforeClass
    public static void setUpClass() {
        organizations = createOrganization(
                new String[]{
                    "TestOrgName1",
                    "TestOrgName2,",});
    }

    @Test
    public void testOrganizationGetArray() {
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS,
                                BasicCall.REST.GET));
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(organizations, jsonResponseArray, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testOrganizationGetById() {
        for (int i = 0; i < organizations.length(); i++) {
            JSONObject jsonResponse
                    = (JSONObject) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID,
                                    BasicCall.REST.GET,
                                    null,
                                    i + 1, 0));
            try {
                JSONAssert.assertEquals(organizations.getJSONObject(i), jsonResponse, JSONCompareMode.LENIENT);
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }
}
