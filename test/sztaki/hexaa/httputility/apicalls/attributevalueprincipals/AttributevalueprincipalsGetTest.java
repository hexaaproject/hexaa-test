package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/attributevalueprincipals/{id} call.
 */
public class AttributevalueprincipalsGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONArray attributevalues = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueprincipalsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.principal("Org1");
        Utility.Create.attributespec(new String[]{"testName1"}, "user");
        attributevalues = Utility.Create.attributevalueprincipal("OrgValue1", 1);
        System.out.println(Utility.persistent.getStatusLine());
    }

    /**
     * Gets the attributevalue and checks it to the stored one.
     */
    @Test
    public void testAttributevalueprincipalGet() {
        JSONObject jsonResponse;
        Object response
                = JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
                                BasicCall.REST.GET,
                                null,
                                1, 1));
        if (response instanceof JSONArray) {
            fail("This is a JSONArray for some mysterious reason" + response.toString());
        }
        jsonResponse = (JSONObject) response;

        JSONObject jsonTemp = attributevalues.getJSONObject(0);
        jsonTemp.put("principal_id", jsonTemp.remove("principal"));
        jsonTemp.put("attribute_spec_id", jsonTemp.remove("attribute_spec"));
        jsonTemp.put("service_ids", jsonTemp.remove("services"));

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
