package sztaki.hexaa.httputility.apicalls.services;

import org.json.simple.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;
import sztaki.hexaa.httputility.apicalls.attributes.Attributespecs;

public class ServicesInsertTest {
    
    @Test
    public void testInsertingService() {
        
        JSONObject json = new JSONObject();
        json.put("name", "myService");
        json.put("entityid", "1");
        json.put("url", "my.service.is.awsome");
        json.put("description", "My service really is awsome!");
        
        System.out.println(json.toJSONString());

        assertEquals("[]", new Services().call(HttpUtilityBasicCall.REST.POST, json.toJSONString()));

    }
    
}
