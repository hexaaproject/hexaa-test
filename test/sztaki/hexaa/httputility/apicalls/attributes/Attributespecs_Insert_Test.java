/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.attributes;

import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;

/**
 *
 * @author Bana Tibor
 */
public class Attributespecs_Insert_Test {

    @Test
    public void testInserting() {

        JSONObject json = new JSONObject();
        json.put("oid", "1");
        json.put("friendly_name", "testName1");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        assertEquals("[]", new Attributespecs().call(HttpUtilityBasicCall.REST.GET, json.toJSONString()));

        json = new JSONObject();
        json.put("oid", "2");
        json.put("friendly_name", "testName2");
        json.put("syntax", "noSyntax2");
        json.put("is_multivalue", false);

        assertEquals("[]", new Attributespecs().call(HttpUtilityBasicCall.REST.GET, json.toJSONString()));

    }
}
