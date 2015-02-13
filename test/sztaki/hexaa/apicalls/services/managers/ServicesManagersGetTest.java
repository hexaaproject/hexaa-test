package sztaki.hexaa.apicalls.services.managers;

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

/**
 * Tests the GET method on the /api/services/{id}/managers and
 * /api/services/{id}/manager/count calls.
 */
public class ServicesManagersGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesManagersGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store services.
	 */
	public static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store managers.
	 */
	public static JSONArray managers = new JSONArray();

	/**
	 * Creates two services and two principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "ServicesManagersGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] { \"ServicesManagersGetTest_service1\" }); did not succeed");
		}

		managers = Utility.Create
				.principal(new String[] { "ServicesManagersGetTest_pri1",
						"ServicesManagersGetTest_pri2" });
		if (managers.length() < 2) {
			fail("Utility.Create.principal(new String[] { \"ServicesManagersGetTest_pri1\", \"ServicesManagersGetTest_pri2\" }); did not succeed");
		}

		Utility.Link.managersToService(services.getJSONObject(0).getInt("id"),
				managers.getJSONObject(0).getInt("id"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesManagersGetTest.class.getSimpleName());
		for (int i = 0; i < managers.length(); i++) {
			Utility.Remove.principal(managers.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.persistent.isAdmin = true;
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Get the manager count.
	 */
	@Test
	public void testServicesManagersCount() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_MANAGER_COUNT, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(2, jsonResponse.get("count"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET managers on both services.
	 */
	@Test
	public void testServicesManagersGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_MANAGERS, BasicCall.REST.GET, null,
					services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			JSONAssert.assertEquals(managers.getJSONObject(0),
					jsonResponse.getJSONObject(1), JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

}
