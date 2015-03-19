package sztaki.hexaa.apicalls;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;

/**
 * Tests all the possible uri-s for the not existing methods to check that there
 * are no vulnerability in the system from methods that should not exist.
 */
public class MethodNotAllowedTest extends NormalTest {

	/* *** REST(GET,POST,PUT,DELETE) boundles for the *** */
	/* easier use, not complete, feel free to add to it *** */
	BasicCall.REST[] restGetPostDeletePatch = { BasicCall.REST.GET,
			BasicCall.REST.POST, BasicCall.REST.DELETE, BasicCall.REST.PATCH, };
	BasicCall.REST[] restGetPutDeletePatch = { BasicCall.REST.GET,
			BasicCall.REST.PUT, BasicCall.REST.DELETE, BasicCall.REST.PATCH, };
	BasicCall.REST[] restGetPostPutPatch = { BasicCall.REST.GET,
			BasicCall.REST.POST, BasicCall.REST.PUT, BasicCall.REST.PATCH, };
	BasicCall.REST[] restGetPostPatch = { BasicCall.REST.GET,
			BasicCall.REST.POST, BasicCall.REST.PATCH, };
	BasicCall.REST[] restPostPatch = { BasicCall.REST.POST,
			BasicCall.REST.PATCH, };
	BasicCall.REST[] restPost = { BasicCall.REST.POST, };
	BasicCall.REST[] restPostPutDeletePatch = { BasicCall.REST.POST,
			BasicCall.REST.PUT, BasicCall.REST.DELETE, BasicCall.REST.PATCH, };
	BasicCall.REST[] restPostPutPatch = { BasicCall.REST.POST,
			BasicCall.REST.PUT, BasicCall.REST.PATCH, };
	BasicCall.REST[] restPutDeletePatch = { BasicCall.REST.PUT,
			BasicCall.REST.DELETE, BasicCall.REST.PATCH, };
	BasicCall.REST[] restDelete = { BasicCall.REST.DELETE, };
	BasicCall.REST[] restPostDelete = { BasicCall.REST.POST,
			BasicCall.REST.DELETE, };

