package sztaki.hexaa.httputility.apicalls.attributes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;

public class AttributespecsNonEmptyTest {

    private static JSONArray array = new JSONArray();

    /**
     * Before the Class methods run initialize 2 object on the server
     */
    @BeforeClass
    public static void buildUp() {

        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();

        json1.put("oid", "1");
        json1.put("friendly_name", "testName1");
        json1.put("syntax", "noSyntax1");
        json1.put("is_multivalue", false);

        System.out.println(json1.toJSONString());
        System.out.println(new Attributespecs().call(HttpUtilityBasicCall.REST.POST,
                json1.toJSONString()));
        json1.put("id", 1);

        json2.put("oid", "differentName");
        json2.put("friendly_name", "differentTestName1");
        json2.put("syntax", "noSyntax2");
        json2.put("is_multivalue", false);

        System.out.println(json2.toJSONString());
        System.out.println(new Attributespecs().call(HttpUtilityBasicCall.REST.POST,
                json2.toJSONString()));
        json2.put("id", 2);

        array.add(json1);
        array.add(json2);
    }

    /**
     * GET-s all Attributespecs from /api/attributespecs and gets an array, from
     * this array it choose the correct object and assert it with the locally
     * stored object
     */
    @Test
    public void testGetByArray() {

        String response = new Attributespecs().call(HttpUtilityBasicCall.REST.GET);

        JSONObject jsonResponse = null;

        try {
            jsonResponse = (JSONObject) ((JSONArray) new JSONParser().parse(response)).get(0);
        } catch (ParseException ex) {
            Logger.getLogger(AttributespecsNonEmptyTest.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        if (jsonResponse != null && jsonResponse.get("is_multivalue") == null) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }

        assertEquals(((JSONObject) array.get(0)).toJSONString(),
                jsonResponse.toJSONString());
    }

    /**
     * GET-s the desired object by calling /api/attributespecs/{id} with the
     * proper id, tests it against the locally stored object
     */
    @Test
    public void testGetByID() {
        // Get the Attributespecs with the id = 2
        String response
                = new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 2, 0);

        JSONObject jsonResponse = null;
        // Parse it to JSON
        try {
            jsonResponse = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(AttributespecsNonEmptyTest.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        if (jsonResponse != null && jsonResponse.get("is_multivalue") == null) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }
        // Assert it with the local object
        assertEquals(((JSONObject) array.get(1)).toJSONString(),
                jsonResponse.toJSONString());
    }

    /**
     * To test the capability of the /api/attributespecs GET to retrieve an
     * array of existing Attributespecs we call the API call and assert it with
     * the local array
     */
    @Test
    public void testArray() {
        /* *** GET the desired array from the server *** */
        String response = new Attributespecs().call(HttpUtilityBasicCall.REST.GET);

        JSONArray jsonResponse = null;

        // Parse the string, so the key:value pairs will be ordered properly
        try {
            jsonResponse = (JSONArray) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(AttributespecsNonEmptyTest.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        // If the array does not have the "is_multivalue" key we add it as false
        if (jsonResponse != null && jsonResponse.size() > 0) {
            for (Object json : jsonResponse) {
                if (((JSONObject) json) != null) {
                    if (((JSONObject) json).get("is_multivalue") == null) {
                        ((JSONObject) json).put("is_multivalue", false);
                    }
                }
            }
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONArray();
        }
        // Assert the two arrays as string
        assertEquals(
                array.toJSONString(), jsonResponse.toJSONString());

    }

    /**
     * Changes an Attributespecs on the server by calling PUT on
     * /api/attributespec/{id}, after that it verifies the success by
     * /api/attributespec/{id} GET
     */
    @Test
    public void testPut() {
        /* *** The desired changes *** */
        ((JSONObject) array.get(1)).put("oid", "oidByPut");
        ((JSONObject) array.get(1)).put("friendly_name", "nameByPut");
        // Remove the id key for the call, and readd it after the call, so the 
        // other tests are not effected
        System.out.println(((JSONObject) array.get(1)).toJSONString());
        int idTemp = (int) ((JSONObject) array.get(1)).remove("id");
        new Attributespecs_ID().call(HttpUtilityBasicCall.REST.PUT,
                ((JSONObject) array.get(1)).toJSONString(), 2, 0);
        ((JSONObject) array.get(1)).put("id", idTemp);

        /* *** Verifing the success *** */
        String response
                = new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 2, 0);

        JSONObject jsonResponse = null;

        try {
            jsonResponse = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(AttributespecsNonEmptyTest.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        if (jsonResponse != null && jsonResponse.get("is_multivalue") == null) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }

        System.out.println(((JSONObject) array.get(1)).toJSONString());
        System.out.println(jsonResponse.toJSONString());
        assertEquals("oidByPut", jsonResponse.get("oid"));
    }

    /**
     * POST-s on /api/attributespecs to the server, verifies it by
     * /api/attributespecs_id GET, after that it calls DELETE and verifies the
     * non existence by calling /api/attributespec GET and assert it with the
     * local array
     */
    @Test
    public void testDelete() {

        /* *** POST a new object *** */
        JSONObject json3 = new JSONObject();

        json3.put("oid", "3");
        json3.put("friendly_name", "testName3");
        json3.put("syntax", "noSyntax1");
        json3.put("is_multivalue", false);

        System.out.println(json3.toJSONString());
        System.out.println(new Attributespecs().call(HttpUtilityBasicCall.REST.POST,
                json3.toJSONString()));
        json3.put("id", 3);

        /* *** Verify the data on the server *** */
        String response
                = new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 3, 0);

        JSONObject jsonOResponse = null;

        try {
            jsonOResponse = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(AttributespecsNonEmptyTest.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        if (jsonOResponse != null && jsonOResponse.get("is_multivalue") == null) {
            jsonOResponse.put("is_multivalue", false);
        }
        if (jsonOResponse == null) {
            jsonOResponse = new JSONObject();
        }
        assertEquals(json3.toJSONString(),
                jsonOResponse.toJSONString());

        /* *** Calling DELETE *** */
        System.out.println(
                new Attributespecs_ID().call(HttpUtilityBasicCall.REST.DELETE, 3, 0));

        /* *** Verifing the delete *** */
        response = new Attributespecs().call(HttpUtilityBasicCall.REST.GET);

        JSONArray jsonResponse = null;

        try {
            jsonResponse = (JSONArray) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(AttributespecsNonEmptyTest.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        if (jsonResponse != null && jsonResponse.size() > 0) {
            for (Object json : jsonResponse) {
                if (((JSONObject) json) != null) {
                    if (((JSONObject) json).get("is_multivalue") == null) {
                        ((JSONObject) json).put("is_multivalue", false);
                    }
                }
            }
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONArray();
        }

        assertEquals(
                array.toJSONString(), jsonResponse.toJSONString());

    }
}
