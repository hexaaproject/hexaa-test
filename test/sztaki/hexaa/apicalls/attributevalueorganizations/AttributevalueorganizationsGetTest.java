package sztaki.hexaa.apicalls.attributevalueorganizations;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/attributevalueorganizations/{id} call.
 */
public class AttributevalueorganizationsGetTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONArray attributevalues = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueorganizationsGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization("Org1");
        Utility.Create.attributespec(new String[]{"testName1"}, "user");
        attributevalues = Utility.Create.attributevalueorganization("OrgValue1", 1, 1);
    }

    /**
     * Gets the attributevalue and checks it to the stored one.
     */
    @Test
    public void testAttributevalueorganizationGet() {
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(AttributevalueorganizationsGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
        
        JSONObject jsonTemp = attributevalues.getJSONObject(0);
        jsonTemp.put("organization_id",jsonTemp.remove("organization"));
        jsonTemp.put("attribute_spec_id",jsonTemp.remove("attribute_spec"));
        jsonTemp.put("service_ids",jsonTemp.remove("services"));
        
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            JSONAssert.assertEquals(jsonTemp, jsonResponse, JSONCompareMode.LENIENT);
        } catch(AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
