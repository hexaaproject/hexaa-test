package sztaki.hexaa.apicalls.consents;

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
 * Tests the GET method on the /api/consents, /api/consents/{id} and
 * /api/consents/{sid}/service calls.
 */
public class ConsentsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ConsentsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray consents = new JSONArray();

	/**
	 * Creates the necessary objects on the server to begin the tests.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"ConsentsGetTest_service1", "ConsentsGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"ConsentsGetTest_service1\", \"ConsentsGetTest_service1\" }); did not succeed");
		}

		attributespecs = Utility.Create.attributespec(new String[] {
				"ConsentsGetTest_as1", "ConsentsGetTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"ConsentsGetTest_as1\", \"ConsentsGetTest_as2\" }, \"user\"); did not succeed");
		}

		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), attributespecs
						.getJSONObject(0).getInt("id"), true);
		Utility.Link.attributespecsToService(
				services.getJSONObject(1).getInt("id"), attributespecs
						.getJSONObject(1).getInt("id"), true);
		consents = Utility.Create.consent(true, new int[] { attributespecs
				.getJSONObject(0).getInt("id") }, services.getJSONObject(0)
				.getInt("id"));
		if (consents.length() < 1) {
			fail("Utility.Create.consent(true, new int[] { attributespecs.getJSONObject(0).getInt(\"id\") }, services.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
		consents.put(Utility.Create.consent(true,
				new int[] { attributespecs.getJSONObject(1).getInt("id") },
				services.getJSONObject(1).getInt("id")).getJSONObject(0));
		if (consents.length() < 2) {
			fail("Utility.Create.consent(true, new int[] { attributespecs.getJSONObject(1).getInt(\"id\") }, services.getJSONObject(1).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ ConsentsGetTest.class.getSimpleName());
		for (int i = 0; i < consents.length(); i++) {
			Utility.Remove.consent(consents.getJSONObject(i).getInt("id"));
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
	 * Gets all the consents and asserts them with the locally stored ones.
	 */
	@Test
	public void testConsentsGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(Const.Api.CONSENTS,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(consents, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Get one consent by its id and asserts it with the locally stored one.
	 */
	@Test
	public void testConsentsGetById() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.CONSENTS_ID, BasicCall.REST.GET, null, consents
							.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(consents.getJSONObject(0), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Gets the consent by the id of the service enabled by it.
	 */
	@Test
	public void testConsentsGetByServiceId() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.CONSENTS_SID_SERVICE, BasicCall.REST.GET, null,
					0, services.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(consents.getJSONObject(0), jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
