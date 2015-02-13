package sztaki.hexaa.apicalls.services.attributespecs;

import static org.junit.Assert.*;

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
				"ServicesAttributespecsGetTest_as2" }, "manager");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"ServicesAttributespecsGetTest_as1\", \"ServicesAttributespecsGetTest_as2\" }, \"manager\"); did not succeed");
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
						.getJSONObject(1).getInt("id"), false);

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
			return;
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertNotEquals(new JSONArray(), jsonResponse,
					JSONCompareMode.LENIENT);
			assertEquals(
					attributespecs.getJSONObject(1).getInt("id"),
					jsonResponse.getJSONObject(0).getInt(
							"attribute_spec_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