	/**
	 * Bunch of tests to verify that the Method Not Allowed exception drop works
	 * fine, and not allowed methods do nut return any information that they
	 * should not.
	 */
	@Test
	public void testMethodNotAllowedAttributevalueorganization() {

		/* *** Attributevalueorganization *** */
		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID, },
				restPost);

		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID, },
				restPostPatch);

		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTEVALUEORGANIZATIONS, },
				restGetPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedAttributevalueprincipal() {
		/* *** Attributevalueprincipal *** */
		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTEVALUEPRINCIPALS, },
				restGetPutDeletePatch);

		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID, },
				restPost);

		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID, },
				restPostPatch);
	}

	@Test
	public void testMethodNotAllowedAttributespec() {
		/* *** Attributespec *** */
		this.expectingNotAllowed(new String[] { Const.Api.ATTRIBUTESPECS, },
				restPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.ATTRIBUTESPECS_ID, },
				restPost);

		this.expectingNotAllowed(
				new String[] { Const.Api.ATTRIBUTESPECS_ID_SERVICES, },
				restPostPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedConsents() {
		/* *** Consents *** */
		this.expectingNotAllowed(new String[] { Const.Api.CONSENTS, },
				restPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.CONSENTS_ID, },
				restPostDelete);

		this.expectingNotAllowed(
				new String[] { Const.Api.CONSENTS_SID_SERVICE, },
				restPostPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedEntitlement() {
		/* *** Entitlement *** */
		// SERVICES_ID_ENTITLEMENTS is called with the other services related
		// calls.
		this.expectingNotAllowed(new String[] { Const.Api.ENTITLEMENTS_ID, },
				restPost);
	}

	@Test
	public void testMethodNotAllowedEntitlementpack() {
		/* *** Entitlementpack *** */
		// SERVICES_ID_ENTITLEMENTPACKS is tested with the other Services
		// related calls.
		this.expectingNotAllowed(
				new String[] {
				// ENTITLEMENTPACKS_PUBLIC is not working 100% properly, as
				// the Symphony treats the /public part as an id if its
				// called differently than GET
				//
				// Const.Api.ENTITLEMENTPACKS_PUBLIC,
				Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS, },
				restPostPutDeletePatch);

		this.expectingNotAllowed(
				new String[] { Const.Api.ENTITLEMENTPACKS_ID, }, restPost);

		this.expectingNotAllowed(
				new String[] { Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENT, },
				restGetPostDeletePatch);

		this.expectingNotAllowed(
				new String[] { Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID, },
				restGetPostPatch);

		this.expectingNotAllowed(
				new String[] { Const.Api.ENTITLEMENTPACKS_ID_TOKEN, },
				restPostPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedInvitation() {
		/* *** Invitation *** */
		this.expectingNotAllowed(new String[] { Const.Api.INVITATIONS, },
				restGetPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.INVITATIONS_ID, },
				restPost);

		this.expectingNotAllowed(new String[] {
				Const.Api.INVITATIONS_ID_RESEND,
				Const.Api.INVITATIONS_TOKEN_ACCEPT_TOKEN,
				Const.Api.INVITATIONS_TOKEN_ACCEPTS_EMAIL_EMAIL,
				Const.Api.INVITATIONS_TOKEN_REJECTS_EMAIL_EMAIL, },
				restPostPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedNews() {
		/* *** News *** */
		this.expectingNotAllowed(new String[] {
				Const.Api.ORGANIZATIONS_ID_NEWS, Const.Api.PRINCIPAL_NEWS,
				Const.Api.PRINCIPALS_PID_NEWS, Const.Api.SERVICES_ID_NEWS, },
				restPostPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedOrganization() {
		/* *** Organization *** */
		this.expectingNotAllowed(new String[] { Const.Api.ORGANIZATIONS,
				Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
				Const.Api.ORGANIZATIONS_ID_ROLES, }, restPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.ORGANIZATIONS_ID, },
				restPost);

		this.expectingNotAllowed(
				new String[] {
						Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
						Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS,
						Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
						Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
						Const.Api.ORGANIZATIONS_ID_INVITATIONS,
						Const.Api.ORGANIZATIONS_ID_MANAGERS,
						Const.Api.ORGANIZATIONS_ID_MEMBERS, },
				restPostPutDeletePatch);

		this.expectingNotAllowed(new String[] {
				Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
				Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
				Const.Api.ORGANIZATIONS_ID_MEMBERS_PID, }, restGetPostPatch);

		this.expectingNotAllowed(new String[] {
				Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
				Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN, },
				restGetPostDeletePatch);
	}

	@Test
	public void testMethodNotAllowedOther() {
		/* *** Other *** */
		this.expectingNotAllowed(new String[] { Const.Api.ATTRIBUTES,
				Const.Api.TOKENS, }, restGetPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.ENTITYIDS,
				Const.Api.PROPERTIES, }, restPostPutDeletePatch);
	}

	@Test
	public void testMethodNotAllowedPrinciapl() {
		/* *** Princiapl *** */
		this.expectingNotAllowed(
				new String[] {
						Const.Api.MANAGER_ORGANIZATIONS,
						Const.Api.MANAGER_SERVICES,
						Const.Api.MEMBER_ORGANIZATIONS,
						Const.Api.PRINCIPAL_ATTRIBUTESPECS,
						Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
						Const.Api.PRINCIPAL_ENTITLEMENTS,
						Const.Api.PRINCIPAL_INVITATIONS,
						Const.Api.PRINCIPAL_ISADMIN,
						Const.Api.PRINCIPAL_ROLES,
						Const.Api.PRINCIPAL_SELF,
						Const.Api.PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS, },
				restPostPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.PRINCIPALS, },
				restDelete);

		this.expectingNotAllowed(new String[] { Const.Api.PRINCIPALS_FEDID,
				Const.Api.PRINCIPALS_ID_ID, }, restPostPutPatch);

		this.expectingNotAllowed(new String[] { Const.Api.PRINCIPAL, },
				restGetPostPutPatch);
	}

	@Test
	public void testMethodNotAllowedRoles() {
		/* *** Roles *** */
		this.expectingNotAllowed(new String[] { Const.Api.ROLES_ID, }, restPost);

		this.expectingNotAllowed(
				new String[] { Const.Api.ROLES_ID_ENTITLEMENTS,
						Const.Api.ROLES_ID_PRINCIPALS, },
				restPostPutDeletePatch);

		this.expectingNotAllowed(new String[] {
				Const.Api.ROLES_ID_ENTITLEMENTS_EID,
				Const.Api.ROLES_ID_PRINCIPALS_PID, }, restGetPostPatch);
	}

	@Test
	public void testMethodNotAllowedSecuritydomain() {
		/* *** Securitydomain *** */
		this.expectingNotAllowed(new String[] { Const.Api.SECURITYDOMAINS },
				restPutDeletePatch);
		this.expectingNotAllowed(new String[] { Const.Api.SECURITYDOMAINS_ID },
				restPost);
	}

	@Test
	public void testMethodNotAllowedServices() {
		/* *** Services *** */
		this.expectingNotAllowed(new String[] { Const.Api.SERVICES,
				Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
				Const.Api.SERVICES_ID_ENTITLEMENTS, }, restPutDeletePatch);

		this.expectingNotAllowed(new String[] { Const.Api.SERVICES_ID, },
				restPost);

		this.expectingNotAllowed(new String[] {
				Const.Api.SERVICES_ID_ATTRIBUTESPECS,
				Const.Api.SERVICES_ID_INVITATIONS,
				Const.Api.SERVICES_ID_MANAGERS,
				Const.Api.SERVICES_ID_ORGANIZATIONS, }, restPostPutDeletePatch);

		this.expectingNotAllowed(new String[] {
				Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
				Const.Api.SERVICES_ID_MANAGERS_PID, }, restGetPostPatch);

	}

	/**
	 * Easy to use testing bench, running all possible uri/call pair from the
	 * parameter arrays. All the http calls and asserts are in one method, makes
	 * it easier to maintain.
	 *
	 * @param uris
	 *            String[]: Strings for the uri-s to call, preferably from the
	 *            Const.Api constants.
	 * @param calls
	 *            REST[]: from the BasicCall.REST[], can be GET, POST, PUT,
	 *            DELETE.
	 */
	public void expectingNotAllowed(String[] uris, BasicCall.REST[] calls) {
		for (String uri : uris) {
			for (BasicCall.REST method : calls) {
				BasicCall notAllwedCall = new BasicCall();
				notAllwedCall.call(uri, method);
				try {
					assertEquals(Const.StatusLine.MethodNotAllowed,
							notAllwedCall.getStatusLine());
				} catch (AssertionError e) {
					AssertErrorHandler(e);
				}
			}
		}
	}
}
