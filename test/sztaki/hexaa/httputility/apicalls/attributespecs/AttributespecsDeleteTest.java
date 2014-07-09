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
        JSONObject json3 = new JSONObject();

        json3.put("oid", "3");
        json3.put("friendly_name", "testName3");
        json3.put("syntax", "noSyntax1");
        json3.put("is_multivalue", false);

        new BasicCall().call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json3.toString(),
                0, 0);
        json3.put("id", 1);

        /* *** Verify the data on the server *** */
        String response = new BasicCall().call(
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
            JSONAssert.assertEquals(json3,
                    jsonOResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            collector.addError(e);
        }

        /* *** Calling DELETE *** */
        new BasicCall().call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.DELETE,
                null,
                1, 0);

        /* *** Verifing the delete *** */
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
        }
    }
}
