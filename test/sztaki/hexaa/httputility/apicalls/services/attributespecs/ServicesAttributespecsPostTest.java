package sztaki.hexaa.httputility.apicalls.services.attributespecs;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesAttributespecsPostTest extends CleanTest {

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each and creates 2 attributespecs to work
     * with as well.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.services(new String[]{"testService1", "testService2"});
        Utility.Create.attributespecs(new String[]{"asTest1", "asTest2"});
    }

    /**
     * Adds two attributespecs to one of the services and checks both of them.
     */
    @Test
    public void testServicesAttributespecsPost() {
        // POSTs the first attributespec to the first service
        persistent.call(
                Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
                BasicCall.REST.PUT,
                null,
                1, 1);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            assertEquals(
                    "1",
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)))
                    .getJSONObject(0).getString("attribute_spec_id"));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        // POSTs the second attributespec to the first service as well
        persistent.call(
                Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
                BasicCall.REST.PUT,
                null,
                1, 2);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
            assertEquals(
                    "1",
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)))
                    .getJSONObject(0).getString("attribute_spec_id"));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
            assertEquals(
                    "2",
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)))
                    .getJSONObject(1).getString("attribute_spec_id"));
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
