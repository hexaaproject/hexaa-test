package sztaki.hexaa.httputility;

/**
 * Contains constants for the different keys, URI-s, and other server specific
 * data.
 */
public class Const {

    /**
     * Flag to load the properties if false.
     */
    public static boolean PROPERTIES_LOADED = false;
    /**
     * The servers port number. Default 80, load from config.properties.
     */
    public static int HEXAA_PORT = 80;
    /**
     * The header required by the server application to authenticate.
     */
    public static final String HEXAA_HEADER = ("X-HEXAA-AUTH");
    /**
     * The api key required by the server to authenticate. The
     * Authenticator.authenticate() method updates it if necessary.
     */
    public static String HEXAA_AUTH = ("fb97b1edd685f39d3df497b9e207d9cf6c6dca55dc46cb89933c9f95f172d3b2");
    /**
     * The IP address or name of the server. Default localhost, load from
     * config.properties.
     */
    //public static final String HEXAA_HOST = ("192.168.203.203");
    public static String HEXAA_HOST = ("localhost");
    /**
     * The scheme required by the server.
     */
    public static final String HEXAA_SCHEME = ("http");
    /**
     * The fedid of the superadmin that we use for the tests. Default
     * tesztAdmin@sztaki.hu, load from config.properties.
     */
    public static String HEXAA_FEDID = ("tesztAdmin@sztaki.hu");
    /**
     * The master_secret that match the servers master_secret for
     * authentication. Default exist, load from config.properties.
     */
    public static String MASTER_SECRET = ("7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv");

    /**
     * The .properties file to load some of the Strings found in the Const. By
     * default its config.properties.
     */
    public static final String PROPERTIES = ("config.properties");

    /**
     * Contains the existing URL-s of the server.
     */
    public static class Api {

        // Attribute value (for organization)
        /**
         * POST(create) attribute value for organizations.
         */
        public static final String ATTRIBUTEVALUEORGANIZATIONS = ("/app.php/api/attributevalueorganizations");
        /**
         * GET, PATCH, PUT(edit), DELETE attribute value for organizations.
         */
        public static final String ATTRIBUTEVALUEORGANIZATIONS_ID = ("/app.php/api/attributevalueorganizations/{id}");
        /**
         * GET,PUT(set),DELETE attribute value consent per service.
         */
        public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID = ("/app.php/api/attributevalueorganizations/{id}/services/{sid}");

        /* *** Attribute value (for principal) *** */
        /**
         * POST(create) attribute value for principal.
         */
        public static final String ATTRIBUTEVALUEPRINCIPALS = ("/app.php/api/attributevalueprincipals");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE attribute value for principals.
         */
        public static final String ATTRIBUTEVALUEPRINCIPALS_ID = ("/app.php/api/attributevalueprincipals/{id}");
        /**
         * GET,PUT(set),DELETE attribute value consent per service.
         */
        public static final String ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID = ("/app.php/api/attributevalueprincipals/{id}/services/{sid}");

        /* *** AttributeSpec *** */
        /**
         * GET, POST(create) attribute specifications.
         */
        public static final String ATTRIBUTESPECS = ("/app.php/api/attributespecs");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE attribute specification with the
         * id.
         */
        public static final String ATTRIBUTESPECS_ID = ("/app.php/api/attributespecs/{id}");
        /**
         * GET connected services to the attribute specification with the id.
         */
        public static final String ATTRIBUTESPECS_ID_SERVICES = ("/app.php/api/attributespecs/{id}/service");

        /* *** Consent *** */
        /**
         * GET, POST(create) consent.
         */
        public static final String CONSENTS = ("/app.php/api/consents");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE consent.
         */
        public static final String CONSENTS_ID = ("/app.php/api/consents/{id}");
        /**
         * GET consent for current user for specific service.
         */
        public static final String CONSENTS_SID_SERVICE = ("/app.php/api/consents/{sid}/service");

        /* *** Entitlement *** */
        /**
         * GET, PATCH, PUT(edit), DELETE entitlement.
         */
        public static final String ENTITLEMENTS_ID = ("/app.php/api/entitlements/{id}");

