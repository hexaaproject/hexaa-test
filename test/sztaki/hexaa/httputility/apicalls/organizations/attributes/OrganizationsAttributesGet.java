package sztaki.hexaa.httputility.apicalls.organizations.attributes;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Test the GET method related to Attributes and Attributevalue on
 * organizations.
 */
public class OrganizationsAttributesGet extends CleanTest {

    /**
     * JSONArray to store the created attributespecs.
     */
    public static JSONArray attributespecs = new JSONArray();
    /**
     * JSONArray to store an attributevalue.
     */
    public static JSONArray attributevalue = new JSONArray();

    /**
     * Creates all the necessary objects on the server to test the methods to
     * GET the attributes and attributevalues of an organization.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("testOrg");

        attributespecs = Utility.Create.attributespec(new String[]{"atributespec1", "atributespec2"}, "manager");

        Utility.Create.service("testService");

        Utility.Create.entitlementpacks(1, new String[]{"testEPack1"});

        Utility.Link.attributespecsToService(1, new int[]{1, 2});

        Utility.Link.entitlementpackToOrg(1, new int[]{1});

        attributevalue = Utility.Create.attributevalueorganizations(new String[]{"testValue1"}, 1, 1);
        attributevalue.put((Utility.Create.attributevalueorganizations(new String[]{"testValue2"}, 2, 1)).getJSONObject(0));
    }

    /**
     * GET the available attributespecs.
     */
    @Test
    public void testOrganizationsAttributespecsGet() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    attributespecs,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET a specific attributespecs values.
     */
    @Test
    public void testOrganizationsAttributevaluesBySpecsGet() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS,
                                BasicCall.REST.GET));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but a JSONObject: " + ((JSONObject) response).toString());
        }

        JSONArray jsonResponse
                = (JSONArray) response;

        JSONArray jsonExpected = new JSONArray();

        jsonExpected.put(attributevalue.getJSONObject(0));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    jsonExpected,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET all the attributevalues.
     */
    @Test
    public void testOrganizationsAllAttributevalues() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    attributevalue,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
