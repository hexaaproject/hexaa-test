package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesPostTest extends CleanTest {

    /**
     * Test for the /app.php/api/services POST call, creates a new Service on
     * the sample entityid and verifies the success
     */
    @Test
    public void testServicePost() {
        // GET the existing entityids
        JSONArray jsonEntityArray = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.ENTITYIDS,
                        BasicCall.REST.GET,
                        null,
                        0,
                        0));

        // Creates the json object to be POSTed on the server
        JSONObject json = new JSONObject();
        json.put("name", "myService");
        json.put("entityid", jsonEntityArray.getString(0));
        json.put("url", "my.service.is.awsome");
        json.put("description", "My service really is awsome!");
        // POSTs the json object
        persistent.call(
                Const.Api.SERVICES,
                BasicCall.REST.POST,
                json.toString(),
                0, 0);
        // Checks the creation by Status Line
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // GETs the service by id for verification
        JSONObject jsonResponse = (JSONObject) JSONParser.parseJSON(persistent.call(
                Const.Api.SERVICES_ID,
                BasicCall.REST.GET,
                null,
                1, 0));
        // Checks the service by StatusLine and id as well
        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            JSONAssert.assertEquals(json, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
