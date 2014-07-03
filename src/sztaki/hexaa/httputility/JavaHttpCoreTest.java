package sztaki.hexaa.httputility;

import org.json.simple.JSONObject;
import sztaki.hexaa.httputility.HttpUtilityBasicCall.REST;
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

        DatabaseManipulator dm = new DatabaseManipulator();
        dm.dropDatabase();

        JavaHttpCoreTest HCT = new JavaHttpCoreTest();
        
        HttpUtilityBasicCall organizationsMembers = new Organizations_ID_Members_PID();
        
        organizationsMembers.setId(1);
        organizationsMembers.setSId(1);
        
        System.out.println(organizationsMembers.call(REST.GET,1,1));
        System.out.println(organizationsMembers.call(REST.POST,1,1));
        System.out.println(organizationsMembers.call(REST.PUT,1,1));
        System.out.println(organizationsMembers.call(REST.DELETE,1,1));
        
        
 //       HCT.printAllCall(organizationsMembers);

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

    private void printAllCall(HttpUtilityBasicCall call) {
        System.out.println(call.call(REST.GET));
        System.out.println(call.call(REST.POST));
        System.out.println(call.call(REST.PUT));
        System.out.println(call.call(REST.DELETE));
    }

    private void runAll() {
        System.out.println("entityIds");
        this.printAllCall(new EntityIds());

        System.out.println("manager_organizations");
        this.printAllCall(new Manager_Organizations());

        System.out.println("manager_services");
        this.printAllCall(new Manager_Services());

        System.out.println("member_organizations");
        this.printAllCall(new Member_Organizations());

        System.out.println("attributespecs");
        this.printAllCall(new Attributespecs());

        System.out.println("attributespecs_id");
        this.printAllCall(new Attributespecs_ID());

        System.out.println("attributevalueorganizations_id");
        this.printAllCall(new AttributevalueOrganizations_ID());

        System.out.println("attributevalueprincipals_asid");
        this.printAllCall(new AttributevaluePrincipals_ASID());

        System.out.println("attributevalueprincipals_id");
        this.printAllCall(new AttributevaluePrincipals_ID());

        System.out.println("attributevalueprincipals_id_consents");
        this.printAllCall(new AttributevaluePrincipals_ID_Consents());

        System.out.println("attributevalueprincipals_id_services_sid");
        this.printAllCall(new AttributevaluePrincipals_ID_Services_SID());

        System.out.println("entitlementpacks_id");
        this.printAllCall(new Entitlementpacks_ID());

        System.out.println("entitlementpacks_id_entitlements");
        this.printAllCall(new Entitlementpacks_ID_Entitlements());

        System.out.println("entitlementpacks_id_entitlements_eid");
        this.printAllCall(new Entitlementpacks_ID_Entitlements_EID());

        System.out.println("organizations");
        this.printAllCall(new Organizations());

        System.out.println("organizations_id");
        this.printAllCall(new Organizations_ID());

        System.out.println("organizations_id_attributespecs");
        this.printAllCall(new Organizations_ID_Attributespecs());

        System.out.println("organizations_id_attributevalueorganizations_asid");
        this.printAllCall(new Organizations_ID_Attributevalueorganizations_ASID());

        System.out.println("organizations_id_entitlementpacks");
        this.printAllCall(new Organizations_ID_Entitlementpacks());

        System.out.println("organizations_id_entitlementpacks_epid");
        this.printAllCall(new Organizations_ID_Entitlementpacks_EPID());

        System.out.println("organizations_id_entitlementpacks_token");
        this.printAllCall(new Organizations_ID_Entitlementpacks_TOKEN());

        System.out.println("organizations_id_entitlements");
        this.printAllCall(new Organizations_ID_Entitlements());

        System.out.println("organizations_id_manager");
        this.printAllCall(new Organizations_ID_Manager());

        System.out.println("organizations_id_manager_pid");
        this.printAllCall(new Organizations_ID_Manager_PID());

        System.out.println("organizations_id_members");
        this.printAllCall(new Organizations_ID_Members());

        System.out.println("organizations_id_members_pid");
        this.printAllCall(new Organizations_ID_Members_PID());

        System.out.println("organizations_id_roles");
        this.printAllCall(new Organizations_ID_Roles());

        System.out.println("principal");
        this.printAllCall(new Principal());

        System.out.println("principal_attributespecs");
        this.printAllCall(new Principal_Attributespecs());

        System.out.println("principal_fedid");
        this.printAllCall(new Principal_FEDID());

        System.out.println("principals_id");
        this.printAllCall(new Principals_ID());

        System.out.println("principals");
        this.printAllCall(new Principals());

        System.out.println("roles_id");
        this.printAllCall(new Roles_ID());

        System.out.println("roles_id_principals");
        this.printAllCall(new Roles_ID_Principals());

        System.out.println("roles_id_principals_pid");
        this.printAllCall(new Roles_ID_Principals_PID());

        System.out.println("services");
        this.printAllCall(new Services());

        System.out.println("services_attributespecs");
        this.printAllCall(new Services_Attributespecs());

        System.out.println("services_attributespecs_asid");
        this.printAllCall(new Services_Attributespecs_ASID());

        System.out.println("services_entitlementpacks");
        this.printAllCall(new Services_Entitlementpacks());

        System.out.println("services_entitlements");
        this.printAllCall(new Services_Entitlements());

        System.out.println("services_id");
        this.printAllCall(new Services_ID());

        System.out.println("services_managers");
        this.printAllCall(new Services_Managers());

        System.out.println("services_managers_pid");
        this.printAllCall(new Services_Managers_PID());
    }

}
