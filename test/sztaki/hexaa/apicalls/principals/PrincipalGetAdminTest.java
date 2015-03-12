package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.DataProp;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Test the GET method on the /api/principal/isadmin call.
 */
public class PrincipalGetAdminTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalGetAdminTest.class.getSimpleName() + " ***");
	}

	/**
	 * GET the admin info about the principal when the principal is an admin.
	 */
	@Test
	public void testPrincipalGetIsAdmin() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ISADMIN, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(true, jsonResponse.getBoolean("is_admin"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the admin info about the principal when the principal is not an
	 * admin.
	 */
	@Test
	public void testPrincipalGetNotAdmin() {
		persistent.authenticate("admin@is.not");

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ISADMIN, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(false, jsonResponse.getBoolean("is_admin"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		persistent.authenticate(new DataProp().getString("HEXAA_FEDID"));
	}
}
