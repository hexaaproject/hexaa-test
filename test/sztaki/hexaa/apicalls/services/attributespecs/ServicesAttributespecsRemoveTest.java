package sztaki.hexaa.apicalls.services.attributespecs;

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
 * Tests the DELETE method on the /api/services/{id}/attributespecs/{asid} call.
 */
public class ServicesAttributespecsRemoveTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesAttributespecsRemoveTest.class.getSimpleName()
				+ " ***");
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
				"ServicesAttributespecsRemoveTest_service1",
				"ServicesAttributespecsRemoveTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ServicesAttributespecsRemoveTest_service1\", \"ServicesAttributespecsRemoveTest_service2\" }); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(new String[] {
				"ServicesAttributespecsRemoveTest_as1",
				"ServicesAttributespecsRemoveTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"ServicesAttributespecsRemoveTest_as1\", \"ServicesAttributespecsRemoveTest_as2\" }, \"user\"); did not succeed");
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
				+ ServicesAttributespecsRemoveTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * DELETE(remove) one of the attributespecs from the first service, and
	 * check both services.
	 */
	@Test
	public void testServicesAttributespecsRemove() {
		Utility.Remove.attributespecFromService(services.getJSONObject(0)
				.getInt("id"), attributespecs.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		JSONArray jsonResponse = this.getItems(jsonItems);
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(attributespecs.getJSONObject(1).getInt("id"),
					jsonResponse.getJSONObject(0).getInt("attribute_spec_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(1).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		
		jsonResponse = jsonItems.getJSONArray("items");
		
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(0, jsonResponse.length());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
