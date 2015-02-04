package sztaki.hexaa.apicalls.services.attributespecs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/services/{id}/attributespecs call.
 */
public class ServicesAttributespecsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesAttributespecsGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();

	/**
	 * Creates two services, two attributespecs and links them together.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ServicesAttributespecsGetTest_service1",
				"ServicesAttributespecsGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesAttributespecsGetTest_service1\", \"ServicesAttributespecsGetTest_service2\" }); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(new String[] {
				"ServicesAttributespecsGetTest_as1",
				"ServicesAttributespecsGetTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"ServicesAttributespecsGetTest_as1\", \"ServicesAttributespecsGetTest_as2\" }, \"user\"); did not succeed");
		}
		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), new int[] {
						attributespecs.getJSONObject(0).getInt("id"),
						attributespecs.getJSONObject(1).getInt("id") }, true);
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesAttributespecsGetTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * GET the 2 services attributespecs and checks if they are with the correct
	 * id.
	 */
	@Test
	public void testServicesAttributespecsGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesAttributespecsGetTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(2, jsonResponse.length());
			assertEquals(attributespecs.getJSONObject(0).getInt("id"),
					jsonResponse.getJSONObject(0).getInt("attribute_spec_id"));
			assertEquals(attributespecs.getJSONObject(1).getInt("id"),
					jsonResponse.getJSONObject(1).getInt("attribute_spec_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(1).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesAttributespecsGetTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(0, jsonResponse.length());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
