package sztaki.hexaa.apicalls.services;

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
 * Tests the DELETE method on the /api/services/{id} call.
 */
public class ServicesDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ServicesDeleteTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	public static JSONArray services = new JSONArray();

	/**
	 * Creates one organization and two services.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesDeleteTest_service1", "ServicesDeleteTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesDeleteTest_service1\", \"ServicesDeleteTest_service2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesDeleteTest.class.getSimpleName());
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * DELETE the first service and checks that only the second one exists.
	 */
	@Test
	public void testServicesDelete() {
		// The DELETE call.
		Utility.Remove.service(services.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// Getting server response: list of services
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(Const.Api.SERVICES,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		JSONArray jsonResponse = this.getItems(jsonItems);

		// Find the service that should exist by id, empty jsonobject if not
		// exist
		JSONObject jsonServiceInResponse = new JSONObject();
		for (int i = 0; i < jsonResponse.length(); i++) {
			if (jsonResponse.getJSONObject(i).getInt("id") == services
					.getJSONObject(1).getInt("id")) {
				jsonServiceInResponse = jsonResponse.getJSONObject(i);
			}
		}

		// Compare the existing ones, check that the deleted one is not there
		try {
			JSONAssert.assertEquals(services.getJSONObject(1),
					jsonServiceInResponse, JSONCompareMode.LENIENT);
			for (int i = 0; i < jsonResponse.length(); i++) {
				JSONAssert.assertNotEquals(services.getJSONObject(0),
						jsonResponse.getJSONObject(i), JSONCompareMode.LENIENT);
			}
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
