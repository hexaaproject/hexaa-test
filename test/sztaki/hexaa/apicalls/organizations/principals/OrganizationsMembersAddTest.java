package sztaki.hexaa.apicalls.organizations.principals;

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
 * Tests the PUT method on the /api/organizations/{id}/members/{pid} and
 * /api/organizations/{id}/member calls.
 */
public class OrganizationsMembersAddTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationsMembersAddTest.class.getSimpleName() + " ***");
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
				.organization("OrganizationsMembersAddTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"OrganizationsMembersAddTest_org1\"); did not succeed");
		}
		principals = Utility.Create.principal(new String[] {
				"OrganizationsMembersAddTest_pri1",
				"OrganizationsMembersAddTest_pri2" });
		if (principals.length() < 2) {
			fail("Utility.Create.principal(new String[] {\"OrganizationsMembersAddTest_pri1\", \"OrganizationsMembersAddTest_pri2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationsMembersAddTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.persistent.setAdmin();
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Tests the PUT method to link the principal to the organization as a
	 * member.
	 */
	@Test
	public void testOrganizationMemberAdd() {
		Utility.Link.memberToOrganization(organizations.getJSONObject(0).getInt("id"), principals.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the PUT method on the /api/organizations/{id}/member call to link a
	 * principal given by an array.
	 */
	@Test
	public void testOrganizationMemberSet() {
		Utility.Link.memberToOrganizationSet(organizations.getJSONObject(0).getInt("id"), new int[] { principals.getJSONObject(1).getInt("id") });

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
