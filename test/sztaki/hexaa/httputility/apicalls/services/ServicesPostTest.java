package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesPostTest extends CleanTest {

    @Test
    public void testServicePost() {

        JSONObject json = new JSONObject();
        json.put("name", "myService");
        json.put("entityid", "https://sp.example.org/shibboleth");
        json.put("url", "my.service.is.awsome");
        json.put("description", "My service really is awsome!");

        persistent.call(Const.Api.SERVICES, BasicCall.REST.POST, json.toString(), 0, 0);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            System.out.println(persistent.getStatusLine());
            collector.addError(e);
        }
    }
}
