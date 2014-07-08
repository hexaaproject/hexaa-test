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

    @Test
    public void testInserting() {

        JSONObject json = new JSONObject();
        json.put("oid", "1");
        json.put("friendly_name", "testName1");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        assertEquals("", new BasicCall().call(Const.Api.ATTRIBUTESPECS, BasicCall.REST.POST, json.toString(), 0, 0));
        json.remove("is_multivalue");
        json.put("id", 1);
        JSONObject jsonResponse = (JSONObject) JSONParser.parseJSON(
                new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID,
                        BasicCall.REST.GET,
                        null,
                        1,
                        0));
        System.out.println(json.toString());
        System.out.println(jsonResponse.toString());

        JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.STRICT);

        //assertEquals(json.toString(), new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.GET, null, 1, 0));
        json = new JSONObject();
        json.put("oid", "2");
        json.put("friendly_name", "testName2");
        json.put("syntax", "noSyntax2");
        json.put("is_multivalue", false);

        assertEquals("", new BasicCall().call(Const.Api.ATTRIBUTESPECS, BasicCall.REST.POST, json.toString(), 0, 0));
        json.remove("is_multivalue");
        json.put("id", 2);
        //assertEquals(json.toString(), new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID, BasicCall.REST.GET, null, 2, 0));
        jsonResponse = (JSONObject) JSONParser.parseJSON(
                new BasicCall().call(Const.Api.ATTRIBUTESPECS_ID,
                        BasicCall.REST.GET,
                        null,
                        2,
                        0));
        System.out.println(json.toString());
        System.out.println(jsonResponse.toString());

        JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.STRICT);

    }

}
