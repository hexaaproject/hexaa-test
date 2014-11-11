package sztaki.hexaa.httputility.apicalls.attributevalueprincipals.services;

import org.json.JSONObject;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/attributevalueorganizations/{id}/services
 * and /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueprincipalsServicesGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONObject attributevalueprincipal = new JSONObject();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueprincipalsServicesGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"service1", "service2"});
        Utility.Create.entitlementpacks(1, "entitlementpack1");
        Utility.Create.entitlementpacks(2, "entitlementpack2");

        Utility.Create.organization("Org1");
        Utility.Link.entitlementpackToOrg(1, new int[]{1, 2});

        Utility.Create.attributespec(new String[]{"testName1"}, "user");
        Utility.Link.attributespecsToService(1, new int[]{1, 2}, true);
        Utility.Link.attributespecsToService(2, new int[]{1, 2}, true);
        attributevalueprincipal = Utility.Create.attributevalueprincipal("OrgValue1", 1, new int[]{2}).getJSONObject(0);
        System.out.println(Utility.persistent.getStatusLine());
    }

    /**
     * GET all services for the attributevalue.
     */
    @Test
    public void testAttributevalueorganizationsGetServices() {
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }
        
        System.out.println(attributevalueprincipal.toString());
        System.out.println(jsonResponse.toString());
        try {
            assertEquals(attributevalueprincipal.get("services"), jsonResponse.get("service_ids"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET one service for the attributevalue.
     */
    @Test
    public void testAttributevalueorganizationsGetServicesByID() {
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
                    BasicCall.REST.GET,
                    null,
                    1, 2);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }

        JSONObject service;
        try {
            service = persistent.getResponseJSONObject(
                    Const.Api.SERVICES_ID,
                    BasicCall.REST.GET,
                    null,
                    2, 2);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }
        
        System.out.println(attributevalueprincipal.toString());
        System.out.println(jsonResponse.toString());
        System.out.println(service.toString());

        try {
            JSONAssert.assertEquals(service, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
