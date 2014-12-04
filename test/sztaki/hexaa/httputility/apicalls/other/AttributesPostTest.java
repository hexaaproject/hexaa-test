package sztaki.hexaa.httputility.apicalls.other;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;

/**
 * Tests the POST method on the /api/attributes call, that returns all the
 * attributes without the normal authentication process for SSP.
 */
public class AttributesPostTest {

    
    
    @Test
    public void testAttributesPost() {
        JSONObject json = new JSONObject();
        
        json.put("fedid", Const.HEXAA_FEDID);
        json.put("entityid", "https://example.com/ssp");
        json.put("apikey", new Authenticator().getAPIKey());
        
        BasicCall call = new BasicCall();
        String response;
        
        response = call.call(Const.Api.ATTRIBUTES, BasicCall.REST.POST, json.toString());
        
        System.out.println(response);
        
        try {
            assertEquals(Const.StatusLine.OK, call.getStatusLine());
        } catch (AssertionError e) {
            fail(e.getLocalizedMessage());
        }
    }
}
