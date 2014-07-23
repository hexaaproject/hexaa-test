package sztaki.hexaa.httputility;

/**
 * Contains constants for the different keys, URI-s, and other server specific
 * data.
 */
public class Const {

    public static final int HEXAA_PORT = 80;
    public static final String HEXAA_HEADER = ("X-HEXAA-AUTH");
    public static String HEXAA_AUTH = ("1c04c81498d1e534abe433998c48c60c9df628d7ce0ab60897546ac4599edd28");
    public static final String HEXAA_HOST = ("192.168.203.183");
    public static final String HEXAA_SCHEME = ("http");

    public static class Api {

        // Attribute value (for organization)
        public static final String ATTRIBUTEVALUEORGANIZATIONS_ID = ("/app.php/api/attributevalueorganizations/{id}");
        public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_CONSENTS = ("/app.php/api/attributevalueorganizations/{id}/consents");
        public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID = ("/app.php/api/attributevalueorganizations/{id}/services/{sid}");
        public static final String ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATIONS_ASID = ("/app.php/api/organizations/{id}/attributevalueorganizations/{asid}");

        // Attribute value (for principal)
        public static final String ATTRIBUTEVALUEPRINCIPALS_ASID = ("/app.php/api/attributevalueprincipals/{asid}");
        public static final String ATTRIBUTEVALUEPRINCIPALS_ID = ("/app.php/api/attributevalueprincipals/{id}");
        public static final String ATTRIBUTEVALUEPRINCIPALS_ID_CONSENTS = ("/app.php/api/attributevalueprincipals/{id}/consents");
        public static final String ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID = ("/app.php/api/attributevalueprincipals/{id}/services/{sid}");

        // AttributeSpec
        public static final String ATTRIBUTESPECS = ("/app.php/api/attributespecs");
        public static final String ATTRIBUTESPECS_ID = ("/app.php/api/attributespecs/{id}");
        public static final String ATTRIBUTESPECS_ID_SERVICES = ("/app.php/api/attributespecs/{id}/services");

        // Entitlement
        public static final String ENTITLEMENTS_ID = ("/app.php/api/entitlements/{id}");

        // EntitlementPack
        public static final String ENTITLEMENTPACKS_PUBLIC = ("/app.php/api/entitlementpacks/public");
        public static final String ENTITLEMENTPACKS_ID = ("/app.php/api/entitlementpacks/{id}");
        public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS = ("/app.php/api/entitlementpacks/{id}/entitlements");
        public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID = ("/app.php/api/entitlementpacks/{id}/entitlements/{eid}");

        // Invitation
        public static final String INVITATIONS = ("/app.php/api/invitations");
        public static final String INVITATIONS_ID = ("/app.php/api/invitations/{id}");

        // Organization
        public static final String ORGANIZATIONS = ("/app.php/api/organizations");
        public static final String ORGANIZATIONS_ID = ("/app.php/api/organizations/{id}");
        public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS = ("/app.php/api/organizations/{id}/attributespecs");
        public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS = ("/app.php/api/organizations/{id}/attributespecs/{asid}/attributevalueorganizations");
        public static final String ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION = ("/app.php/api/organizations/{id}/attributevalueorganization");
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS = ("/app.php/api/organizations/{id}/entitlementpacks");
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID = ("/app.php/api/organizations/{id}/entitlementpacks/{epid}");
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT = ("/app.php/api/organizations/{id}/entitlementpacks/{epid}/accept");
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN = ("/app.php/api/organizations/{id}/entitlementpacks/{token}/token");
        public static final String ORGANIZATIONS_ID_ENTITLEMENTS = ("/app.php/api/organizations/{id}/entitlements");
        public static final String ORGANIZATIONS_ID_MANAGERS = ("/app.php/api/organizations/{id}/managers");
        public static final String ORGANIZATIONS_ID_MANAGERS_PID = ("/app.php/api/organizations/{id}/managers/{pid}");
        public static final String ORGANIZATIONS_ID_MEMBERS = ("/app.php/api/organizations/{id}/members");
        public static final String ORGANIZATIONS_ID_MEMBERS_PID = ("/app.php/api/organizations/{id}/members/{pid}");
        public static final String ORGANIZATIONS_ID_ROLES = ("/app.php/api/organizations/{id}/roles");

        public static final String ATTRIBUTES = ("/app.php/api/attributes");
        public static final String ENTITYIDS = ("/app.php/api/entityids");
        public static final String TOKENS = ("/app.php/api/tokens");

        // Principals
        public static final String MANAGER_ORGANIZATIONS = ("/app.php/api/manager/organizations");
        public static final String MANAGER_SERVICES = ("/app.php/api/manager/services");
        public static final String MEMBER_ORGANIZATIONS = ("/app.php/api/member/organizations");
        public static final String PRINCIPAL = ("/app.php/api/principal/self");
        public static final String PRINCIPAL_ATTRIBUTESPECS = ("/app.php/api/principal/attributespecs");
        public static final String PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL = ("/app.php/api/principal/attributevalueprincipal");
        public static final String PRINCIPAL_EMAILINVITATIONS = ("/app.php/api/principal/emailinvitations");
        public static final String PRINCIPAL_ENTITLEMENTS = ("/app.php/api/principal/entitlements");
        public static final String PRINCIPAL_ROLES = ("/app.php/api/principal/roles");
        public static final String PRINCIPAL_URLINVITATIONS = ("/app.php/api/principal/urlinvitations");
        public static final String PRINCIPALS = ("/app.php/api/principals");
        public static final String PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS = ("/app.php/api/principals/{asid}/attributespecs/attributevalueprincipals");
        public static final String PRINCIPALS_FEDID = ("/app.php/api/principals/{fedid}/fedid");
        public static final String PRINCIPALS_ID = ("/app.php/api/principals/{id}/id");

        // Roles
        public static final String ROLES_ID = ("/app.php/api/roles/{id}");
        public static final String ROLES_ID_ENTITLEMENTS = ("/app.php/api/roles/{id}/entitlements");
        public static final String ROLES_ID_ENTITLEMENTS_EID = ("/app.php/api/roles/{id}/entitlements/{eid}");
        public static final String ROLES_ID_PRINCIPALS = ("/app.php/api/roles/{id}/principals");
        public static final String ROLES_ID_PRINCIPALS_PID = ("/app.php/api/roles/{id}/principals/{pid}");

        // Services
        public static final String SERVICES = ("/app.php/api/services");
        public static final String SERVICES_ID = ("/app.php/api/services/{id}");
        public static final String SERVICES_ID_ATTRIBUTESPECS = ("/app.php/api/services/{id}/attributespecs");
        public static final String SERVICES_ID_ATTRIBUTESPECS_ASID = ("/app.php/api/services/{id}/attributespecs/{asid}");
        public static final String SERVICES_ID_ENTITLEMENTPACKS = ("/app.php/api/services/{id}/entitlementpacks");
        public static final String SERVICES_ID_ENTITLEMENTS = ("/app.php/api/services/{id}/entitlements");
        public static final String SERVICES_ID_MANAGERS = ("/app.php/api/services/{id}/managers");
        public static final String SERVICES_ID_MANAGERS_PID = ("/app.php/api/services/{id}/managers/{pid}");
        public static final String SERVICES_ID_ORGANIZATIONS = ("/app.php/api/services/{id}/organizations");

    }

    public void setAuth(String string) {
        HEXAA_AUTH = string;
    }
}
