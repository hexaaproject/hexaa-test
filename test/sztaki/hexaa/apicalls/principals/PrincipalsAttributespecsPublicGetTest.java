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
import sztaki.hexaa.DataProp;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Test the GET method on the /api/principal/attributespecs call for public
 * attributespecs.
 */
public class PrincipalsAttributespecsPublicGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ PrincipalsAttributespecsPublicGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();

	/**
	 * Creates one organization, two services and two attributespecs and links
	 * them.
	 */
	@BeforeClass
	public static void setUpClass() {
		persistent.authenticate(new DataProp().getString("HEXAA_FEDID"));
		organizations = Utility.Create
				.organization(new String[] { "PrincipalsAttributespecsPublicGetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"PrincipalsAttributespecsPublicGetTest_org1\" }); did not succeed");
		}
		
		services = Utility.Create.service(new String[] {
				"PrincipalsAttributespecsPublicGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.services(new String[] {\"PrincipalsAttributespecsPublicGetTest_service1\" }, \"user\"); did not succeed");
		}

		attributespecs = Utility.Create.attributespec(new String[] {
				"PrincipalsAttributespecsPublicGetTest_as1" }, "user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"PrincipalsAttributespecsPublicGetTest_as1\" }, \"user\"); did not succeed");
		}
		Utility.Link.attributespecsPublicToService(services.getJSONObject(0)
				.getInt("id"), new int[] { attributespecs.getJSONObject(0)
				.getInt("id") });
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ PrincipalsAttributespecsPublicGetTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
	}

	/**
	 * GET the available (public) attributespecs (as the principal is not a
	 * member of any role).
	 */
	@Test
	public void testPrincipalsAttributespecsPublicGetWithItems() {
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.PRINCIPAL_ATTRIBUTESPECS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributespecs, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the available (public) attributespecs (as the principal is not a
	 * member of any role).
	 */
	@Test
	public void testPrincipalsAttributespecsPublicGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.PRINCIPAL_ATTRIBUTESPECS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributespecs, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
