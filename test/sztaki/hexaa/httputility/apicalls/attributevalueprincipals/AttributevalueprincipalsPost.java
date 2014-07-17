package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.attributespecs.Attributespecs;

/**
 * Tests the POST method on the /api/attributevalueprincipals/{asid} call.
 */
public class AttributevalueprincipalsPost extends CleanTest {

    /**
     * Creates two attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Attributespecs.createAttributespecs(
                new String[]{
                    "testAttributespecs1",
                    "testAttributespecs2"});
    }

    /**
     * Creates an attributevalueprincipal with one value and one of the created
     * attributespecs, and verifies it with GET.
     */
    @Test
    public void testAttributevalueprincipalsPost() {
        JSONObject json = new JSONObject();
        json.put("value", "testValueString");

        persistent.call(
                Const.Api.ATTRIBUTEVALUEPRINCIPALS_ASID,
                BasicCall.REST.POST,
                json.toString(),
                1, 1);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
