package sztaki.hexaa.httputility;

import org.json.simple.JSONObject;
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

    private boolean filterOn;

    public static void main(String[] args) {
        
        DatabaseManipulator dm = new DatabaseManipulator();
        dm.dropDatabase();
        
        JavaHttpCoreTest HCT = new JavaHttpCoreTest();

//        System.out.println("attributespecs");
//
//        HttpUtilityBasicCall attributeSpecs = new Attributespecs();
//
//        JSONObject json = new JSONObject();
//        json.put("oid", "testOid");
//        json.put("friendlyName", "testFriendlyName");
//        json.put("description", "testDiscription");
//        json.put("syntax", "testSyntax");
//        json.put("isMultivalue", false);
//
//        HCT.out(json.toJSONString());
//        HCT.out(attributeSpecs.call(HttpUtilityBasicCall.REST.POST, json.toJSONString()));
//        HCT.runAll();
    }

    public JavaHttpCoreTest() {
        this.filterOn = false;
    }

    private void printAllCall(HttpUtilityBasicCall call) {
        this.out(call.call(HttpUtilityBasicCall.REST.GET));
        this.out(call.call(HttpUtilityBasicCall.REST.POST));
        this.out(call.call(HttpUtilityBasicCall.REST.PUT));
        this.out(call.call(HttpUtilityBasicCall.REST.DELETE));
    }

    private void runAll() {
        this.out("entityIds");
        this.printAllCall(new EntityIds());

        this.out("manager_organizations");
        this.printAllCall(new Manager_Organizations());

        this.out("manager_services");
        this.printAllCall(new Manager_Services());

        this.out("member_organizations");
        this.printAllCall(new Member_Organizations());

        this.out("attributespecs");
        this.printAllCall(new Attributespecs());

        this.out("attributespecs_id");
        this.printAllCall(new Attributespecs_ID());

        this.out("attributevalueorganizations_id");
        this.printAllCall(new AttributevalueOrganizations_ID());

        this.out("attributevalueprincipals_asid");
        this.printAllCall(new AttributevaluePrincipals_ASID());

        this.out("attributevalueprincipals_id");
        this.printAllCall(new AttributevaluePrincipals_ID());

        this.out("attributevalueprincipals_id_consents");
        this.printAllCall(new AttributevaluePrincipals_ID_Consents());

        this.out("attributevalueprincipals_id_services_sid");
        this.printAllCall(new AttributevaluePrincipals_ID_Services_SID());

        this.out("entitlementpacks_id");
        this.printAllCall(new Entitlementpacks_ID());

        this.out("entitlementpacks_id_entitlements");
        this.printAllCall(new Entitlementpacks_ID_Entitlements());

        this.out("entitlementpacks_id_entitlements_eid");
        this.printAllCall(new Entitlementpacks_ID_Entitlements_EID());

        this.out("organizations");
        this.printAllCall(new Organizations());

        this.out("organizations_id");
        this.printAllCall(new Organizations_ID());

        this.out("organizations_id_attributespecs");
        this.printAllCall(new Organizations_ID_Attributespecs());

        this.out("organizations_id_attributevalueorganizations_asid");
        this.printAllCall(new Organizations_ID_Attributevalueorganizations_ASID());

        this.out("organizations_id_entitlementpacks");
        this.printAllCall(new Organizations_ID_Entitlementpacks());

        this.out("organizations_id_entitlementpacks_epid");
        this.printAllCall(new Organizations_ID_Entitlementpacks_EPID());

        this.out("organizations_id_entitlementpacks_token");
        this.printAllCall(new Organizations_ID_Entitlementpacks_TOKEN());

        this.out("organizations_id_entitlements");
        this.printAllCall(new Organizations_ID_Entitlements());

        this.out("organizations_id_manager");
        this.printAllCall(new Organizations_ID_Manager());

        this.out("organizations_id_manager_pid");
        this.printAllCall(new Organizations_ID_Manager_PID());

        this.out("organizations_id_members");
        this.printAllCall(new Organizations_ID_Members());

        this.out("organizations_id_members_pid");
        this.printAllCall(new Organizations_ID_Members_PID());

        this.out("organizations_id_roles");
        this.printAllCall(new Organizations_ID_Roles());

        this.out("principal");
        this.printAllCall(new Principal());

        this.out("principal_attributespecs");
        this.printAllCall(new Principal_Attributespecs());

        this.out("principal_fedid");
        this.printAllCall(new Principal_FEDID());

        this.out("principals_id");
        this.printAllCall(new Principals_ID());

        this.out("principals");
        this.printAllCall(new Principals());

        this.out("roles_id");
        this.printAllCall(new Roles_ID());

        this.out("roles_id_principals");
        this.printAllCall(new Roles_ID_Principals());

        this.out("roles_id_principals_pid");
        this.printAllCall(new Roles_ID_Principals_PID());

        this.out("services");
        this.printAllCall(new Services());

        this.out("services_attributespecs");
        this.printAllCall(new Services_Attributespecs());

        this.out("services_attributespecs_asid");
        this.printAllCall(new Services_Attributespecs_ASID());

        this.out("services_entitlementpacks");
        this.printAllCall(new Services_Entitlementpacks());

        this.out("services_entitlements");
        this.printAllCall(new Services_Entitlements());

        this.out("services_id");
        this.printAllCall(new Services_ID());

        this.out("services_managers");
        this.printAllCall(new Services_Managers());

        this.out("services_managers_pid");
        this.printAllCall(new Services_Managers_PID());
    }

    public void out(String source) {
        if (filterOn && (source.equalsIgnoreCase("{\"code\":405,\"message\":\"Method Not Allowed\"}")
                || source.equalsIgnoreCase("{\"code\":404,\"message\":\"Not Found\"}"))) {

        } else {
            System.out.println(source);
        }
    }

}
