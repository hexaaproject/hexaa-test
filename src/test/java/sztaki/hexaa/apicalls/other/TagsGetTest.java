package sztaki.hexaa.apicalls.other;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

public class TagsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + TagsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray domains = new JSONArray();
	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray entitlementpacks = new JSONArray();
	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray organizations = new JSONArray();

	/**
	 * Creates two attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		domains = Utility.Create
				.securitydomain("TagsGetTest_sd1", "otherMasterKey",
						"This is a security domain to test the capability of posting one.");
		if (domains.length() < 1) {
			fail("Utility.Create.securitydomain(\"TagsGetTest_sd1\", \"otherMasterKey\", \"This is a security domain to test the capability of posting one.\"); did not succeed");
		}

		services = Utility.Create.service("TagsGetTest_service1",
				new String[] { "randomTag" });
		if (services.length() < 1) {
			fail("Utility.Create.service(\"TagsGetTest_service1\", new String[] {\"randomTag\"}); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacksPrivate(services
				.getJSONObject(0).getInt("id"),
				new String[] { "TagsGetTest_ep1" });
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacksPrivate(services.getJSONObject(0).getInt(\"id\"), new String[]{\"TagsGetTest_ep1\"}); did not succeed");
		}

		organizations = Utility.Create.organization("TagsGetTest_org1",
				new String[] { "randomTag" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"TagsGetTest_org1\", new String[] {\"randomTag\"}); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ TagsGetTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove
					.organization(organizations.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove
					.entitlementpack(entitlementpacks.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove
					.service(services.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove
					.securitydomain(domains.getJSONObject(i).getInt("id"));
		}
	}

	@Test
	public void testTagsGet() {
		JSONArray jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONArray(Const.Api.TAGS,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals("randomTag",jsonResponse.getJSONObject(0).getString("name"));
		} catch (AssertionError e){
			AssertErrorHandler(e);
		}

		try {
			jsonResponse = persistent.getResponseJSONArray(Const.Api.ENTITLEMENTPACKS_PUBLIC,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(entitlementpacks, jsonResponse, false);
		} catch (AssertionError e){
			AssertErrorHandler(e);
		}
		
		
		

	}
}
