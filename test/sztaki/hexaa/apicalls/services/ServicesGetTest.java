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

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET methods on the /api/services and /api/services/{id} calls.
 */
public class ServicesGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ServicesGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();

	/**
	 * Creates two services.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesGetTest_service1", "ServicesGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesGetTest_service1\", \"ServicesGetTest_service2\" }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesGetTest.class.getSimpleName());
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Tests the /api/services GET which responds with an Array of available
	 * services.
	 */
	@Test
	public void testServicesGetArray() {
		JSONArray jsonResponseArray;
		try {
			jsonResponseArray = persistent.getResponseJSONArray(
					Const.Api.SERVICES, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesGetTest.class.getName()).log(Level.SEVERE,
					null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			JSONAssert.assertEquals(services, jsonResponseArray, false);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the /api/services/{id} GET which responds with an Object of that
	 * exact service.
	 */
	@Test
	public void testServicesGetByID() {
		JSONObject jsonResponseObject;
		// Get the first one
		try {
			jsonResponseObject = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID, BasicCall.REST.GET, null, services
							.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesGetTest.class.getName()).log(Level.SEVERE,
					null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			JSONAssert.assertEquals(services.getJSONObject(0),
					jsonResponseObject, false);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		// Get the second one
		try {
			jsonResponseObject = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID, BasicCall.REST.GET, null, services
							.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesGetTest.class.getName()).log(Level.SEVERE,
					null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			JSONAssert.assertEquals(services.getJSONObject(1),
					jsonResponseObject, false);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
	// TODO úgy gondolom ez egy extra teszteset volt ami totál felesleges.
	// /**
	// * Tests the /api/services GET which responds with an Array of available
	// * services and checks the contained Objects one by one.
	// */
	// @Test
	// public void testServicesGetByArray() {
	// JSONArray jsonResponseArray;
	// try {
	// jsonResponseArray = persistent.getResponseJSONArray(
	// Const.Api.SERVICES, BasicCall.REST.GET);
	// } catch (ResponseTypeMismatchException ex) {
	// Logger.getLogger(ServicesGetTest.class.getName()).log(Level.SEVERE,
	// null, ex);
	// fail(ex.getFullMessage());
	// return;
	// }
	// try {
	// assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
	// assertEquals("Expected " + services.length() + " services but got "
	// + jsonResponseArray.length(), services.length(),
	// jsonResponseArray.length());
	// } catch (AssertionError e) {
	// AssertErrorHandler(e);
	// return;
	// }
	// for (int i = 0; i < services.length(); i++) {
	// try {
	// JSONAssert.assertEquals(services.getJSONObject(i),
	// jsonResponseArray.getJSONObject(i), false);
	// } catch (AssertionError e) {
	// AssertErrorHandler(e);
	// }
	// }
	// }
}
