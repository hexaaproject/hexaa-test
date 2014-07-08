package sztaki.hexaa.httputility;

import sztaki.hexaa.httputility.apicalls.principals.Manager_Services;
import sztaki.hexaa.httputility.apicalls.principals.Member_Organizations;
import sztaki.hexaa.httputility.apicalls.principals.Manager_Organizations;
import sztaki.hexaa.httputility.BasicCall.REST;
import sztaki.hexaa.httputility.apicalls.principals.*;

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
        
        System.out.println(new BasicCall().call(
                Const.Api.PRINCIPAL_EMAILINVITATIONS,
                REST.GET,
                null,
                0,
                0));
        
        System.out.println(new BasicCall().call(
                Const.Api.PRINCIPAL_URLINVITATIONS,
                REST.GET,
                null,
                0,
                0));
        
        System.out.println(new BasicCall().call(Const.Api.PRINCIPALS, REST.GET, "", 0, 0));
        
        System.out.println(new BasicCall().call(Const.Api.PRINCIPALS, REST.POST, "[{\"fedid\": \"ede91bt@gmail.com\"}]", 0, 0));
        
        System.out.println(new BasicCall().call(Const.Api.PRINCIPAL_FEDID, REST.GET, "", 0, 0, "ede91bt@gmail.com@partners.sztaki.hu"));
        
        System.out.println(new BasicCall().call(Const.Api.PRINCIPAL_FEDID, REST.GET, "", 0, 0, "ede91bt@gmail.com"));
        
        System.out.println(new BasicCall().call(Const.Api.PRINCIPALS_ID, REST.GET, "", 1, 1));
        
        System.out.println(new BasicCall().call(Const.Api.PRINCIPALS_ID, REST.GET, "", 2, 2));
        
        
        
    }
}
