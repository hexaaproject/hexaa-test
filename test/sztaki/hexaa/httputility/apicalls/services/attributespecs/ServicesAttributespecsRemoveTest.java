package sztaki.hexaa.httputility.apicalls.services.attributespecs;

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
 * Tests the DELETE method on the /api/services/{id}/attributespecs/{asid} call.
 */
public class ServicesAttributespecsRemoveTest extends CleanTest {

    /**
     * Creates two services, two attributespecs and links them together.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.services(new String[]{"testService1", "testService2"});
        Utility.Create.attributespec(new String[]{"asTest1", "asTest2"});
        Utility.Link.attributespecsToService(1, new int[]{1, 2});
    }

    /**
     * DELETE(remove) one of the attributespecs from the first service, and
     * check both services.
     */
    @Test
    public void testServicesAttributespecsRemove() {
        persistent.call(
                Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
                BasicCall.REST.DEL);

        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            assertEquals(
                    "2",
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)))
                    .getJSONObject(0).getString("attribute_spec_id"));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(
                    "[]",
                    persistent.call(
                            Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                            BasicCall.REST.GET,
                            null,
                            2, 1));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
