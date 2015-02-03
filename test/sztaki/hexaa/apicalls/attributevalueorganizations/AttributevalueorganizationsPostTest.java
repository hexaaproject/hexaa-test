package sztaki.hexaa.apicalls.attributevalueorganizations;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsGetTest;

/**
 * Tests the POST method on the /api/attributevalueorganizations/{asid} call.
 */
public class AttributevalueorganizationsPostTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueorganizationsPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributevalueorganizations = new JSONArray();
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
				"AttributevalueorganizationsPostTest_service1",
				"AttributevalueorganizationsPostTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributevalueorganizationsPostTest_service1\", \"AttributevalueorganizationsPostTest_service2\" }); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(new String[] {
				"AttributevalueorganizationsPostTest_as1",
				"AttributevalueorganizationsPostTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsPostTest_as1\", \"AttributevalueorganizationsPostTest_as2\" }, \"user\"); did not succeed");
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
		for (int i = 0; i < attributevalueorganizations.length(); i++) {
			Utility.Remove
					.attributevalueorganization(attributevalueorganizations
							.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Creates an attributevalueorganization with one value and one of the
	 * created attributespecs, and verifies it with GET.
	 */
	@Test
	public void testAttributevalueorganizationsPost() {
		JSONObject json = new JSONObject();
		json.put("value", "testValueString");
		json.put("service_ids", new JSONArray(new int[] {}));
		json.put("attribute_spec_id", 1);
		json.put("organization_id", 1);

		attributevalueorganizations = Utility.Create
				.attributevalueorganization("testValueString", 1, 1);
		// /TODO át lehetne írni rendesen normal-ra, itt lustaság vezérelt

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
			return;
		}

		JSONArray jsonArrayResponse;
		try {
			jsonArrayResponse = persistent.getResponseJSONArray(
					Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
					BasicCall.REST.GET, null, attributevalueorganizations
							.getJSONObject(0).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(
					AttributevalueorganizationsPostTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		JSONObject jsonResponse = jsonArrayResponse.getJSONObject(0);
		System.out.println(jsonResponse);
		try {
			JSONAssert
					.assertEquals(json, jsonResponse, JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
