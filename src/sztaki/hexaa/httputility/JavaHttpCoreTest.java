package sztaki.hexaa.httputility;

import sztaki.hexaa.httputility.apicalls.principals.Manager_Services;
import sztaki.hexaa.httputility.apicalls.principals.Member_Organizations;
import sztaki.hexaa.httputility.apicalls.principals.Manager_Organizations;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import sztaki.hexaa.httputility.BasicCall.REST;
import sztaki.hexaa.httputility.apicalls.*;
import sztaki.hexaa.httputility.apicalls.attributes.*;
import sztaki.hexaa.httputility.apicalls.entitlements.*;
import sztaki.hexaa.httputility.apicalls.organizations.*;
import sztaki.hexaa.httputility.apicalls.principals.*;
import sztaki.hexaa.httputility.apicalls.roles.*;
import sztaki.hexaa.httputility.apicalls.services.Services;
import sztaki.hexaa.httputility.apicalls.services.Services_Attributespecs;
import sztaki.hexaa.httputility.apicalls.services.Services_Attributespecs_ASID;
import sztaki.hexaa.httputility.apicalls.services.Services_Entitlementpacks;
import sztaki.hexaa.httputility.apicalls.services.Services_Entitlements;
import sztaki.hexaa.httputility.apicalls.services.Services_ID;
import sztaki.hexaa.httputility.apicalls.services.Services_Managers;
import sztaki.hexaa.httputility.apicalls.services.Services_Managers_PID;

/**
 *
 * @author Bana Tibor
 */
public class JavaHttpCoreTest {

    public static void main(String[] args) {

        JavaHttpCoreTest HCT = new JavaHttpCoreTest();

        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
        
        System.out.println(new Manager_Organizations().call(REST.GET));

        System.out.println(new Manager_Services().call(REST.GET));
        
        System.out.println(new Member_Organizations().call(REST.GET));
        
        System.out.println(new Principal().call(REST.GET));
        
        System.out.println(new Principal_Attributespecs().call(REST.GET));
        
        System.out.println(new Principal_Attributespecs_Attributevalueprincipal().call(REST.GET));
        
        System.out.println(new Principal_Attributevalueprincipal().call(REST.GET));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPAL_EMAILINVITATIONS,REST.GET,"",0,0));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPAL_URLINVITATIONS, REST.GET, "", 0, 0));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPALS, REST.GET, "", 0, 0));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPALS, REST.POST, "[{\"fedid\": \"ede91bt@gmail.com\"}]", 0, 0));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPAL_FEDID, REST.GET, "", 0, 0, "ede91bt@gmail.com@partners.sztaki.hu"));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPAL_FEDID, REST.GET, "", 0, 0, "ede91bt@gmail.com"));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPALS_ID, REST.GET, "", 1, 1));
        
        System.out.println(new BasicCall().call(ServerConstants.ApiCalls.PRINCIPALS_ID, REST.GET, "", 2, 2));
        
    }
}
