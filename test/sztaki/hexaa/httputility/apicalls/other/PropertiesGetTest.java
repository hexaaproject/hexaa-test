package sztaki.hexaa.httputility.apicalls.other;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 */
public class PropertiesGetTest extends CleanTest {
    
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
