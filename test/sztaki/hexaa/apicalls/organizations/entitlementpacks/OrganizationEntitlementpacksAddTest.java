package sztaki.hexaa.apicalls.organizations.entitlementpacks;

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
 * Tests the PUT method on the /api/organizations/{id}/entitlementpacks/{epid},
 * /api/organizations/{id}/entitlementpacks/{epid}/accept and
 * /api/organizations/{id}/entitlementpack calls.
 */
public class OrganizationEntitlementpacksAddTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationEntitlementpacksAddTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates a service, an organization and entitlementpacks.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "OrganizationEntitlementpacksAddTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"OrganizationEntitlementpacksAddTest_service1\" }); did not succeed");
		}
		organizations = Utility.Create
				.organization(new String[] { "OrganizationEntitlementpacksAddTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"OrganizationEntitlementpacksAddTest_org1\"}); did not succeed");
		}
		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"), new String[] {
				"OrganizationEntitlementpacksAddTest_ep1",
				"OrganizationEntitlementpacksAddTest_ep2" });
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.attributespec(services.getJSONObject(0).getInt(\"id\"), new String[] {\"OrganizationEntitlementpacksAddTest_ep1\", \"OrganizationEntitlementpacksAddTest_ep2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationEntitlementpacksAddTest.class.getSimpleName());
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Tests the PUT method to add additional entitlementpacks to the
	 * organization one by one.
	 */
	@Test
	public void testOrganizationEntitlementpacksAdd() {
		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), entitlementpacks.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the PUT method to set the entitlementpacks of the organization.
	 */
	@Test
	public void testOrganizationEntitlementpacksSetByArray() {
		Utility.Link.entitlementpackToOrgByArray(organizations.getJSONObject(0)
				.getInt("id"), new int[] {
				entitlementpacks.getJSONObject(0).getInt("id"),
				entitlementpacks.getJSONObject(1).getInt("id") });

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
