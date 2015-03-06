package sztaki.hexaa.apicalls.securitydomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

public class SecuritydomainGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ SecuritydomainGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray domains = new JSONArray();

	/**
	 * Creates two attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		domains = Utility.Create
				.securitydomain("SecuritydomainGetTest_sd1",
						"otherMasterKey",
						"This is a security domain to test the capability of posting one.");
		if (domains.length() < 1) {
			fail("Utility.Create.securitydomain(\"SecuritydomainGetTest_sd1\", \"otherMasterKey\", \"This is a security domain to test the capability of posting one.\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ SecuritydomainGetTest.class.getSimpleName());
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove
					.securitydomain(domains.getJSONObject(i).getInt("id"));
		}
	}

	@Test
	public void testSecuritydomainGetWithArray() {
		JSONArray jsonResponse;
		try {
			persistent.setAdmin();
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SECURITYDOMAINS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
			JSONAssert.assertEquals(domains, jsonResponse, false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	@Test
	public void testSecuritydomainGetWithItems() {
		JSONObject jsonItems;
		try {
			persistent.setAdmin();
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SECURITYDOMAINS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
			JSONAssert.assertEquals(domains, jsonResponse, false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	@Test
	public void testSecuritydomainGetWithId() {
		JSONObject jsonResponse;
		try {
			persistent.setAdmin();
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.SECURITYDOMAINS_ID, BasicCall.REST.GET, domains
							.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
			JSONAssert.assertEquals(domains.getJSONObject(0), jsonResponse,
					false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
