package sztaki.hexaa.httputility.apicalls.attributevalueprincipals.services;

import org.json.JSONObject;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.ResponseTypeMismatchException;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the PUT method on the
 * /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueprincipalsServicesDeleteTest extends CleanTest {

    /**
     * JSONArray to store the created attributevalues.
     */
    public static JSONObject attributevalueprincipal = new JSONObject();

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueprincipalsServicesDeleteTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one attributespecs.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.service(new String[]{"service1", "service2"});
        Utility.Create.entitlementpacks(1, "entitlementpack1");
        Utility.Create.entitlementpacks(2, "entitlementpack2");

        Utility.Create.organization("Org1");
        Utility.Link.entitlementpackToOrg(1, new int[]{1, 2});

        Utility.Create.attributespec(new String[]{"testName1"}, "user");
        Utility.Link.attributespecsToService(1, new int[]{1, 2}, true);
        Utility.Link.attributespecsToService(2, new int[]{1, 2}, true);
        attributevalueprincipal = Utility.Create.attributevalueprincipal("OrgValue1", 1, new int[]{1, 2}).getJSONObject(0);
    }

    /**
     * PUT a service to the attributevalue and checks that after the put the
     * same service is available with /api/services/{id} and
     * /api/attributevalueorganizations/{id}/services/{sid}.
     */
    @Test
    public void testAttributevalueprincipalsDeleteServices() {
        Utility.Remove.serviceFromAttributevalueprincipals(1, 1);

        try {
            assertEquals(Const.StatusLine.NoContent, Utility.persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        JSONObject jsonResponse;
        try {
            jsonResponse = persistent.getResponseJSONObject(
                    Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
                    BasicCall.REST.GET,
                    null,
                    1, 1);
        } catch (ResponseTypeMismatchException ex) {
            System.out.println(ex.getFullMessage());
            fail(ex.getFullMessage());
            return;
        }

        try {
            // TODO complete after correction or other to the null response body problem of the /api/attributevalueprincipals/{id}/services/{asid}
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
