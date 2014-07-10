package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsPutTest extends CleanTest {

    private static JSONArray array = new JSONArray();

    /**
     * Before the Class methods run initialize 2 object on the server
     */
    @BeforeClass
    public static void buildUp() {

        JSONObject json1 = new JSONObject();

        json1.put("oid", "1");
        json1.put("friendly_name", "testName1");
        json1.put("syntax", "noSyntax1");
        json1.put("is_multivalue", false);
        persistent.call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json1.toString(),
                0, 0);

        json1.put("id", 1);

        array.put(json1);
    }

    /**
     * Changes an Attributespecs on the server by calling PUT on
     * /api/attributespec/{id}, after that it verifies the success by
     * /api/attributespec/{id} GET
     */
    @Test
    public void testAttributespecsPut() {
        /* *** The desired changes *** */
        ((JSONObject) array.get(0)).put("oid", "oidByPut");
        ((JSONObject) array.get(0)).put("friendly_name", "nameByPut");
        // Remove the id key for the call, and re-add it after the call, so the 
        // other tests are not effected
        int idTemp = (int) ((JSONObject) array.get(0)).remove("id");
        persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.PUT,
                ((JSONObject) array.get(0)).toString(),
                1, 0);
        ((JSONObject) array.get(0)).put("id", idTemp);
        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        /* *** Verifing the success *** */
        String response = persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.GET,
                null,
                1, 0);

        JSONObject jsonResponse = null;

        jsonResponse = (JSONObject) JSONParser.parseJSON(response);

        if (jsonResponse != null && jsonResponse.isNull("is_multivalue")) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }
        try {
            assertEquals("oidByPut", jsonResponse.get("oid"));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
