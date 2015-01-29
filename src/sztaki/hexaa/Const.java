package sztaki.hexaa;

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
	// public static final String HEXAA_HOST = ("192.168.203.203");
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
	 * The prefix for the uri's that should be used.
	 */
	public static final String URI_PREFIX = ("urn:geant:server.org:hexaa:");

	/**
	 * The port number the ssh should use. Default 22, load from
	 * config.properties.
	 */
	public static int SSH_PORT = 22;

	/**
	 * Contains the existing URL-s of the server.
	 */
	public static class Api {

		// Attribute value (for organization)
		/**
		 * POST(create) attribute value for organizations.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS = ("/api/attributevalueorganizations");
		/**
		 * GET, PATCH, PUT(edit), DELETE attribute value for organizations.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS_ID = ("/api/attributevalueorganizations/{id}");
		/**
		 * GET all services linked to this attributevalue.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES = ("/api/attributevalueorganizations/{id}/services");
		/**
		 * GET,PUT(set),DELETE attribute value consent per service.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID = ("/api/attributevalueorganizations/{id}/services/{sid}");

		/* *** Attribute value (for principal) *** */
		/**
		 * POST(create) attribute value for principal.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS = ("/api/attributevalueprincipals");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE attribute value for principals.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS_ID = ("/api/attributevalueprincipals/{id}");
		/**
		 * GET,PUT(set),DELETE attribute value consent per service.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES = ("/api/attributevalueprincipals/{id}/services");
		/**
		 * GET,PUT(set),DELETE attribute value consent per service.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID = ("/api/attributevalueprincipals/{id}/services/{sid}");

		/* *** AttributeSpec *** */
		/**
		 * GET, POST(create) attribute specifications.
		 */
		public static final String ATTRIBUTESPECS = ("/api/attributespecs");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE attribute specification with the
		 * id.
		 */
		public static final String ATTRIBUTESPECS_ID = ("/api/attributespecs/{id}");
		/**
		 * GET connected services to the attribute specification with the id.
		 */
		public static final String ATTRIBUTESPECS_ID_SERVICES = ("/api/attributespecs/{id}/service");

		/* *** Consent *** */
		/**
		 * GET, POST(create) consent.
		 */
		public static final String CONSENTS = ("/api/consents");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE consent.
		 */
		public static final String CONSENTS_ID = ("/api/consents/{id}");
		/**
		 * GET consent for current user for specific service.
		 */
		public static final String CONSENTS_SID_SERVICE = ("/api/consents/{sid}/service");

		/* *** Entitlement *** */
		/**
		 * GET, PATCH, PUT(edit), DELETE entitlement.
		 */
		public static final String ENTITLEMENTS_ID = ("/api/entitlements/{id}");

		/* *** EntitlementPack *** */
		/**
		 * GET all public entitlementpacks.
		 */
		public static final String ENTITLEMENTPACKS_PUBLIC = ("/api/entitlementpacks/public");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID = ("/api/entitlementpacks/{id}");
		/**
		 * PUT array of entitlements to entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID_ENTITLEMENT = ("/api/entitlementpacks/{id}/entitlement");
		/**
		 * GET entitlements of entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS = ("/api/entitlementpacks/{id}/entitlements");
		/**
		 * PUT(add),DELETE(remove) entitlement to/from entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID = ("/api/entitlementpacks/{id}/entitlements/{eid}");
		/**
		 * GET(generate) new entitlementpack token.
		 */
		public static final String ENTITLEMENTPACKS_ID_TOKEN = ("/api/entitlementpacks/{id}/token");

		/* *** Invitation *** */
		/**
		 * POST(send) new invitation.
		 */
		public static final String INVITATIONS = ("/api/invitations");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE invitation.
		 */
		public static final String INVITATIONS_ID = ("/api/invitations/{id}");
		/**
		 * GET(resend) invitation.
		 */
		public static final String INVITATIONS_ID_RESEND = ("/api/invitations/{id}/resend");
		/**
		 * GET(accept) invitation with only token.
		 */
		public static final String INVITATIONS_TOKEN_ACCEPT_TOKEN = ("/api/invitations/{token}/accept/token");
		/**
		 * GET(accept) invitation with email address.
		 */
		public static final String INVITATIONS_TOKEN_ACCEPTS_EMAIL_EMAIL = ("/api/invitations/{token}/accepts/{email}/email");
		/**
		 * GET(reject) invitation with email address.
		 */
		public static final String INVITATIONS_TOKEN_REJECTS_EMAIL_EMAIL = ("/api/invitations/{token}/rejects/{email}/email");

		/* *** News *** */
		/**
		 * GET news for current user.
		 */
		public static final String ORGANIZATIONS_ID_NEWS = ("/api/organizations/{id}/news");
		/**
		 * GET news for specified organization.
		 */
		public static final String PRINCIPAL_NEWS = ("/api/principal/news");
		/**
		 * GET news for specified user.
		 */
		public static final String PRINCIPALS_PID_NEWS = ("/api/principals/{pid}/news");
		/**
		 * GET news for specified service.
		 */
		public static final String SERVICES_ID_NEWS = ("/api/services/{id}/news");

		/* *** Organization *** */
		/**
		 * GET, POST(create) organization.
		 */
		public static final String ORGANIZATIONS = ("/api/organizations");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE organization.
		 */
		public static final String ORGANIZATIONS_ID = ("/api/organizations/{id}");
		/**
		 * GET available attribute specifications.
		 */
		public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS = ("/api/organizations/{id}/attributespecs");
		/**
		 * GET all attribute values of an attribute specification for an
		 * organization.
		 */
		public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS = ("/api/organizations/{id}/attributespecs/{asid}/attributevalueorganizations");
		/**
		 * GET all attribute values of the organization.
		 */
		public static final String ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION = ("/api/organizations/{id}/attributevalueorganization");
		/**
		 * PUT(set) entitlement packs of organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACK = ("/api/organizations/{id}/entitlementpack");
		/**
		 * GET entitlement packs of organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS = ("/api/organizations/{id}/entitlementpacks");
		/**
		 * PUT(request link) ,DELETE entitlement pack to/from organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID = ("/api/organizations/{id}/entitlementpacks/{epid}");
		/**
		 * PUT(accept link) entitlement pack to an organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT = ("/api/organizations/{id}/entitlementpacks/{epid}/accept");
		/**
		 * PUT(link) entitlement pack to organization by token.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN = ("/api/organizations/{id}/entitlementpacks/{token}/token");
		/**
		 * GET entitlements of organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTS = ("/api/organizations/{id}/entitlements");
		/**
		 * GET all pending and rejected invitations of the specified
		 * organization.
		 */
		public static final String ORGANIZATIONS_ID_INVITATIONS = ("/api/organizations/{id}/invitations");
		/**
		 * GET number of managers of organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGER_COUNT = ("/api/organizations/{id}/manager/count");
		/**
		 * PUT managers to organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGER = ("/api/organizations/{id}/manager");
		/**
		 * GET managers of organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGERS = ("/api/organizations/{id}/managers");
		/**
		 * PUT(add), DELETE(remove) manager to/from organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGERS_PID = ("/api/organizations/{id}/managers/{pid}");
		/**
		 * GET number of members of organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBER_COUNT = ("/api/organizations/{id}/member/count");
		/**
		 * PUT members to organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBER = ("/api/organizations/{id}/member");
		/**
		 * GET members of organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBERS = ("/api/organizations/{id}/members");
		/**
		 * PUT(add), DELETE(remove) member to/from organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBERS_PID = ("/api/organizations/{id}/members/{pid}");
		/**
		 * GET, POST(create) roles of/for organization.
		 */
		public static final String ORGANIZATIONS_ID_ROLES = ("/api/organizations/{id}/roles");

		/* *** OTHER *** */
		/**
		 * POST(get) all attributes (including entitlements) for a principal per
		 * service.
		 */
		public static final String ATTRIBUTES = ("/api/attributes");
		/**
		 * POST(get) a token for the API (master-secret authentication).
		 */
		public static final String TOKENS = ("/api/tokens");
		/**
		 * GET the properties of the HEXAA backend.
		 */
		public static final String PROPERTIES = ("/api/properties");
		/**
		 * GET(list) service entityIDs.
		 */
		public static final String ENTITYIDS = ("/api/entityids");

		/* *** Principals *** */
		/**
		 * GET all organizations where the user is a manager.
		 */
		public static final String MANAGER_ORGANIZATIONS = ("/api/manager/organizations");
		/**
		 * GET all services where the user is a manager.
		 */
		public static final String MANAGER_SERVICES = ("/api/manager/services");
		/**
		 * GET all organizations where the user is a member.
		 */
		public static final String MEMBER_ORGANIZATIONS = ("/api/member/organizations");
		/**
		 * GET available attribute specifications.
		 */
		public static final String PRINCIPAL_ATTRIBUTESPECS = ("/api/principal/attributespecs");
		/**
		 * GET all attribute values of the principal.
		 */
		public static final String PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL = ("/api/principal/attributevalueprincipal");
		/**
		 * GET all entitlements of the user.
		 */
		public static final String PRINCIPAL_ENTITLEMENTS = ("/api/principal/entitlements");
		/**
		 * GET all invitations of current principal.
		 */
		public static final String PRINCIPAL_INVITATIONS = ("/api/principal/invitations");
		/**
		 * GET all invitations of current principal.
		 */
		public static final String PRINCIPAL_ISADMIN = ("/api/principal/isadmin");
		/**
		 * GET all roles of the user.
		 */
		public static final String PRINCIPAL_ROLES = ("/api/principal/roles");
		/**
		 * GET info about current principal.
		 */
		public static final String PRINCIPAL_SELF = ("/api/principal/self");
		/**
		 * GET all related services to the current user.
		 */
		public static final String PRINCIPAL_SERVICES_RELATED = ("/api/principal/services/related");
		/**
		 * GET, POST(create) principal(s).
		 */
		public static final String PRINCIPALS = ("/api/principals");
		/**
		 * GET available attribute values per principal and attribute
		 * specification.
		 */
		public static final String PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS = ("/api/principals/{asid}/attributespecs/attributevalueprincipals");
		/**
		 * GET, DELETE info about, delete a principal by fedid.
		 */
		public static final String PRINCIPALS_FEDID = ("/api/principals/{fedid}/fedid");
		/**
		 * GET, DELETE info about, delete a principal by id.
		 */
		public static final String PRINCIPALS_ID_ID = ("/api/principals/{id}/id");
		/**
		 * PATCH, PUT a principal by id.
		 */
		public static final String PRINCIPALS_ID = ("/api/principals/{id}");
		/**
		 * DELETE current principal.
		 */
		public static final String PRINCIPAL = ("/api/principal");

		/* *** Roles *** */
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE role.
		 */
		public static final String ROLES_ID = ("/api/roles/{id}");
		/**
		 * PUT(set) principal to role.
		 */
		public static final String ROLES_ID_PRINCIPAL = ("/api/roles/{id}/principal");
		/**
		 * PUT(set) entitlements to role.
		 */
		public static final String ROLES_ID_ENTITLEMENT = ("/api/roles/{id}/entitlement");
		/**
		 * GET entitlements in role.
		 */
		public static final String ROLES_ID_ENTITLEMENTS = ("/api/roles/{id}/entitlements");
		/**
		 * PUT(add), DELETE(remove) entitlement to/from role.
		 */
		public static final String ROLES_ID_ENTITLEMENTS_EID = ("/api/roles/{id}/entitlements/{eid}");
		/**
		 * GET principals in role.
		 */
		public static final String ROLES_ID_PRINCIPALS = ("/api/roles/{id}/principals");
		/**
		 * PUT(add), DELETE(remove) principal to/from role.
		 */
		public static final String ROLES_ID_PRINCIPALS_PID = ("/api/roles/{id}/principals/{pid}");

		/* *** Services *** */
		/**
		 * GET services where user is a manager. POST(create) new service.
		 */
		public static final String SERVICES = ("/api/services");
		/**
		 * GET services where user is a manager. POST(create) new service.
		 */
		public static final String SERVICES_TOKEN_ENABLE = ("/api/services/{token}/enable");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE service.
		 */
		public static final String SERVICES_ID = ("/api/services/{id}");
		/**
		 * PUT(link) attributespecs to service by array.
		 */
		public static final String SERVICES_ID_ATTRIBUTESPEC = ("/api/services/{id}/attributespec");
		/**
		 * GET attribute specifications linked to the service.
		 */
		public static final String SERVICES_ID_ATTRIBUTESPECS = ("/api/services/{id}/attributespecs");
		/**
		 * PUT(add), DELETE(remove) attribute specification to/from service.
		 */
		public static final String SERVICES_ID_ATTRIBUTESPECS_ASID = ("/api/services/{id}/attributespecs/{asid}");
		/**
		 * GET entitlementpack -organization relations.
		 */
		public static final String SERVICES_ID_ENTITLEMENTPACKS_REQUESTS = ("/api/services/{id}/entitlementpack/requests");
		/**
		 * GET entitlementpacks of the service.
		 */
		public static final String SERVICES_ID_ENTITLEMENTPACKS = ("/api/services/{id}/entitlementpacks");
		/**
		 * GET, POST(create) entitlements of service.
		 */
		public static final String SERVICES_ID_ENTITLEMENTS = ("/api/services/{id}/entitlements");
		/**
		 * GET all pending and rejected invitations of the service.
		 */
		public static final String SERVICES_ID_INVITATIONS = ("/api/services/{id}/invitations");
		/**
		 * GET managers of the service.
		 */
		public static final String SERVICES_ID_MANAGER_COUNT = ("/api/services/{id}/manager/count");
		/**
		 * GET managers of the service.
		 */
		public static final String SERVICES_ID_MANAGER = ("/api/services/{id}/manager");
		/**
		 * GET managers of the service.
		 */
		public static final String SERVICES_ID_MANAGERS = ("/api/services/{id}/managers");
		/**
		 * PUT(add), DELETE(remove) manager to/from service.
		 */
		public static final String SERVICES_ID_MANAGERS_PID = ("/api/services/{id}/managers/{pid}");
		/**
		 * GET organizations linked to the service.
		 */
		public static final String SERVICES_ID_ORGANIZATIONS = ("/api/services/{id}/organizations");

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
		 * HTTP/1.1 302 Found.
		 */
		public static String RedirectFound = "HTTP/1.1 302 Found";
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
		/**
		 * HTTP/1.1 401 Unauthorized.
		 */
		public static String Unauthorized = "HTTP/1.1 401 Unauthorized";
	}

	/**
	 * Setter for {@link HEXAA_AUTH}.
	 *
	 * @param string
	 *            string representation of the required hash code.
	 */
	public void setAuth(String string) {
		HEXAA_AUTH = string;
	}
}
