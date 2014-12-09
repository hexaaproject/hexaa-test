package sztaki.hexaa.httputility.apicalls.services.entitlementpacks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the GET method on the /api/services/{id}/entitlementpacks/requests.
 */
public class ServicesEntitlementpacksRequestTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + ServicesEntitlementpacksRequestTest.class.getSimpleName() + " ***");
    }

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each.
     */
    @BeforeClass
    public static void buildUp() {
        Utility.Create.service(new String[]{"testService1"});
        Utility.Create.organization("testOrg");
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpacks1", "testEntitlementpacks2"});
        Utility.Link.entitlementpackToOrgRequest(1, 1);
    }

    /**
     * Tests the call.
     */
    @Test
    public void testServicesEntitlementpacksRequest() {
        JSONArray jsonResponse;
        
        try {
            jsonResponse = persistent.getResponseJSONArray(
                    Const.Api.SERVICES_ID_ENTITLEMENTPACKS_REQUESTS,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            Logger.getLogger(ServicesEntitlementpacksRequestTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getFullMessage());
            return;
        }
        
        try {
            assertEquals(1, jsonResponse.getJSONObject(0).get("organization_id"));
            assertEquals(1, jsonResponse.getJSONObject(0).get("entitlement_pack_id"));
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
