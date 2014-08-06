package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/attributespecs/{id}/services call.
 */
public class AttributespecsServicesGetTest extends CleanTest {

    /**
     * Creates one attributespec and two services and links the attributespec to
     * the first service.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.attributespec("testOid");
        Utility.Create.service(new String[]{"testService1", "testService2"});
        Utility.Link.attributespecsToService(1, new int[]{1});
    }

    /**
     * GET the service linked to the attributespec.
     */
    @Test
    public void testAttributespecsServicesGet() {
        JSONArray jsonResponse
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ATTRIBUTESPECS_ID_SERVICES,
                                BasicCall.REST.GET));

        try {
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals("1", jsonResponse.getJSONObject(0).getString("service_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
