package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Test the DELETE method on the /api/principal call.
 */
public class PrincipalsDeleteSelfTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsDeleteSelfTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Re-Authenticates as testPrincipal for the test.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.persistent.setAdmin();
		principals = Utility.Create.principal("PrincipalsDeleteSelfTest_pri1");
		new Authenticator().authenticate("PrincipalsDeleteSelfTest_pri1");
	}

	/**
	 * GET the test principal, DELETE it, than test it for forbidden and against
	 * the list of principals.
	 */
	@Test
	public void testPrincipalDeleteSelf() {
		Utility.Remove.principalSelf();

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		persistent.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET);

		try {
			assertEquals(Const.StatusLine.Unauthorized,
					persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		new Authenticator().authenticate(Const.HEXAA_FEDID);

		JSONObject jsonItems;
		try {
			persistent.setAdmin();
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(Const.Api.PRINCIPALS,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		for (int i = 0; i < jsonResponse.length(); i++) {
			try {
				JSONAssert.assertNotEquals(principals.getJSONObject(0),
						jsonResponse.getJSONObject(i), JSONCompareMode.LENIENT);
			} catch (AssertionError e) {
				AssertErrorHandler(e);
			}
		}
	}
}
