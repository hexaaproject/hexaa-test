package sztaki.hexaa.httputility.apicalls.attributevalueorganizations.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/attributevalueorganizations/{id}/services
 * and /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueServicesGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONArray services = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueServicesGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        services = Utility.Create.service(new String[]{"service1", "service2"});
        Utility.Create.entitlementpacks(1, "entitlementpack1");
        Utility.Create.entitlementpacks(2, "entitlementpack2");
        
        Utility.Create.organization("Org1");
        Utility.Link.entitlementpackToOrg(1, new int[]{1,2});
        
        Utility.Create.role("someRole", 1);
        Utility.Link.principalToRole(1, 1);
        
        Utility.Create.attributespec(new String[]{"testName1"}, "manager");
        Utility.Create.attributevalueorganization("OrgValue1", 1, 1, new int[]{1, 2});
        System.out.println(Utility.persistent.getStatusLine());
    }

    /**
     * GET all services for the attributevalue.
     */
    @Test
    public void testAttributevalueorganizationsGetServices() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES,
                                BasicCall.REST.GET));
        
        if (response instanceof JSONObject) {
            fail("Some error instead of an array" + response.toString());
        }
        JSONArray jsonResponse = (JSONArray) response;
        System.out.println(jsonResponse.toString());
    }

    /**
     * GET one service for the attributevalue.
     */
    @Test
    public void testAttributevalueorganizationsGetServicesByID() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES,
                                BasicCall.REST.GET));
        
        if (response instanceof JSONObject) {
            fail("Some error instead of an array" + response.toString());
        }
        JSONArray jsonResponse = (JSONArray) response;
        System.out.println(jsonResponse.toString());
    }
}