        /* *** EntitlementPack *** */
        /**
         * GET all public entitlement packages.
         */
        public static final String ENTITLEMENTPACKS_PUBLIC = ("/app.php/api/entitlementpacks/public");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE entitlement pack.
         */
        public static final String ENTITLEMENTPACKS_ID = ("/app.php/api/entitlementpacks/{id}");
        /**
         * GET entitlements of entitlement pack.
         */
        public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS = ("/app.php/api/entitlementpacks/{id}/entitlements");
        /**
         * PUT(add),DELETE(remove) entitlement to/from entitlement pack.
         */
        public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID = ("/app.php/api/entitlementpacks/{id}/entitlements/{eid}");
        /**
         * GET(generate) new entitlement pack token.
         */
        public static final String ENTITLEMENTPACKS_ID_TOKEN = ("/app.php/api/entitlementpacks/{id}/token");

        /* *** EntityID *** */
        /**
         * GET, POST(create) entityid request(s).
         */
        public static final String ENTITYIDREQUESTS = ("/app.php/api/entityidrequests");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE entityid request.
         */
        public static final String ENTITYIDREQUESTS_ID = ("/app.php/api/entityidrequests/{id}");
        /**
         * GET(accept) entityid request.
         */
        public static final String ENTITYIDREQUESTS_ID_ACCEPT = ("/app.php/api/entityidrequests/{id}/accept");
        /**
         * GET(reject) entityid request.
         */
        public static final String ENTITYIDREQUESTS_ID_REJECT = ("/app.php/api/entityidrequests/{id}/reject");
        /**
         * GET(list) service entityIDs.
         */
        public static final String ENTITYIDS = ("/app.php/api/entityids");

        /* *** Invitation *** */
        /**
         * POST(send) new invitation.
         */
        public static final String INVITATIONS = ("/app.php/api/invitations");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE invitation.
         */
        public static final String INVITATIONS_ID = ("/app.php/api/invitations/{id}");
        /**
         * GET(resend) invitation.
         */
        public static final String INVITATIONS_ID_RESEND = ("/app.php/api/invitations/{id}/resend");
        /**
         * GET(accept) invitation with only token.
         */
        public static final String INVITATIONS_TOKEN_ACCEPT_TOKEN = ("/app.php/api/invitations/{token}/accept/token");
        /**
         * GET(accept) invitation with email address.
         */
        public static final String INVITATIONS_TOKEN_ACCEPTS_EMAIL_EMAIL = ("/app.php/api/invitations/{token}/accepts/{email}/email");
        /**
         * GET(reject) invitation with email address.
         */
        public static final String INVITATIONS_TOKEN_REJECTS_EMAIL_EMAIL = ("/app.php/api/invitations/{token}/rejects/{email}/email");

        /* *** News *** */
        /**
         * GET news for current user.
         */
        public static final String PRINCIPAL_PRINCIPAL_NEWS = ("/app.php/api/principal/principal/news");
        /**
         * GET news for specified organization.
         */
        public static final String PRINCIPALS_ID_ORGANIZATIONS_NEWS = ("/app.php/api/principals/{id}/organizations/news");
        /**
         * GET news for specified user.
         */
        public static final String PRINCIPALS_PID_PRINCIPALS_NEWS = ("/app.php/api/principals/{pid}/principals/news");
        /**
         * GET news for specified service.
         */
        public static final String PRINCIPALS_SID_SERVICES_NEWS = ("/app.php/api/principals/{sid}/services/news");

