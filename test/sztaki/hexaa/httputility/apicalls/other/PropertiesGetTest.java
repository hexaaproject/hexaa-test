package sztaki.hexaa.httputility.apicalls.other;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/properties call.
 */
public class PropertiesGetTest extends CleanTest {
    
    /**
     * GET the properties and checks that the basic informations are present.
     */
    @Test
    public void testPropertiesGet() {
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(Const.Api.PROPERTIES, BasicCall.REST.GET);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(PropertiesGetTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
        System.out.println(jsonResponse.toString());
        
        try {
            assertEquals(true, jsonResponse.has("entitlement_base"));
            assertEquals(true, jsonResponse.has("version"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
