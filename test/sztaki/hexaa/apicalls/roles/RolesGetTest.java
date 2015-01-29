package sztaki.hexaa.apicalls.roles;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/organizations/{id}/role and /api/roles/{id}
 * calls.
 */
public class RolesGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + RolesGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created roles.
	 */
	public static JSONArray roles = new JSONArray();

	/**
	 * Creates one organization and one role for it.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization(new String[] { "testOrgForRole1" });
		roles = Utility.Create.role(new String[] { "testRole1" }, 1);
	}

	/**
	 * GET the role in two way.
	 */
	@Test
	public void testRolesGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.ORGANIZATIONS_ID_ROLES, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(RolesGetTest.class.getName()).log(Level.SEVERE,
					null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(roles, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonObjectResponse;
		try {
			jsonObjectResponse = persistent.getResponseJSONObject(
					Const.Api.ROLES_ID, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(RolesGetTest.class.getName()).log(Level.SEVERE,
					null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(roles.getJSONObject(0), jsonObjectResponse,
					JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
