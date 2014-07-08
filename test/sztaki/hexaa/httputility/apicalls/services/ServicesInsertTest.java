package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.BasicCall;

public class ServicesInsertTest {

    @BeforeClass
    public static void setUpClass() {
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    @Test
    public void testInsertingService() {

        JSONObject json = new JSONObject();
        json.put("name", "myService");
        json.put("entityid", "https://sp.example.org/shibboleth");
        json.put("url", "my.service.is.awsome");
        json.put("description", "My service really is awsome!");

        System.out.println(json.toString());

        String response = new Services().call(BasicCall.REST.POST, json.toString());
        System.out.println(response);

        assertEquals("", response);

    }

}