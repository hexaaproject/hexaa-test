package sztaki.hexaa.httputility.apicalls.attributespecs;

import static org.junit.Assert.*;
import org.junit.Test;
import org.json.JSONObject;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST method on the /app.php/api/attributespecs call.
 */
public class AttributespecsPostTest extends CleanTest {

    /**
     * POST 2 different attributespecs.
     */
    @Test
    public void testAttributespecsPost() {
        // Create a JSON object
        JSONObject json = new JSONObject();
        json.put("oid", "1");
        json.put("friendly_name", "testName1");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        // POST the object to the server and check the StatusLine
        persistent.call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString());
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // Specify the second JSON object
        json = new JSONObject();
        json.put("oid", "2");
        json.put("friendly_name", "testName2");
        json.put("syntax", "noSyntax2");
        json.put("is_multivalue", false);

        // POST the object to the server and assert the string right away
        persistent.call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString(),
                0,
                0);
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
