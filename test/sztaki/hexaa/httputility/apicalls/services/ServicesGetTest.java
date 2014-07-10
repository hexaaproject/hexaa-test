package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesGetTest extends CleanTest {

    private static JSONObject service;

    @BeforeClass
    public static void buildUp() {
        // GET the existing entityids
        JSONArray jsonEntityArray = (JSONArray) JSONParser.parseJSON(
                new BasicCall().call(
                        Const.Api.ENTITYIDS,
                        BasicCall.REST.GET,
                        null,
                        0,
                        0));

        // Creates the json object to be POSTed on the server
        service = new JSONObject();
        service.put("name", "myService");
        service.put("entityid", jsonEntityArray.getString(0));
        service.put("url", "my.service.is.awsome");
        service.put("description", "My service really is awsome!");
        // POSTs the json object
        persistent.call(
                Const.Api.SERVICES,
                BasicCall.REST.POST,
                service.toString(),
                0, 0);
    }

    @Test
    public void testServiceGet() {

    }
}
