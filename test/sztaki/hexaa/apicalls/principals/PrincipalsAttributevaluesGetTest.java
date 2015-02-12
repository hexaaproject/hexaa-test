package sztaki.hexaa.apicalls.principals;

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
 * Test the GET method on the /api/principal/attributevalueprincipal call.
 */
public class PrincipalsAttributevaluesGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsAttributevaluesGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	private static JSONArray attributevalue = new JSONArray();

	/**
	 * Creates the necessary objects for the test.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "PrincipalsAttributevaluesGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.services(new String[] {\"PrincipalsAttributevaluesGetTest_service1\" }, \"user\"); did not succeed");
		}

		attributespecs = Utility.Create
				.attributespec(
						new String[] { "PrincipalsAttributevaluesGetTest_as1" },
						"user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"PrincipalsAttributevaluesGetTest_as1\" }, \"user\"); did not succeed");
		}
		Utility.Link.attributespecsPublicToService(services.getJSONObject(0)
				.getInt("id"), new int[] { attributespecs.getJSONObject(0)
				.getInt("id") });

		attributevalue = Utility.Create.attributevalueprincipal(
				new String[] { "PrincipalsAttributevaluesGetTest_av1" },
				attributespecs.getJSONObject(0).getInt("id"));

		attributevalue.getJSONObject(0).put("attribute_spec_id",
				attributevalue.getJSONObject(0).remove("attribute_spec"));
		attributevalue.getJSONObject(0).put("service_ids",
				attributevalue.getJSONObject(0).remove	("services"));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalsAttributevaluesGetTest.class.getSimpleName());
		for (int i = 0; i < attributevalue.length(); i++) {
			Utility.Remove.attributevalueprincipal(attributevalue
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
	 * GET all attributevalues.
	 */
	@Test
	public void testPrincipalGetAttributevalues() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributevalue, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET all attributevalue specified by the attributespec id.
	 */
	@Test
	public void testPrincipalsGetAttributevalueBySpec() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent
					.getResponseJSONObject(
							Const.Api.PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS,
							BasicCall.REST.GET, null, 0, attributespecs
									.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			JSONAssert.assertEquals(attributevalue, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
