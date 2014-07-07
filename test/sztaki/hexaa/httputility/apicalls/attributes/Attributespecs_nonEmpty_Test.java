/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.attributes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;

/**
 *
 * @author Bana Tibor
 */
public class Attributespecs_nonEmpty_Test {

    private static JSONArray array = new JSONArray();

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

    @Test
    public void testNotEmpty() {

        String response = new Attributespecs().call(HttpUtilityBasicCall.REST.GET);

        JSONObject jsonResponse = null;

        try {
            jsonResponse = (JSONObject) ((JSONArray) new JSONParser().parse(response)).get(0);
        } catch (ParseException ex) {
            Logger.getLogger(Attributespecs_nonEmpty_Test.class.getName()).log(
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

    @Test
    public void testGetSecond() {

        String response
                = new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 2, 0);

        JSONObject jsonResponse = null;

        try {
            jsonResponse = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(Attributespecs_nonEmpty_Test.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        if (jsonResponse != null && jsonResponse.get("is_multivalue") == null) {
            jsonResponse.put("is_multivalue", false);
        }
        if (jsonResponse == null) {
            jsonResponse = new JSONObject();
        }
        assertEquals(((JSONObject) array.get(1)).toJSONString(),
                jsonResponse.toJSONString());
    }

    @Test
    public void testArray() {

        String response = new Attributespecs().call(HttpUtilityBasicCall.REST.GET);

        JSONArray jsonResponse = null;

        try {
            jsonResponse = (JSONArray) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(Attributespecs_nonEmpty_Test.class.getName()).log(
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

    @Test
    public void testPut() {
        // Changes that we want to use in the PUT
        ((JSONObject) array.get(1)).put("oid", "oidByPut");
        ((JSONObject) array.get(1)).put("friendly_name", "nameByPut");
        // Remove the id key for the call, and readd it after the call, so the 
        // other tests are not effected
        System.out.println(((JSONObject) array.get(1)).toJSONString());
        int idTemp = (int) ((JSONObject) array.get(1)).remove("id");
        new Attributespecs_ID().call(HttpUtilityBasicCall.REST.PUT,
                ((JSONObject) array.get(1)).toJSONString(), 2, 0);
        ((JSONObject) array.get(1)).put("id", idTemp);
        
        
        String response
                = new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 2, 0);

        JSONObject jsonResponse = null;

        try {
            jsonResponse = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(Attributespecs_nonEmpty_Test.class.getName()).log(
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

    @Test
    public void testDelete() {
        
    }
}
