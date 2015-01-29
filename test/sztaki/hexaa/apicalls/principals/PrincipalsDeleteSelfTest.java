package sztaki.hexaa.apicalls.principals;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the DELETE method on the /api/principal call.
 */
public class PrincipalsDeleteSelfTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsDeleteSelfTest.class.getSimpleName() + " ***");
	}

	/**
	 * Re-Authenticates as testPrincipal for the test.
	 */
	@BeforeClass
	public static void setUpClass() {
		new Authenticator().authenticate("testPrincipal@something.test");
	}

	/**
	 * GET the test principal, DELETE it, than test it for forbidden and against
	 * the list of principals.
	 */
	@Test
	public void testPrincipalDeleteSelf() {
		JSONObject testPrincipal = (JSONObject) JSONParser.parseJSON(persistent
				.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET));

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

		Object response = JSONParser.parseJSON(persistent.call(
				Const.Api.PRINCIPALS, BasicCall.REST.GET));

		if (response instanceof JSONObject) {
			fail("Not a JSONArray but JSONObject: "
					+ ((JSONObject) response).toString());
		}
		JSONArray jsonResponse = (JSONArray) response;

		for (int i = 0; i < jsonResponse.length(); i++) {
			try {
				JSONAssert.assertNotEquals(testPrincipal,
						jsonResponse.getJSONObject(i), JSONCompareMode.LENIENT);
			} catch (AssertionError e) {
				AssertErrorHandler(e);
			}
		}
	}
}
