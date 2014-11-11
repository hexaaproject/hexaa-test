package sztaki.hexaa.httputility.apicalls.attributevalueorganizations.services;

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
 * Tests the PUT method on the
 * /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueorganizationsServicesPutTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONObject attributevalueorganization = new JSONObject();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueorganizationsServicesPutTest.class.getSimpleName() + " ***");
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
     * PUT a service to the attributevalue and checks that after the put the
     * same service is available with /api/services/{id} and
     * /api/attributevalueorganizations/{id}/services/{sid}.
     */
    @Test
    public void testAttributevalueorganizationsPutServices() {
        Utility.Link.serviceToAttributevalueorganizations(1, 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
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
                    1, 1);
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