        /* *** Organization *** */
        /**
         * GET, POST(create) organization.
         */
        public static final String ORGANIZATIONS = ("/app.php/api/organizations");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE organization.
         */
        public static final String ORGANIZATIONS_ID = ("/app.php/api/organizations/{id}");
        /**
         * GET available attribute specifications.
         */
        public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS = ("/app.php/api/organizations/{id}/attributespecs");
        /**
         * GET all attribute values of an attribute specification for an
         * organization.
         */
        public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS = ("/app.php/api/organizations/{id}/attributespecs/{asid}/attributevalueorganizations");
        /**
         * GET all attribute values of the organization.
         */
        public static final String ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION = ("/app.php/api/organizations/{id}/attributevalueorganization");
        /**
         * GET entitlement packs of organization.
         */
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS = ("/app.php/api/organizations/{id}/entitlementpacks");
        /**
         * PUT(request link) ,DELETE entitlement pack to/from organization.
         */
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID = ("/app.php/api/organizations/{id}/entitlementpacks/{epid}");
        /**
         * PUT(accept link) entitlement pack to an organization.
         */
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT = ("/app.php/api/organizations/{id}/entitlementpacks/{epid}/accept");
        /**
         * PUT(link) entitlement pack to organization by token.
         */
        public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN = ("/app.php/api/organizations/{id}/entitlementpacks/{token}/token");
        /**
         * GET entitlements of organization.
         */
        public static final String ORGANIZATIONS_ID_ENTITLEMENTS = ("/app.php/api/organizations/{id}/entitlements");
        /**
         * GET all pending and rejected invitations of the specified
         * organization.
         */
        public static final String ORGANIZATIONS_ID_INVITATIONS = ("/app.php/api/organizations/{id}/invitations");
        /**
         * GET managers of organization.
         */
        public static final String ORGANIZATIONS_ID_MANAGERS = ("/app.php/api/organizations/{id}/managers");
        /**
         * PUT(add), DELETE(remove) manager to/from organization.
         */
        public static final String ORGANIZATIONS_ID_MANAGERS_PID = ("/app.php/api/organizations/{id}/managers/{pid}");
        /**
         * GET members of organization.
         */
        public static final String ORGANIZATIONS_ID_MEMBERS = ("/app.php/api/organizations/{id}/members");
        /**
         * PUT(add), DELETE(remove) member to/from organization.
         */
        public static final String ORGANIZATIONS_ID_MEMBERS_PID = ("/app.php/api/organizations/{id}/members/{pid}");
        /**
         * GET, POST(create) roles of/for organization.
         */
        public static final String ORGANIZATIONS_ID_ROLES = ("/app.php/api/organizations/{id}/roles");

        /* *** OTHER *** */
        /**
         * POST(get) all attributes (including entitlements) for a principal per
         * service.
         */
        public static final String ATTRIBUTES = ("/app.php/api/attributes");
        /**
         * POST(get) a token for the API (master-secret authentication).
         */
        public static final String TOKENS = ("/app.php/api/tokens");

        /* *** Principals *** */
        /**
         * GET all organizations where the user is a manager.
         */
        public static final String MANAGER_ORGANIZATIONS = ("/app.php/api/manager/organizations");
        /**
         * GET all services where the user is a manager.
         */
        public static final String MANAGER_SERVICES = ("/app.php/api/manager/services");
        /**
         * GET all organizations where the user is a member.
         */
        public static final String MEMBER_ORGANIZATIONS = ("/app.php/api/member/organizations");
        /**
         * GET available attribute specifications.
         */
        public static final String PRINCIPAL_ATTRIBUTESPECS = ("/app.php/api/principal/attributespecs");
        /**
         * GET all attribute values of the principal.
         */
        public static final String PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL = ("/app.php/api/principal/attributevalueprincipal");
        /**
         * GET all entitlements of the user.
         */
        public static final String PRINCIPAL_ENTITLEMENTS = ("/app.php/api/principal/entitlements");
        /**
         * GET all invitations of current principal.
         */
        public static final String PRINCIPAL_INVITATIONS = ("/app.php/api/principal/invitations");
        /**
         * GET all invitations of current principal.
         */
        public static final String PRINCIPAL_ISADMIN = ("/app.php/api/principal/isadmin");
        /**
         * GET all roles of the user.
         */
        public static final String PRINCIPAL_ROLES = ("/app.php/api/principal/roles");
        /**
         * GET info about current principal.
         */
        public static final String PRINCIPAL_SELF = ("/app.php/api/principal/self");
        /**
         * GET, PATCH(edit), PUT(edit), POST(create) principal(s).
         */
        public static final String PRINCIPALS = ("/app.php/api/principals");
        /**
         * GET available attribute values per principal and attribute
         * specification.
         */
        public static final String PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS = ("/app.php/api/principals/{asid}/attributespecs/attributevalueprincipals");
        /**
         * GET, DELETE info about, delete a principal by fedid.
         */
        public static final String PRINCIPALS_FEDID = ("/app.php/api/principals/{fedid}/fedid");
        /**
         * GET, DELETE info about, delete a principal by id.
         */
        public static final String PRINCIPALS_ID = ("/app.php/api/principals/{id}/id");
        /**
         * DELETE current principal.
         */
        public static final String PRINCIPAL = ("/app.php/api/principal");

