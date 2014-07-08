package sztaki.hexaa.httputility.apicalls.attributes;

import static org.junit.Assert.*;
import org.junit.Test;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsInsertTest extends CleanTest {

    /**
     * Uses the /api/attributespecs PUT method with 2 different JSON Object and
     * verifies them from the server
     */
    @Test
    public void testInserting() {
        // Create a JSON object
        JSONObject json = new JSONObject();
        json.put("oid", "1");
        json.put("friendly_name", "testName1");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        // POST the object to the server and assert the string right away
        assertEquals("", new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString(),
                0,
                0));
        // remove the clutter that will not be shown in the response and add what will,
        // this is not a best case
        json.remove("is_multivalue");
        json.put("id", 1);
        // GET the recently changed attribute
        JSONObject jsonResponse = (JSONObject) JSONParser.parseJSON(
                new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID,
                        BasicCall.REST.GET,
                        null,
                        1,
                        0));
        // Assert the response as a JSONObject
        JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.STRICT);

        // Specify the second JSON object
        json = new JSONObject();
        json.put("oid", "2");
        json.put("friendly_name", "testName2");
        json.put("syntax", "noSyntax2");
        json.put("is_multivalue", false);

        // POST the object to the server and assert the string right away
        assertEquals("", new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString(),
                0,
                0));
        // remove the clutter that will not be shown in the response and add what will,
        // this is not a best case
        json.remove("is_multivalue");
        json.put("id", 2);
        // GET the recently changed attribute
        jsonResponse = (JSONObject) JSONParser.parseJSON(
                new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID,
                        BasicCall.REST.GET,
                        null,
                        2,
                        0));
        // Assert the response as a JSONObject
        JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.STRICT);

    }

}
