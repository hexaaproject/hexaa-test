package sztaki.hexaa.httputility.apicalls.roles.entitlements;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class RolesEntitlementsGet extends CleanTest {

    public static JSONArray entitlements = new JSONArray();
    
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrg1"});
        Utility.Create.roles(new String[]{"testRole1"}, 1);
        Utility.Create.services(new String[]{"testService1"});
        entitlements = Utility.Create.entitlements(1, new String[]{"testEntitlement1","testEntitlement2"});
        Utility.Create.entitlementpacks(1, new String[]{"testEntitlementpack1"});

        Utility.Link.entitlementToPack(1, 1);
        //Utility.Link.entitlementToPack(2, 1);
        Utility.Link.entitlementpackToOrg(1, new int[]{1});
        
        System.out.println(persistent.call(Const.Api.ENTITLEMENTS_ID, BasicCall.REST.GET));

        Utility.Link.entitlementToRole(1, new int[]{2,1});
    }
    
    @Test
    public void testRolesEntitlementsGet() {
        
        JSONArray jsonResponseArray
                = (JSONArray) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.ROLES_ID_ENTITLEMENTS,
                                BasicCall.REST.GET));
        System.out.println(jsonResponseArray.toString());
        try {
            JSONAssert.assertEquals(
                    entitlements,
                    jsonResponseArray,
                    JSONCompareMode.LENIENT);
        }catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

}
