package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
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
import sztaki.hexaa.apicalls.attributespecs.AttributespecsGetTest;

/**
 * Test the GET method on /api/principal/roles call.
 */
public class PrincipalsRoleGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsRoleGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created roles.
	 */
	private static JSONArray roles = new JSONArray();

	/**
	 * Creates an organization and a role and links the current principal to the
	 * organization and the role.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create
				.organization(new String[] { "PrincipalsRoleGetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"PrincipalsRoleGetTest_org1\" }); did not succeed");
		}

		roles = Utility.Create.role(
				new String[] { "PrincipalsRoleGetTest_role1" }, organizations
						.getJSONObject(0).getInt("id"));
		if (roles.length() < 1) {
			fail("Utility.Create.role(new String[] {\"PrincipalsRoleGetTest_role1\" }, organizations.getJSONObject(0).getInt(\"id\")); did not succeed");
		}

		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"), Const.HEXAA_ID);

		Utility.Link.principalToRole(roles.getJSONObject(0).getInt("id"),
				new int[] { Const.HEXAA_ID });
	}
	
	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributespecsGetTest.class.getSimpleName());
		for (int i = 0; i < roles.length(); i++) {
			Utility.Remove.roles(roles.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i)
					.getInt("id"));
		}
	}

	/**
	 * GET all the roles of the current principal.
	 */
	@Test
	public void testPrincipalsRolesGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ROLES, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(roles, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
