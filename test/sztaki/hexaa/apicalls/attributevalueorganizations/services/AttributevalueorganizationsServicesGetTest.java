package sztaki.hexaa.apicalls.attributevalueorganizations.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/attributevalueorganizations/{id}/services
 * and /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueorganizationsServicesGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONObject attributevalueorganization = new JSONObject();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueorganizationsServicesGetTest.class.getSimpleName() + " ***");
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

        Utility.Create.attributespec(new String[]{"testName1"}, "manager");
        Utility.Link.attributespecsToService(1, new int[]{1, 2}, true);
        Utility.Link.attributespecsToService(2, new int[]{1, 2}, true);
        attributevalueorganization = Utility.Create.attributevalueorganization("OrgValue1", 1, 1, new int[]{2}).getJSONObject(0);
    }

    /**
     * GET all services for the attributevalue.
     */
    @Test
    public void testAttributevalueorganizationsGetServices() {
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            fail(ex.getFullMessage());
            return;
        }

        System.out.println(attributevalueorganization.get("services"));
        System.out.println(jsonResponse.get("service_ids"));

        System.out.println(attributevalueorganization.get("services").getClass());
        System.out.println(jsonResponse.get("service_ids").getClass());

        try {
            JSONAssert.assertEquals(
                    (JSONArray) attributevalueorganization.get("services"),
                    (JSONArray) jsonResponse.get("service_ids"),
                    JSONCompareMode.LENIENT);
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
                    Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
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

        try {
            JSONAssert.assertEquals(service, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
