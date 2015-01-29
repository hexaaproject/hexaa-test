package sztaki.hexaa.apicalls.organizations.roles;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the GET method on the /api/organizations/{id}/roles call.
 */
public class OrganizationsRolesGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationsRolesGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created roles in it.
	 */
	public static JSONArray roles = new JSONArray();

	/**
	 * Creates one organization and one role for it.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("testOrg");
		roles = Utility.Create.role("testRole", 1);
	}

	/**
	 * GET the role of the organization.
	 */
	@Test
	public void testOrganizationsRolesGet() {
		JSONArray jsonResponse = (JSONArray) JSONParser.parseJSON(persistent
				.call(Const.Api.ORGANIZATIONS_ID_ROLES, BasicCall.REST.GET));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(roles, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

}
