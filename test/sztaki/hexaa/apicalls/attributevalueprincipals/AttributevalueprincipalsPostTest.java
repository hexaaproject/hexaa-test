package sztaki.hexaa.apicalls.attributevalueprincipals;

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
 * Tests the POST method on the /api/attributevalueprincipals/{asid} call.
 */
public class AttributevalueprincipalsPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsPostTest.class.getSimpleName()
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
	 * JSONArray to store the created services.
	 */
	private static JSONArray attributevalueprincipals = new JSONArray();

	/**
	 * Creates two services, two attributespecs and links them together.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"AttributevalueprincipalsPostTest_service1",
				"AttributevalueprincipalsPostTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributevalueprincipalsPostTest_service1\", \"AttributevalueprincipalsPostTest_service2\" }); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(new String[] {
				"AttributevalueprincipalsPostTest_as1",
				"AttributevalueprincipalsPostTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueprincipalsPostTest_as1\", \"AttributevalueprincipalsPostTest_as2\" }, \"user\"); did not succeed");
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
				+ AttributevalueprincipalsPostTest.class.getSimpleName());

		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributevalueprincipals.length(); i++) {
			Utility.Remove.attributevalueprincipal(attributevalueprincipals
					.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Creates an attributevalueprincipal with one value and one of the created
	 * attributespecs, and verifies it with GET.
	 */
	@Test
	public void testAttributevalueprincipalsPost() {

		int fapadosId = attributevalueprincipals.length();

		attributevalueprincipals.put(Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsPostTest_pri_value1",
				attributespecs.getJSONObject(0).getInt("id")).getJSONObject(0));

		attributevalueprincipals.getJSONObject(fapadosId).put(
				"attribute_spec_id",
				attributevalueprincipals.getJSONObject(fapadosId).remove(
						"attribute_spec"));
		attributevalueprincipals.getJSONObject(fapadosId).put(
				"service_ids",
				attributevalueprincipals.getJSONObject(fapadosId).remove(
						"services"));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonArrayResponse = jsonItems.getJSONArray("items");

		int i = 0;
		while (i < jsonArrayResponse.length()
				&& jsonArrayResponse.getJSONObject(i).getInt(
						"attribute_spec_id") != attributespecs.getJSONObject(0)
						.getInt("id")) {
			i++;
		}

		try {
			JSONAssert
					.assertEquals(
							attributevalueprincipals.getJSONObject(fapadosId),
							jsonArrayResponse.getJSONObject(i),
							JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Creates an attributevalueprincipal with one value and one of the created
	 * attributespecs, and verifies it with GET, linked it to a service.
	 */
	@Test
	public void testAttributevalueprincipalsPostWithService() {

		int fapadosId = attributevalueprincipals.length();

		attributevalueprincipals.put(Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsPostTest_pri_value2",
				attributespecs.getJSONObject(1).getInt("id"),
				new int[] { services.getJSONObject(0).getInt("id") })
				.getJSONObject(0));

		attributevalueprincipals.getJSONObject(fapadosId).put(
				"attribute_spec_id",
				attributevalueprincipals.getJSONObject(fapadosId).remove(
						"attribute_spec"));
		attributevalueprincipals.getJSONObject(fapadosId).put(
				"service_ids",
				attributevalueprincipals.getJSONObject(fapadosId).remove(
						"services"));

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonArrayResponse = jsonItems.getJSONArray("items");

		int i = 0;
		while (i < jsonArrayResponse.length()
				&& jsonArrayResponse.getJSONObject(i).getInt(
						"attribute_spec_id") != attributespecs.getJSONObject(1)
						.getInt("id")) {
			i++;
		}

		try {
			JSONAssert
					.assertEquals(
							attributevalueprincipals.getJSONObject(fapadosId),
							jsonArrayResponse.getJSONObject(i),
							JSONCompareMode.LENIENT);
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
