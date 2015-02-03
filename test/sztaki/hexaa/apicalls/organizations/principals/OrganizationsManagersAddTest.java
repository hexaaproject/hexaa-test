package sztaki.hexaa.apicalls.organizations.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.apicalls.attributespecs.AttributespecsGetTest;

/**
 * Tests the PUT method on the /api/organizations/{id}/managers/{pid} and
 * /api/organizations/{id}/manager calls.
 */
public class OrganizationsManagersAddTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationsManagersAddTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates one organization and one principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization("OrganizationsManagersAddTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"OrganizationsManagersAddTest_org1\"); did not succeed");
		}
		principals = Utility.Create.principal(new String[] {
				"OrganizationsManagersAddTest_pri1",
				"OrganizationsManagersAddTest_pri2" });
		if (principals.length() < 2) {
			fail("Utility.Create.principal(new String[] {\"OrganizationsManagersAddTest_pri1\", \"OrganizationsManagersAddTest_pri2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributespecsGetTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.persistent.isAdmin = true;
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Tests the PUT method to link the principal to the organization as a
	 * manager.
	 */
	@Test
	public void testOrganizationManagersAdd() {
		Utility.Link.managerToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the PUT method on the /api/organizations/{id}/manager call to link
	 * a principal given by an array.
	 */
	@Test
	public void testOrganizationManagerSet() {
		Utility.Link.managerToOrganizationSet(organizations.getJSONObject(0)
				.getInt("id"),
				new int[] { principals.getJSONObject(0).getInt("id") });

		try {
			assertEquals(Const.StatusLine.BadRequest,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"),
				new int[] { principals.getJSONObject(0).getInt("id") });

		Utility.Link.managerToOrganizationSet(organizations.getJSONObject(0)
				.getInt("id"),
				new int[] { principals.getJSONObject(0).getInt("id") });

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
