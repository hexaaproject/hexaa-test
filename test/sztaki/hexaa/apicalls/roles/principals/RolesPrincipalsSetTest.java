package sztaki.hexaa.apicalls.roles.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 *
 */
public class RolesPrincipalsSetTest extends CleanTest {

	/**
	 * JSONArray to store the created principals.
	 */
	public static JSONArray principals = new JSONArray();

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ RolesPrincipalsSetTest.class.getSimpleName() + " ***");
	}

	/**
	 * Creates an organization, two role, a service and a principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization(new String[] { "testOrg1" });
		Utility.Create.role(new String[] { "testRole1", "testRole2" }, 1);
		Utility.Create.service(new String[] { "testService1" });
		principals = Utility.Create.principal(new String[] { "fedidTest1",
				"fedidTest2" });
		Utility.Link.memberToOrganization(1, new int[] { 2, 3 });
	}

	/**
	 * PUT the principal to a role as an array.
	 */
	@Test
	public void testRolesPrincipalsSet() {
		Utility.Link.principalToRoleByArray(1, new int[] { 2, 3 });

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		System.out.println(Utility.persistent.getResponse());

		JSONArray jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.ROLES_ID_PRINCIPALS, BasicCall.REST.GET, null, 1,
					1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(RolesPrincipalsSetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(principals.getJSONObject(0).getInt("id"), jsonResponse
					.getJSONObject(0).getInt("principal_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
