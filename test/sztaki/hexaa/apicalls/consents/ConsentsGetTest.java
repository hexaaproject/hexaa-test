package sztaki.hexaa.apicalls.consents;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/consents, /api/consents/{id} and
 * /api/consents/{sid}/service calls.
 */
public class ConsentsGetTest extends CleanTest {

	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray consents = new JSONArray();

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ConsentsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates the necessary objects on the server to begin the tests.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.service("testService1");
		Utility.Create.attributespec("randomOID1", "user");
		Utility.Link.attributespecsToService(1, 1, true);
		consents = Utility.Create.consent(true, new int[] { 1 }, 1);

		Utility.Create.service("testService2");
		Utility.Create.attributespec("randomOID2", "user");
		Utility.Link.attributespecsToService(2, 2, true);
		consents.put(Utility.Create.consent(true, new int[] { 2 }, 2)
				.getJSONObject(0));
	}

	/**
	 * Gets all the consents and asserts them with the locally stored ones.
	 */
	@Test
	public void testConsentsGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(Const.Api.CONSENTS,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

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
					Const.Api.CONSENTS_ID, BasicCall.REST.GET, null, 1, 1);
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
					1, 1);
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