        /* *** Roles *** */
        /**
         * GET, PATCH(edit), PUT(edit), DELETE role.
         */
        public static final String ROLES_ID = ("/app.php/api/roles/{id}");
        /**
         * PUT(set) principal.
         */
        public static final String ROLES_ID_PRINCIPAL = ("/app.php/api/roles/{id}");
        /**
         * GET entitlements in role.
         */
        public static final String ROLES_ID_ENTITLEMENTS = ("/app.php/api/roles/{id}/entitlements");
        /**
         * PUT(add), DELETE(remove) entitlement to/from role.
         */
        public static final String ROLES_ID_ENTITLEMENTS_EID = ("/app.php/api/roles/{id}/entitlements/{eid}");
        /**
         * GET principals in role.
         */
        public static final String ROLES_ID_PRINCIPALS = ("/app.php/api/roles/{id}/principals");
        /**
         * PUT(add), DELETE(remove) principal to/from role.
         */
        public static final String ROLES_ID_PRINCIPALS_PID = ("/app.php/api/roles/{id}/principals/{pid}");

        /* *** Services *** */
        /**
         * GET services where user is a manager. POST(create) new service.
         */
        public static final String SERVICES = ("/app.php/api/services");
        /**
         * GET, PATCH(edit), PUT(edit), DELETE service.
         */
        public static final String SERVICES_ID = ("/app.php/api/services/{id}");
        /**
         * GET attribute specifications linked to the service.
         */
        public static final String SERVICES_ID_ATTRIBUTESPECS = ("/app.php/api/services/{id}/attributespecs");
        /**
         * PUT(add), DELETE(remove) attribute specification to/from service.
         */
        public static final String SERVICES_ID_ATTRIBUTESPECS_ASID = ("/app.php/api/services/{id}/attributespecs/{asid}");
        /**
         * GET entitlementpacks of the service.
         */
        public static final String SERVICES_ID_ENTITLEMENTPACKS = ("/app.php/api/services/{id}/entitlementpacks");
        /**
         * GET, POST(create) entitlements of service.
         */
        public static final String SERVICES_ID_ENTITLEMENTS = ("/app.php/api/services/{id}/entitlements");
        /**
         * GET all pending and rejected invitations of the service.
         */
        public static final String SERVICES_ID_INVITATIONS = ("/app.php/api/services/{id}/invitations");
        /**
         * GET managers of the service.
         */
        public static final String SERVICES_ID_MANAGERS = ("/app.php/api/services/{id}/managers");
        /**
         * PUT(add), DELETE(remove) manager to/from service.
         */
        public static final String SERVICES_ID_MANAGERS_PID = ("/app.php/api/services/{id}/managers/{pid}");
        /**
         * GET organizations linked to the service.
         */
        public static final String SERVICES_ID_ORGANIZATIONS = ("/app.php/api/services/{id}/organizations");

    }

    /**
     * Contains the possible Status Lines the server can answer with.
     */
    public static class StatusLine {

        /**
         * HTTP/1.1 200 OK.
         */
        public static String OK = "HTTP/1.1 200 OK";
        /**
         * HTTP/1.1 201 Created.
         */
        public static String Created = "HTTP/1.1 201 Created";
        /**
         * HTTP/1.1 204 No Content.
         */
        public static String NoContent = "HTTP/1.1 204 No Content";
        /**
         * HTTP/1.1 400 Bad Request.
         */
        public static String BadRequest = "HTTP/1.1 400 Bad Request";
        /**
         * HTTP/1.1 403 Forbidden.
         */
        public static String Forbidden = "HTTP/1.1 403 Forbidden";
        /**
         * HTTP/1.1 404 Not Found.
         */
        public static String NotFound = "HTTP/1.1 404 Not Found";
        /**
         * HTTP/1.1 405 Method Not Allowed.
         */
        public static String MethodNotAllowed = "HTTP/1.1 405 Method Not Allowed";
    }

    /**
     * Setter for {@link HEXAA_AUTH}.
     *
     * @param string string representation of the required hash code.
     */
    public void setAuth(String string) {
        HEXAA_AUTH = string;
    }
}
