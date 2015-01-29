package sztaki.hexaa.apicalls.entitlementpacks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the DELETE method on the /api/entitlementpacks/{id} call.
 */
public class EntitlementpacksDeleteTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementpacksDeleteTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	private static JSONArray entitlementpacks = new JSONArray();

	/**
	 * Creates one service and two entitlementpacks.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.service(new String[] { "testService1" });
		entitlementpacks = Utility.Create.entitlementpacks(1, new String[] {
				"testEntitlementpacks1", "testEntitlementpacks2" });
	}

	/**
	 * DELETEs one of the two created entitlements, and GETs both of them.
	 */
	@Test
	public void testEntitlementsDelete() {
		// The DELETE call.
		Utility.Remove.entitlementpack(1);

		try {
			// Checks the status line from the DELETE call for 204
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		// GETs the one that was deleted and checks the status line for 404
		persistent.call(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.GET,
				null, 1, 0);
		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
			// GETs the second entitlements and asserts it as a JSON
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.GET, null, 2,
					0);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(EntitlementpacksDeleteTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			// Checks the status line for 200
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(entitlementpacks.getJSONObject(1),
					jsonResponse, JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
