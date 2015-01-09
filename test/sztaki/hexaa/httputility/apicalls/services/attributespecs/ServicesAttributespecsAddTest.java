package sztaki.hexaa.httputility.apicalls.services.attributespecs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the /api/services/{id}/attributespecs/{asid} call.
 */
public class ServicesAttributespecsAddTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesAttributespecsAddTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates two services and two attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        Utility.Create.attributespec(new String[]{"asTest1", "asTest2"}, "user");
    }

    /**
     * Adds two attributespecs to one of the services and checks both of them.
     */
    @Test
    public void testServicesAttributespecsAdd() {
        // PUT the first attributespec to the first service.
        Utility.Link.attributespecsToService(1, 1, true);
        
        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        
        JSONArray jsonResponseArray;
        try {
            jsonResponseArray = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesAttributespecsAddTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
        
        try {
            assertEquals(
                    1,
                    jsonResponseArray
                    .getJSONObject(jsonResponseArray.length() - 1).getInt("attribute_spec_id"));
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
