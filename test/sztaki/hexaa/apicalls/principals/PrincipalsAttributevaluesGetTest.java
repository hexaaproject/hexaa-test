package sztaki.hexaa.apicalls.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the GET method on the /api/principal/attributevalueprincipal call.
 */
public class PrincipalsAttributevaluesGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalsAttributevaluesGetTest.class.getSimpleName() + " ***");
    }

    /**
     * JSONArray to store the created attributevalues.
     */
    private static JSONArray attributevalue = new JSONArray();

    /**
     * Creates the necessary objects for the test.
     */
    @BeforeClass
    public static void setUpClass() {

        Utility.Create.service(new String[]{"testServForPrincGet1"});

        /**
         */
        Utility.Create.organization("testOrg");
        Utility.Create.role("testRole", 1);

        Utility.Create.entitlementpacks(1, new String[]{"testPack"});
        Utility.Create.entitlements(1, new String[]{"testEntitlement"});

        Utility.Link.entitlementToPack(1, 1);
        Utility.Link.entitlementpackToOrg(1, 1);
        Utility.Link.entitlementsToRole(1, new int[]{1});
        Utility.Link.principalToRole(1, 1);
        /**
         */
        Utility.Create.attributespec(new String[]{"testAttrSpec1"}, "user");
        Utility.Link.attributespecsPublicToService(1, new int[]{1});

        attributevalue = Utility.Create.attributevalueprincipal(new String[]{"testValue1"}, 1);
        JSONObject jsonTemp = new JSONObject();
        jsonTemp.put("attribute_spec_id", attributevalue.getJSONObject(0).getInt("attribute_spec"));
        jsonTemp.put("service_ids", attributevalue.getJSONObject(0).get("services"));
        jsonTemp.put("value", attributevalue.getJSONObject(0).get("value"));
        attributevalue.put(0, jsonTemp);
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
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(attributevalue, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * GET all attributevalue specified by the attributespec id.
     */
    @Test
    public void testPrincipalsGetAttributevalueBySpec() {
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS,
                                BasicCall.REST.GET,
                                null,
                                1, 1));

        if (response instanceof JSONObject) {
            fail("Not a JSONArray but JSONObject: " + ((JSONObject) response).toString());
        }
        JSONArray jsonResponse = (JSONArray) response;

        try {
            JSONAssert.assertEquals(attributevalue, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
