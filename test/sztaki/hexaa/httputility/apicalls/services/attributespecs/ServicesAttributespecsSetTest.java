package sztaki.hexaa.httputility.apicalls.services.attributespecs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 */
public class ServicesAttributespecsSetTest extends CleanTest {

    /**
     * JSONArray to store the created attributespecs.
     */
    public static JSONArray attributespecs = new JSONArray();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesAttributespecsSetTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates an organization, two role, a service and a principal.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1"});
        attributespecs = Utility.Create.attributespec(new String[]{"1", "2"}, "manager");
    }

    /**
     * PUT the attributespecs to a service as an array.
     */
    @Test
    public void testServicesAttributespecsSet() {
        Utility.Link.attributespecsToServiceByArray(1, new int[]{1, 2}, new boolean[]{true, true});

        try {
            assertEquals(Const.StatusLine.Created, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        System.out.println(Utility.persistent.getResponse());

        JSONArray jsonResponse;

        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesAttributespecsSetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }

        try {
            JSONAssert.assertEquals(attributespecs, jsonResponse, JSONCompareMode.LENIENT);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
