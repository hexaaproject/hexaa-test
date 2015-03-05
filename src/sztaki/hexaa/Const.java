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
	 * Integer, containing the numerical id of the current principal_self.
	 * Updated in the Authenticator().authenticate().
	 */
	public static int HEXAA_ID = 0;
	/**
	 * The master_secret that match the servers master_secret for
	 * authentication. Default exist, load from config.properties.
	 */
	public static String MASTER_SECRET = ("7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv");
	/**
	 * The master_secret that match the servers master_secret for
	 * authentication. Default exist, load from config.properties.
	 */
	public static String ALTERNATIVE_SECRET = ("evf76cehsive5ixfaqwjdifbirezblzmg2kjmirc");

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
		public static final String ATTRIBUTEVALUEORGANIZATIONS = ("/api/attributevalueorganizations.{_format}");
		/**
		 * GET, PATCH, PUT(edit), DELETE attribute value for organizations.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS_ID = ("/api/attributevalueorganizations/{id}.{_format}");
		/**
		 * GET all services linked to this attributevalue.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES = ("/api/attributevalueorganizations/{id}/services.{_format}");
		/**
		 * GET,PUT(set),DELETE attribute value consent per service.
		 */
		public static final String ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID = ("/api/attributevalueorganizations/{id}/services/{sid}.{_format}");

		/* *** Attribute value (for principal) *** */
		/**
		 * POST(create) attribute value for principal.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS = ("/api/attributevalueprincipals.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE attribute value for principals.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS_ID = ("/api/attributevalueprincipals/{id}.{_format}");
		/**
		 * GET,PUT(set),DELETE attribute value consent per service.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES = ("/api/attributevalueprincipals/{id}/services.{_format}");
		/**
		 * GET,PUT(set),DELETE attribute value consent per service.
		 */
		public static final String ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID = ("/api/attributevalueprincipals/{id}/services/{sid}.{_format}");

		/* *** AttributeSpec *** */
		/**
		 * GET, POST(create) attribute specifications.
		 */
		public static final String ATTRIBUTESPECS = ("/api/attributespecs.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE attribute specification with the
		 * id.
		 */
		public static final String ATTRIBUTESPECS_ID = ("/api/attributespecs/{id}.{_format}");
		/**
		 * GET connected services to the attribute specification with the id.
		 */
		public static final String ATTRIBUTESPECS_ID_SERVICES = ("/api/attributespecs/{id}/services.{_format}");

		/* *** Consent *** */
		/**
		 * GET, POST(create) consent.
		 */
		public static final String CONSENTS = ("/api/consents.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE consent.
		 */
		public static final String CONSENTS_ID = ("/api/consents/{id}.{_format}");
		/**
		 * GET consent for current user for specific service.
		 */
		public static final String CONSENTS_SID_SERVICE = ("/api/consents/{sid}/service.{_format}");

		/* *** Entitlement *** */
		/**
		 * GET, PATCH, PUT(edit), DELETE entitlement.
		 */
		public static final String ENTITLEMENTS_ID = ("/api/entitlements/{id}.{_format}");

		/* *** EntitlementPack *** */
		/**
		 * GET all public entitlementpacks.
		 */
		public static final String ENTITLEMENTPACKS_PUBLIC = ("/api/entitlementpacks/public.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID = ("/api/entitlementpacks/{id}.{_format}");
		/**
		 * PUT array of entitlements to entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID_ENTITLEMENT = ("/api/entitlementpacks/{id}/entitlement.{_format}");
		/**
		 * GET entitlements of entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS = ("/api/entitlementpacks/{id}/entitlements.{_format}");
		/**
		 * PUT(add),DELETE(remove) entitlement to/from entitlementpack.
		 */
		public static final String ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID = ("/api/entitlementpacks/{id}/entitlements/{eid}.{_format}");
		/**
		 * GET(generate) new entitlementpack token.
		 */
		public static final String ENTITLEMENTPACKS_ID_TOKEN = ("/api/entitlementpacks/{id}/token.{_format}");

		/* *** Invitation *** */
		/**
		 * POST(send) new invitation.
		 */
		public static final String INVITATIONS = ("/api/invitations.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE invitation.
		 */
		public static final String INVITATIONS_ID = ("/api/invitations/{id}.{_format}");
		/**
		 * GET(resend) invitation.
		 */
		public static final String INVITATIONS_ID_RESEND = ("/api/invitations/{id}/resend.{_format}");
		/**
		 * GET(accept) invitation with only token.
		 */
		public static final String INVITATIONS_TOKEN_ACCEPT_TOKEN = ("/api/invitations/{token}/accept/token.{_format}");
		/**
		 * GET(accept) invitation with email address.
		 */
		public static final String INVITATIONS_TOKEN_ACCEPTS_EMAIL_EMAIL = ("/api/invitations/{token}/accepts/{email}/email.{_format}");
		/**
		 * GET(reject) invitation with email address.
		 */
		public static final String INVITATIONS_TOKEN_REJECTS_EMAIL_EMAIL = ("/api/invitations/{token}/rejects/{email}/email.{_format}");

		/* *** News *** */
		/**
		 * GET news for current user.
		 */
		public static final String ORGANIZATIONS_ID_NEWS = ("/api/organizations/{id}/news.{_format}");
		/**
		 * GET news for specified organization.
		 */
		public static final String PRINCIPAL_NEWS = ("/api/principal/news.{_format}");
		/**
		 * GET news for specified user.
		 */
		public static final String PRINCIPALS_PID_NEWS = ("/api/principals/{pid}/news.{_format}");
		/**
		 * GET news for specified service.
		 */
		public static final String SERVICES_ID_NEWS = ("/api/services/{id}/news.{_format}");

		/* *** Organization *** */
		/**
		 * GET, POST(create) organization.
		 */
		public static final String ORGANIZATIONS = ("/api/organizations.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE organization.
		 */
		public static final String ORGANIZATIONS_ID = ("/api/organizations/{id}.{_format}");
		/**
		 * GET available attribute specifications.
		 */
		public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS = ("/api/organizations/{id}/attributespecs.{_format}");
		/**
		 * GET all attribute values of an attribute specification for an
		 * organization.
		 */
		public static final String ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS = ("/api/organizations/{id}/attributespecs/{asid}/attributevalueorganizations.{_format}");
		/**
		 * GET all attribute values of the organization.
		 */
		public static final String ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION = ("/api/organizations/{id}/attributevalueorganization.{_format}");
		/**
		 * PUT(set) entitlement packs of organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACK = ("/api/organizations/{id}/entitlementpack.{_format}");
		/**
		 * GET entitlement packs of organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS = ("/api/organizations/{id}/entitlementpacks.{_format}");
		/**
		 * PUT(request link) ,DELETE entitlement pack to/from organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID = ("/api/organizations/{id}/entitlementpacks/{epid}.{_format}");
		/**
		 * PUT(accept link) entitlement pack to an organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT = ("/api/organizations/{id}/entitlementpacks/{epid}/accept.{_format}");
		/**
		 * PUT(link) entitlement pack to organization by token.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN = ("/api/organizations/{id}/entitlementpacks/{token}/token.{_format}");
		/**
		 * GET entitlements of organization.
		 */
		public static final String ORGANIZATIONS_ID_ENTITLEMENTS = ("/api/organizations/{id}/entitlements.{_format}");
		/**
		 * GET all pending and rejected invitations of the specified
		 * organization.
		 */
		public static final String ORGANIZATIONS_ID_INVITATIONS = ("/api/organizations/{id}/invitations.{_format}");
		/**
		 * GET number of managers of organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGER_COUNT = ("/api/organizations/{id}/manager/count.{_format}");
		/**
		 * PUT(set) managers of organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGER = ("/api/organizations/{id}/manager.{_format}");
		/**
		 * GET managers of organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGERS = ("/api/organizations/{id}/managers.{_format}");
		/**
		 * PUT(add), DELETE(remove) manager to/from organization.
		 */
		public static final String ORGANIZATIONS_ID_MANAGERS_PID = ("/api/organizations/{id}/managers/{pid}.{_format}");
		/**
		 * GET number of members of organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBER_COUNT = ("/api/organizations/{id}/member/count.{_format}");
		/**
		 * PUT(set) members of organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBER = ("/api/organizations/{id}/member.{_format}");
		/**
		 * GET members of organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBERS = ("/api/organizations/{id}/members.{_format}");
		/**
		 * PUT(add), DELETE(remove) member to/from organization.
		 */
		public static final String ORGANIZATIONS_ID_MEMBERS_PID = ("/api/organizations/{id}/members/{pid}.{_format}");
		/**
		 * GET, POST(create) roles of/for organization.
		 */
		public static final String ORGANIZATIONS_ID_ROLES = ("/api/organizations/{id}/roles.{_format}");

		/* *** OTHER *** */
		/**
		 * POST(get) all attributes (including entitlements) for a principal per
		 * service.
		 */
		public static final String ATTRIBUTES = ("/api/attributes.{_format}");
		/**
		 * POST(get) a token for the API (master-secret authentication).
		 */
		public static final String TOKENS = ("/api/tokens.{_format}");
		/**
		 * GET the properties of the HEXAA backend.
		 */
		public static final String PROPERTIES = ("/api/properties.{_format}");
		/**
		 * GET(list) service entityIDs.
		 */
		public static final String ENTITYIDS = ("/api/entityids.{_format}");
		/**
		 * GET(list) all scoped key names.
		 */
		public static final String SCOPEDKEYS = ("/api/scopedkeys.{_format}");
		/**
		 * GET(list) all tags.
		 */
		public static final String TAGS = ("/api/tags.{_format}");

		/* *** Principals *** */
		/**
		 * GET all organizations where the user is a manager.
		 */
		public static final String MANAGER_ORGANIZATIONS = ("/api/manager/organizations.{_format}");
		/**
		 * GET all services where the user is a manager.
		 */
		public static final String MANAGER_SERVICES = ("/api/manager/services.{_format}");
		/**
		 * GET all organizations where the user is a member.
		 */
		public static final String MEMBER_ORGANIZATIONS = ("/api/member/organizations.{_format}");
		/**
		 * GET available attribute specifications.
		 */
		public static final String PRINCIPAL_ATTRIBUTESPECS = ("/api/principal/attributespecs.{_format}");
		/**
		 * GET all attribute values of the principal.
		 */
		public static final String PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL = ("/api/principal/attributevalueprincipal.{_format}");
		/**
		 * GET all entitlements of the user.
		 */
		public static final String PRINCIPAL_ENTITLEMENTS = ("/api/principal/entitlements.{_format}");
		/**
		 * GET all invitations of current principal.
		 */
		public static final String PRINCIPAL_INVITATIONS = ("/api/principal/invitations.{_format}");
		/**
		 * GET all invitations of current principal.
		 */
		public static final String PRINCIPAL_ISADMIN = ("/api/principal/isadmin.{_format}");
		/**
		 * GET all roles of the user.
		 */
		public static final String PRINCIPAL_ROLES = ("/api/principal/roles.{_format}");
		/**
		 * GET info about current principal.
		 */
		public static final String PRINCIPAL_SELF = ("/api/principal/self.{_format}");
		/**
		 * GET all related services to the current user.
		 */
		public static final String PRINCIPAL_SERVICES_RELATED = ("/api/principal/services/related.{_format}");
		/**
		 * GET, POST(create) principal(s).
		 */
		public static final String PRINCIPALS = ("/api/principals.{_format}");
		/**
		 * GET available attribute values per principal and attribute
		 * specification.
		 */
		public static final String PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS = ("/api/principals/{asid}/attributespecs/attributevalueprincipals.{_format}");
		/**
		 * GET, DELETE info about, delete a principal by fedid.
		 */
		public static final String PRINCIPALS_FEDID = ("/api/principals/{fedid}/fedid.{_format}");
		/**
		 * GET, DELETE info about, delete a principal by id.
		 */
		public static final String PRINCIPALS_ID_ID = ("/api/principals/{id}/id.{_format}");
		/**
		 * PATCH, PUT a principal by id.
		 */
		public static final String PRINCIPALS_ID = ("/api/principals/{id}.{_format}");
		/**
		 * DELETE current principal.
		 */
		public static final String PRINCIPAL = ("/api/principal.{_format}");

		/* *** Roles *** */
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE role.
		 */
		public static final String ROLES_ID = ("/api/roles/{id}.{_format}");
		/**
		 * PUT(set) principal to role.
		 */
		public static final String ROLES_ID_PRINCIPAL = ("/api/roles/{id}/principal.{_format}");
		/**
		 * PUT(set) entitlements to role.
		 */
		public static final String ROLES_ID_ENTITLEMENT = ("/api/roles/{id}/entitlement.{_format}");
		/**
		 * GET entitlements in role.
		 */
		public static final String ROLES_ID_ENTITLEMENTS = ("/api/roles/{id}/entitlements.{_format}");
		/**
		 * PUT(add), DELETE(remove) entitlement to/from role.
		 */
		public static final String ROLES_ID_ENTITLEMENTS_EID = ("/api/roles/{id}/entitlements/{eid}.{_format}");
		/**
		 * GET principals in role.
		 */
		public static final String ROLES_ID_PRINCIPALS = ("/api/roles/{id}/principals.{_format}");
		/**
		 * PUT(add), DELETE(remove) principal to/from role.
		 */
		public static final String ROLES_ID_PRINCIPALS_PID = ("/api/roles/{id}/principals/{pid}.{_format}");

		/* *** Securitydomain *** */
		/**
		 * GET, POST(create) security domains.
		 */
		public static final String SECURITYDOMAINS = ("/api/securitydomains.{_format}");
		/**
		 * GET, PUT(edit), PATCH(edit), DELETE security domains.
		 */
		public static final String SECURITYDOMAINS_ID = ("/api/securitydomains/{id}.{_format}");

		/* *** Services *** */
		/**
		 * GET services where user is a manager. POST(create) new service.
		 */
		public static final String SERVICES = ("/api/services.{_format}");
		/**
		 * GET services where user is a manager. POST(create) new service.
		 */
		public static final String SERVICES_TOKEN_ENABLE = ("/api/services/{token}/enable.{_format}");
		/**
		 * GET, PATCH(edit), PUT(edit), DELETE service.
		 */
		public static final String SERVICES_ID = ("/api/services/{id}.{_format}");
		/**
		 * PUT(link) attributespecs to service by array.
		 */
		public static final String SERVICES_ID_ATTRIBUTESPEC = ("/api/services/{id}/attributespec.{_format}");
		/**
		 * GET attribute specifications linked to the service.
		 */
		public static final String SERVICES_ID_ATTRIBUTESPECS = ("/api/services/{id}/attributespecs.{_format}");
		/**
		 * PUT(add), DELETE(remove) attribute specification to/from service.
		 */
		public static final String SERVICES_ID_ATTRIBUTESPECS_ASID = ("/api/services/{id}/attributespecs/{asid}.{_format}");
		/**
		 * GET entitlementpack -organization relations.
		 */
		public static final String SERVICES_ID_ENTITLEMENTPACKS_REQUESTS = ("/api/services/{id}/entitlementpack/requests.{_format}");
		/**
		 * GET entitlementpacks of the service.
		 */
		public static final String SERVICES_ID_ENTITLEMENTPACKS = ("/api/services/{id}/entitlementpacks.{_format}");
		/**
		 * GET, POST(create) entitlements of service.
		 */
		public static final String SERVICES_ID_ENTITLEMENTS = ("/api/services/{id}/entitlements.{_format}");
		/**
		 * GET all pending and rejected invitations of the service.
		 */
		public static final String SERVICES_ID_INVITATIONS = ("/api/services/{id}/invitations.{_format}");
		/**
		 * GET managers of the service.
		 */
		public static final String SERVICES_ID_MANAGER_COUNT = ("/api/services/{id}/manager/count.{_format}");
		/**
		 * GET managers of the service.
		 */
		public static final String SERVICES_ID_MANAGER = ("/api/services/{id}/manager.{_format}");
		/**
		 * GET managers of the service.
		 */
		public static final String SERVICES_ID_MANAGERS = ("/api/services/{id}/managers.{_format}");
		/**
		 * PUT(add), DELETE(remove) manager to/from service.
		 */
		public static final String SERVICES_ID_MANAGERS_PID = ("/api/services/{id}/managers/{pid}.{_format}");
		/**
		 * GET organizations linked to the service.
		 */
		public static final String SERVICES_ID_ORGANIZATIONS = ("/api/services/{id}/organizations.{_format}");

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
		 * HTTP/1.1 401 Unauthorized.
		 */
		public static String Unauthorized = "HTTP/1.1 401 Unauthorized";
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
		 * HTTP/1.1 409 Conflict.
		 */
		public static String Conflict = "HTTP/1.1 409 Conflict";
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
