package sztaki.hexaa.apicalls.services.managers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import static org.junit.Assert.fail;
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
 * Tests the GET method on the /api/services/{id}/managers call.
 */
public class ServicesManagersGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesManagersGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store managers.
	 */
	public static JSONArray managers = new JSONArray();

	/**
	 * Creates two services and two managers and link the two managers to the
	 * first service.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.service(new String[] { "testService1", "testService2" });
		managers = Utility.Create.principal(new String[] { "principalTest1",
				"principalTest2" });
		try {
			managers.put(persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET));
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesManagersGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		Utility.Link.managersToService(1, new int[] { 2, 3 });
	}

	/**
	 * GET managers on both services.
	 */
	@Test
	public void testServicesManagersGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_MANAGERS, BasicCall.REST.GET, null,
					1, 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesManagersGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			JSONAssert.assertEquals(managers, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_MANAGERS, BasicCall.REST.GET, null,
					2, 2);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesManagersGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			JSONAssert.assertEquals(managers.getJSONObject(2),
					jsonResponse.getJSONObject(0), JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
