package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/principal/services/related call.
 */
public class PrincipalServicesRelatedTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalServicesRelatedTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created roles.
	 */
	private static JSONArray roles = new JSONArray();
	/**
	 * JSONArray to store the created entitlements.
	 */
	private static JSONArray entitlements = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates one organization, two services, one role, one entitlement and one
	 * entitlementpack, and links them together.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "PrincipalServicesRelatedTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"PrincipalServicesRelatedTest_org1\" }); did not succeed");
		}
		// Utility.Link.memberToOrganization(1, 1);

		services = Utility.Create
				.service(new String[] { "PrincipalServicesRelatedTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.services(new String[] {\"PrincipalServicesRelatedTest_service1\" }, \"user\"); did not succeed");
		}

		roles = Utility.Create
				.role(new String[] { "PrincipalServicesRelatedTest_role1" },
						organizations.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.roles(new String[] {\"PrincipalServicesRelatedTest_role1\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}

		entitlements = Utility.Create
				.entitlements(
						services.getJSONObject(0).getInt("id"),
						new String[] { "PrincipalServicesRelatedTest_entitlements1" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"PrincipalServicesRelatedTest_entitlements1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				new String[] { "PrincipalServicesRelatedTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"PrincipalServicesRelatedTest_ep1\" }); did not succeed");
		}

		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] { entitlementpacks.getJSONObject(0)
				.getInt("id") });
		Utility.Link.entitlementsToRole(roles.getJSONObject(0).getInt("id"),
				new int[] { entitlements.getJSONObject(0).getInt("id") });

		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				new int[] { Const.HEXAA_ID });

	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalServicesRelatedTest.class.getSimpleName());
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < entitlements.length(); i++) {
			Utility.Remove.entitlement(entitlements.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * Checks the returned id-s.
	 */
	@Test
	public void testPrincipalServicesRelated() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_SERVICES_RELATED, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(services.length(), jsonResponse.length());
			for (int i = 0; i < services.length(); i++) {
				assertEquals(services.getJSONObject(i).get("id"), jsonResponse
						.getJSONObject(i).get("id"));
			}
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
