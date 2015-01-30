package sztaki.hexaa.apicalls.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the PUT method on the /api/services/{id} call.
 */
public class ServicesPutTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ServicesPutTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	public static JSONArray services = new JSONArray();

	/**
	 * Creates two services.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesPutTest_service1", "ServicesPutTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesPutTest_service1\", \"ServicesPutTest_service2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesPutTest.class.getSimpleName());
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * PUTs the first service and checks that only the first one was modified
	 * and the second one is the original.
	 */
	@Test
	public void testServicesPut() {
		// Modify the first role
		JSONObject json = new JSONObject();
		json.put("entityid", services.getJSONObject(0).get("entityid"));
		json.put("name", "ServicesPutTest_service1_modified");
		json.put("url", services.getJSONObject(0).get("url"));
		json.put("description", services.getJSONObject(0).get("description"));

		persistent.call(Const.Api.SERVICES_ID, BasicCall.REST.PUT,
				json.toString(), services.getJSONObject(0).getInt("id"), 0);

		try {
			assertEquals(Const.StatusLine.NoContent, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonFirstResponse;
		JSONObject jsonSecondResponse;
		try {
			jsonFirstResponse = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID, BasicCall.REST.GET, null, services
							.getJSONObject(0).getInt("id"), 0);
			jsonSecondResponse = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID, BasicCall.REST.GET, null, services
							.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesPutTest.class.getName()).log(Level.SEVERE,
					null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			JSONAssert.assertEquals(json, jsonFirstResponse,
					JSONCompareMode.LENIENT);
			JSONAssert.assertEquals(services.getJSONObject(1),
					jsonSecondResponse, JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
