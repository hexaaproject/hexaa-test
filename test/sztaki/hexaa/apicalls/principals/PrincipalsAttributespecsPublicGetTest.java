package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
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
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();

	/**
	 * Creates one organization, two services and two attributespecs and links
	 * them.
	 */
	@BeforeClass
	public static void setUpClass() {
		new Authenticator().authenticate(Const.HEXAA_FEDID);
		organizations = Utility.Create
				.organization(new String[] { "PrincipalsAttributespecsPublicGetTest_org1" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"PrincipalsAttributespecsPublicGetTest_org1\" }); did not succeed");
		}
		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"), 1);

		services = Utility.Create.service(new String[] {
				"PrincipalsAttributespecsPublicGetTest_service1",
				"PrincipalsAttributespecsPublicGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.services(new String[] {\"PrincipalsAttributespecsPublicGetTest_service1\", \"PrincipalsAttributespecsPublicGetTest_service2\" }, \"user\"); did not succeed");
		}

		attributespecs = Utility.Create.attributespec(new String[] {
				"PrincipalsAttributespecsPublicGetTest_as1",
				"PrincipalsAttributespecsPublicGetTest_as2" }, "user");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"PrincipalsAttributespecsPublicGetTest_as1\", \"PrincipalsAttributespecsPublicGetTest_as2\" }, \"user\"); did not succeed");
		}
		Utility.Link.attributespecsPublicToService(services.getJSONObject(0)
				.getInt("id"), new int[] { attributespecs.getJSONObject(0)
				.getInt("id") });
		Utility.Link.attributespecsPrivateToService(services.getJSONObject(1)
				.getInt("id"), new int[] { attributespecs.getJSONObject(1)
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
			Utility.Remove
					.service(services.getJSONObject(i).getInt("id"));
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
	public void testPrincipalsAttributespecsPublicGet() {
		JSONArray jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.PRINCIPAL_ATTRIBUTESPECS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray publicAttributespecs = new JSONArray();

		publicAttributespecs.put(attributespecs.getJSONObject(0));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(publicAttributespecs, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
