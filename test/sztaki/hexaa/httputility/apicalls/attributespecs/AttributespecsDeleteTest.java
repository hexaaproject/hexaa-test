package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsDeleteTest extends CleanTest {

    /**
     * POST-s on /api/attributespecs to the server, verifies it by
     * /api/attributespecs_id GET, after that it calls DELETE and verifies the
     * non existence by calling /api/attributespec GET and assert it with the
     * local array
     */
    @Test
    public void testAttributespecsDelete() {

        /* *** POST a new object *** */
        JSONObject json = new JSONObject();

        json.put("oid", "3");
        json.put("friendly_name", "testName3");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        persistent.call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json.toString(),
                0, 0);
        json.put("id", 1);
        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        /* *** Verify the data on the server *** */
        String response = persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.GET,
                null,
                1, 0);

        JSONObject jsonOResponse = null;

        jsonOResponse = (JSONObject) JSONParser.parseJSON(response);

        if (jsonOResponse != null && jsonOResponse.isNull("is_multivalue")) {
            jsonOResponse.put("is_multivalue", false);
        }
        if (jsonOResponse == null) {
            jsonOResponse = new JSONObject();
        }
        try {
            JSONAssert.assertEquals(json,
                    jsonOResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        /* *** Calling DELETE *** */
        response = persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.DELETE,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        /* *** Verifing the delete *** */
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
