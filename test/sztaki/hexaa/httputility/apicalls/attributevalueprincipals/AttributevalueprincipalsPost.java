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
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST method on the /api/attributevalueprincipals/{asid} call.
 */
public class AttributevalueprincipalsPost extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueprincipalsPost.class.getSimpleName() + " ***");
    }

    /**
     * Creates two attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespec(
                new String[]{
                    "testAttributespecs1",
                    "testAttributespecs2"},
                "user");
    }

    /**
     * Creates an attributevalueprincipal with one value and one of the created
     * attributespecs, and verifies it with GET.
     */
    @Test
    public void testAttributevalueprincipalsPost() {
        JSONObject json = new JSONObject();
        json.put("value", "testValueString");
        json.put("service_ids", new JSONArray(new int[]{1}));
        json.put("attribute_spec_id", 1);
        json.put("organization_id", 1);

        Utility.Create.attributevalueprincipal("testValueString", 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
