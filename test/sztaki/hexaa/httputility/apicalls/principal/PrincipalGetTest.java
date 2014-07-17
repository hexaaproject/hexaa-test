package sztaki.hexaa.httputility.apicalls.principal;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;
import sztaki.hexaa.httputility.apicalls.services.Services;

public class PrincipalGetTest extends CleanTest{

    public static JSONArray organizations = new JSONArray();
    public static JSONArray services = new JSONArray();
    
    
    @BeforeClass
    public static void setUpClass() {
        organizations = Organization.createOrganization(new String[] {"testOrgForPrincGet"});
        services = Services.createServices(new String[] {"testServForPrincGet"});
        
    }
    
}
