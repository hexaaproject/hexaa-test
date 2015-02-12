package sztaki.hexaa.apicalls.roles.entitlements;

import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/role/{id}/entitlements call.
 */
public class RolesEntitlementsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesEntitlementsGetTest.class.getSimpleName() + " ***");
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
	 * Creates an organization, two role, a service, an entitlement and an
	 * entitlementpack.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "RolesEntitlementsGetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"RolesEntitlementsGetTest_org1\" }); did not succeed");
		}
		// Utility.Link.memberToOrganization(1, 1);

		services = Utility.Create
				.service(new String[] { "RolesEntitlementsGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.services(new String[] {\"RolesEntitlementsGetTest_service1\" }, \"user\"); did not succeed");
		}

		roles = Utility.Create.role(
				new String[] { "RolesEntitlementsGetTest_role1" },
				organizations.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.roles(new String[] {\"RolesEntitlementsGetTest_role1\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}

		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"),
				new String[] { "RolesEntitlementsGetTest_entitlements1" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"RolesEntitlementsGetTest_entitlements1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				new String[] { "RolesEntitlementsGetTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"RolesEntitlementsGetTest_ep1\" }); did not succeed");
		}

		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] { entitlementpacks.getJSONObject(0)
				.getInt("id") });
		Utility.Link.entitlementsToRole(roles.getJSONObject(0).getInt("id"),
				entitlements.getJSONObject(0).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesEntitlementsGetTest.class.getSimpleName());
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
	 * GET the entitlements of the role.
	 */
	@Test
	public void testRolesEntitlementsGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ROLES_ID_ENTITLEMENTS, BasicCall.REST.GET, null,
					roles.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			JSONAssert.assertEquals(entitlements.getJSONObject(0),
					jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
			JSONAssert.assertEquals(entitlements, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
