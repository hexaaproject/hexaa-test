package sztaki.hexaa.apicalls.attributespecs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the GET method on the /api/attributespecs/{id}/services call.
 */
public class AttributespecsServicesGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributespecsServicesGetTest.class.getSimpleName() + " ***");
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
	 * Creates one attributespec and two services and links the attributespec to
	 * the first service.
	 */
	@BeforeClass
	public static void setUpClass() {
		attributespecs = Utility.Create.attributespec(
				"AttributespecsServicesGetTest_as1", "user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec( \"AttributespecsServicesGetTest_as1\", \"user\"); did not succeed");
		}
		services = Utility.Create.service(new String[] {
				"AttributespecsServicesGetTest_service1",
				"AttributespecsServicesGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributespecsServicesGetTest_service1\", \"AttributespecsServicesGetTest_service2\" }); did not succeed");
		}
		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"),
				new int[] { attributespecs.getJSONObject(0).getInt("id") },
				true);
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributespecsGetTest.class.getSimpleName());
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
	}

	/**
	 * GET the service linked to the attributespec.
	 */
	@Test
	public void testAttributespecsServicesGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTESPECS_ID_SERVICES, BasicCall.REST.GET,
					null, attributespecs.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(services.getJSONObject(0).getInt("id"), jsonResponse
					.getJSONObject(0).getInt("service_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
