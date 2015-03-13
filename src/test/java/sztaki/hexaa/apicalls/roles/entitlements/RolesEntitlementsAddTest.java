package sztaki.hexaa.apicalls.roles.entitlements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

/**
 * Tests the PUT method on the /api/role/{id}/entitlements/{eid} call.
 */
public class RolesEntitlementsAddTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesEntitlementsAddTest.class.getSimpleName() + " ***");
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
				.organization(new String[] { "RolesEntitlementsAddTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"RolesEntitlementsAddTest_org1\" }); did not succeed");
		}
		// Utility.Link.memberToOrganization(1, 1);

		services = Utility.Create
				.service(new String[] { "RolesEntitlementsAddTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.services(new String[] {\"RolesEntitlementsAddTest_service1\" }, \"user\"); did not succeed");
		}

		roles = Utility.Create.role(
				new String[] { "RolesEntitlementsAddTest_role1" },
				organizations.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.roles(new String[] {\"RolesEntitlementsAddTest_role1\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}

		entitlements = Utility.Create.entitlements(services.getJSONObject(0)
				.getInt("id"),
				new String[] { "RolesEntitlementsAddTest_entitlements1" });
		if (entitlements.length() < 1) {
			fail("Utility.Create.entitlements(services.getJSONObject(0).getInt(\"id\"), new String[] {\"RolesEntitlementsAddTest_entitlements1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				new String[] { "RolesEntitlementsAddTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), new String[] {\"RolesEntitlementsAddTest_ep1\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ RolesEntitlementsAddTest.class.getSimpleName());
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
	 * PUT the entitlement to a role with three different outcome.
	 */
	@Test
	public void testRolesEntitlementsAdd() {
		// PUT an entitlement from outside of organization.
		Utility.Link.entitlementsToRole(roles.getJSONObject(0).getInt("id"),
				entitlements.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.BadRequest,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// PUT entitlement to pack and pack to organization.
		Utility.Link.entitlementToPack(
				entitlements.getJSONObject(0).getInt("id"), entitlementpacks
						.getJSONObject(0).getInt("id"));
		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] { entitlementpacks.getJSONObject(0)
				.getInt("id") });

		// PUT entitlement to role.
		Utility.Link.entitlementsToRole(roles.getJSONObject(0).getInt("id"),
				entitlements.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// PUT same entitlement to role again.
		Utility.Link.entitlementsToRole(roles.getJSONObject(0).getInt("id"),
				entitlements.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
