package sztaki.hexaa.httputility.apicalls.attributevalueorganizations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;

/**
 * Tests the POST method on the /api/attributevalueorganizations/{asid} call.
 */
public class AttributevalueorganizationsPost extends CleanTest {

    /**
     * Creates two attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespecs(
                new String[]{
                    "testAttributespecs1",
                    "testAttributespecs2"});
        Organization.createOrganization(new String[]{"testForAtt"});
    }

    /**
     * Creates an attributevalueorganization with one value and one of the
     * created attributespecs, and verifies it with GET.
     */
    @Test
    public void testAttributevalueorganizationsPost() {
        JSONObject json = new JSONObject();
        json.put("value", "testValueString");

        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATIONS_ASID,
                BasicCall.REST.POST,
                json.toString(),
                1, 1);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    json,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
