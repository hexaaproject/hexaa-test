package sztaki.hexaa.httputility.apicalls.attributespecs;

import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class AttributespecsDeleteTest extends CleanTest {

    /**
     * Initialize an Attributespecs object on the server (POST
     * /api/attributespecs)
     */
    @BeforeClass
    public static void setUpClass() {

        JSONObject json1 = new JSONObject();

        json1.put("oid", "1");
        json1.put("friendly_name", "testName1");
        json1.put("syntax", "noSyntax1");
        json1.put("is_multivalue", false);
        persistent.call(
                Const.Api.ATTRIBUTESPECS,
                BasicCall.REST.POST,
                json1.toString(),
                0, 0);

        // Fail the test class if the BeforeClass was unsuccessful at creating the necessary attributespecs
        try {
            Assume.assumeTrue(persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
        } catch (AssumptionViolatedException e) {
            System.out.println(
                    "In "
                    + AttributespecsDeleteTest.class.getName()
                    + " the first POST call failed");
            fail("POST /api/attributespecs was unsuccessful.");
        }

    }

    /**
     * It calls DELETE and verifies the non existence by calling
     * /api/attributespec GET
     */
    @Test
    public void testAttributespecsDelete() {

        /* *** Calling DELETE *** */
        String response = persistent.call(
                Const.Api.ATTRIBUTESPECS_ID,
                BasicCall.REST.DELETE,
                null,
                1, 0);
        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }

        /* *** Verifing the delete *** */
        try {
            assertEquals("[]", persistent.call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
