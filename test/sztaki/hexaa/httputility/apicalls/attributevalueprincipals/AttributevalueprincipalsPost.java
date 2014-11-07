package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

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
 * Tests the POST method on the /api/attributevalueprincipals/{asid} call.
 */
public class AttributevalueprincipalsPost extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueprincipalsPost.class.getSimpleName() + " ***");
    }

    /**
     * Creates two attributespecs and links them to a service.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespec(
                new String[]{
                    "testAttributespecs1",
                    "testAttributespecs2"},
                "user");
        Utility.Create.service("testService");
        Utility.Link.attributespecsToService(1, 2, true);
    }

    /**
     * Creates an attributevalueprincipal with one value and one of the created
     * attributespecs, and verifies it with GET.
     */
    @Test
    public void testAttributevalueprincipalsPost() {
        JSONObject json = new JSONObject();
        json.put("value", "testValueString1");
        json.put("service_ids", new JSONArray(new int[]{}));
        json.put("attribute_spec_id", 1);

        Utility.Create.attributevalueprincipal("testValueString1", 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        Object jsonResponse = JSONParser.parseJSON(
                persistent.call(
                        Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                        BasicCall.REST.GET));

        if (jsonResponse instanceof JSONObject) {
            fail("Got JSONObject not JSONArray, most likely an error: " + jsonResponse.toString());
        }
        JSONArray jsonArrayResponse = (JSONArray) jsonResponse;
        
        int in = 0;
        for (int i = 0; i < jsonArrayResponse.length(); i++) {
            if (jsonArrayResponse.getJSONObject(i).getInt("attribute_spec_id") == 1) {
                in = i;
            }
        }

        try {
            JSONAssert.assertEquals(
                    json,
                    jsonArrayResponse.getJSONObject(in),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Creates an attributevalueprincipal with one value and one of the created
     * attributespecs, and verifies it with GET, linked it to a service.
     */
    @Test
    public void testAttributevalueprincipalsPostWithService() {
        JSONObject json = new JSONObject();
        json.put("value", "testValueString2");
        json.put("service_ids", new JSONArray(new int[]{1}));
        json.put("attribute_spec_id", 2);

        Utility.Create.attributevalueprincipal("testValueString2", 2, new int[]{1});

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        Object jsonResponse = JSONParser.parseJSON(
                persistent.call(
                        Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                        BasicCall.REST.GET));

        if (jsonResponse instanceof JSONObject) {
            fail("Got JSONObject not JSONArray, most likely an error: " + jsonResponse.toString());
        }
        JSONArray jsonArrayResponse = (JSONArray) jsonResponse;
        
        int in = 0;
        for (int i = 0; i < jsonArrayResponse.length(); i++) {
            if (jsonArrayResponse.getJSONObject(i).getInt("attribute_spec_id") == 2) {
                in = i;
            }
        }

        try {
            JSONAssert.assertEquals(
                    json,
                    jsonArrayResponse.getJSONObject(in),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
