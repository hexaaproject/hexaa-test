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
 * Tests the PUT method on the /api/services/{id}/attributespecs/{asid} call.
 */
public class ServicesAttributespecsAddTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesAttributespecsAddTest.class.getSimpleName() + " ***");
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
	 * Creates two services, two attributespecs.
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
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ServicesAttributespecsAddTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Adds two attributespecs to one of the services and checks both of them.
	 */
	@Test
	public void testServicesAttributespecsAdd() {
		// PUT the first attributespec to the first service.
		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), attributespecs
						.getJSONObject(1).getInt("id"), true);

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONArray jsonResponseArray;
		// Get the first services' as-s.
		try {
			jsonResponseArray = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesAttributespecsAddTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(
					attributespecs.getJSONObject(1).getInt("id"),
					jsonResponseArray.getJSONObject(0).getInt(
							"attribute_spec_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
