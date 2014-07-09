package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsGetTest extends CleanTest {

    private static JSONArray array = new JSONArray();

    /**
     * Before the Class methods run initialize 2 object on the server
     */
    @BeforeClass
    public static void buildUp() {

        JSONObject json = new JSONObject();

        json.put("oid", "1");
        json.put("friendly_name", "testName1");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString(),
                0, 0);
        json.put("id", 1);

        array.put(json);

        json = new JSONObject();

        json.put("oid", "differentName");
        json.put("friendly_name", "differentTestName1");
        json.put("syntax", "noSyntax2");
        json.put("is_multivalue", false);

        new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString(),
                0, 0);
        json.put("id", 2);

        array.put(json);
    }

    /**
     * GET-s all Attributespecs from /api/attributespecs and gets an array, from
     * this array it choose the correct object and assert it with the locally
     * stored object
     */
    @Test
    public void testAttributespecsGetByArray() {

        JSONObject jsonResponse = null;

        jsonResponse = (JSONObject) ((JSONArray) JSONParser.parseJSON(
                new BasicCall().call(
                        Const.Api.ATTRIBUTESPECS,
                        BasicCall.REST.GET))).get(0);

        if (jsonResponse != null && jsonResponse.isNull("is_multivalue")) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }

        try {
            JSONAssert.assertEquals((JSONObject) array.get(0),
                    jsonResponse, false);
        } catch (AssertionError e) {
            collector.addError(e);
        }
    }

    /**
     * GET-s the desired object by calling /api/attributespecs/{id} with the
     * proper id, tests it against the locally stored object
     */
    @Test
    public void testAttributespecsGetByID() {
        // Get the Attributespecs with the id = 2
        // Parse it to JSON
        JSONObject jsonResponse = null;
        jsonResponse = (JSONObject) JSONParser.parseJSON(new BasicCall().call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.GET,
                null,
                2, 0));

        if (jsonResponse != null && jsonResponse.isNull("is_multivalue")) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }
        // Assert it with the local object
        try {
            JSONAssert.assertEquals((JSONObject) array.get(1),
                    jsonResponse, false);
        } catch (AssertionError e) {
            collector.addError(e);
        }
    }

    /**
     * To test the capability of the /api/attributespecs GET to retrieve an
     * array of existing Attributespecs we call the API call and assert it with
     * the local array
     */
    @Test
    public void testAttributespecsArray() {
        /* *** GET the desired array from the server *** */
        String response = new BasicCall().call(Const.Api.ATTRIBUTESPECS, BasicCall.REST.GET);

        JSONArray jsonResponse = null;

        // Parse the string, so the key:value pairs will be ordered properly
        jsonResponse = (JSONArray) JSONParser.parseJSON(response);

        // If the array does not have the "is_multivalue" key we add it as false
        if (jsonResponse != null && jsonResponse.length() > 0) {
            for (int i = 0; i < jsonResponse.length(); i++) {
                if (((JSONObject) jsonResponse.get(i)) != null) {
                    if (((JSONObject) jsonResponse.get(i)).isNull("is_multivalue")) {
                        ((JSONObject) jsonResponse.get(i)).put("is_multivalue", false);
                    }
                }
            }
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONArray();
        }
        // Assert the two arrays as string
        try {
            JSONAssert.assertEquals(
                    array, jsonResponse, false);
        } catch (AssertionError e) {
            collector.addError(e);
        }

    }

}
