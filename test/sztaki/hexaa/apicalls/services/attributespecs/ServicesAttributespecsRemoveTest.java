package sztaki.hexaa.apicalls.services.attributespecs;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the DELETE method on the /api/services/{id}/attributespecs/{asid} call.
 */
public class ServicesAttributespecsRemoveTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesAttributespecsRemoveTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates two services, two attributespecs and links them together.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"testService1", "testService2"});
        Utility.Create.attributespec(new String[]{"asTest1", "asTest2"}, "user");
        Utility.Link.attributespecsToService(1, new int[]{1, 2}, true);
    }

    /**
     * DELETE(remove) one of the attributespecs from the first service, and
     * check both services.
     */
    @Test
    public void testServicesAttributespecsRemove() {
        Utility.Remove.attributespecFromService(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
            assertEquals(
                    2,
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                                    BasicCall.REST.GET,
                                    null,
                                    1, 1)))
                    .getJSONObject(0).getInt("attribute_spec_id"));
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals(
                    "[]",
                    persistent.call(
                            Const.Api.SERVICES_ID_ATTRIBUTESPECS,
                            BasicCall.REST.GET,
                            null,
                            2, 1));
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
