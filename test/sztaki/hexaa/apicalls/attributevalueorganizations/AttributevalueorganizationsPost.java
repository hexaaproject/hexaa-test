package sztaki.hexaa.apicalls.attributevalueorganizations;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the POST method on the /api/attributevalueorganizations/{asid} call.
 */
public class AttributevalueorganizationsPost extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueorganizationsPost.class.getSimpleName() + " ***");
    }

    /**
     * Creates the necessary objects on the server to begin the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespec(
                new String[]{
                    "testAttributespecs1",
                    "testAttributespecs2"},
                "manager");
        Utility.Create.organization(new String[]{"testForAtt"});
        Utility.Create.service("testService");
        Utility.Link.attributespecsToService(1, 1, true);
    }

    /**
     * Creates an attributevalueorganization with one value and one of the
     * created attributespecs, and verifies it with GET.
     */
    @Test
    public void testAttributevalueorganizationsPost() {
        JSONObject json = new JSONObject();
        json.put("value", "testValueString");
        json.put("service_ids", new JSONArray(new int[]{}));
        json.put("attribute_spec_id", 1);
        json.put("organization_id", 1);

        Utility.Create.attributevalueorganization("testValueString", 1, 1);

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        JSONArray jsonArrayResponse;
        try {
            jsonArrayResponse = persistent.getResponseJSONArray(
                    Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(AttributevalueorganizationsPost.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        JSONObject jsonResponse = jsonArrayResponse.getJSONObject(0);
        System.out.println(jsonResponse);
        try {
            JSONAssert.assertEquals(
                    json,
                    jsonResponse,
                    JSONCompareMode.LENIENT);
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
