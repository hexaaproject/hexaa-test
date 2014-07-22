package sztaki.hexaa.httputility.apicalls.roles;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class RolesDeleteTest extends CleanTest{
    
    public static JSONArray roles = new JSONArray();
    
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[] {"testOrgForRoleDel"});
        roles = Utility.Create.roles(new String[] {"testRole1","testRole2"}, 1);
    }
    
    @Test
    public void testRolesDelete() {
        persistent.call(Const.Api.ROLES_ID, BasicCall.REST.DEL);
        
        try {
            assertEquals("HTTP/1.1 204 No Content", persistent.getStatusLine());
            JSONAssert.assertEquals(
                    roles.getJSONObject(1),
                    ((JSONArray) JSONParser.parseJSON(
                            persistent.call(
                                    Const.Api.ORGANIZATIONS_ID_ROLES,
                                    BasicCall.REST.GET))).getJSONObject(0),
                    JSONCompareMode.LENIENT);
        }catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
