package sztaki.hexaa.apicalls.attributespecs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/attributespecs/{id}/services call.
 */
public class AttributespecsServicesGetTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributespecsServicesGetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespec and two services and links the attributespec to
     * the first service.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespec("testOid", "user");
        Utility.Create.service(new String[]{"testService1", "testService2"});
        Utility.Link.attributespecsToService(1, new int[]{1}, true);
    }

    /**
     * GET the service linked to the attributespec.
     */
    @Test
    public void testAttributespecsServicesGet() {
        JSONArray jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.ATTRIBUTESPECS_ID_SERVICES,
                    BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(AttributespecsServicesGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals(1, jsonResponse.getJSONObject(0).getInt("service_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
