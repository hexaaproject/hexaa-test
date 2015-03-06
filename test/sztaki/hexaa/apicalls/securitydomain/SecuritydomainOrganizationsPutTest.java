package sztaki.hexaa.apicalls.securitydomain;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

public class SecuritydomainOrganizationsPutTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ SecuritydomainOrganizationsPutTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray domains = new JSONArray();
	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates two attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		principals = Utility.Create.principal(new String[] {
				"SecuritydomainOrganizationsPutTest_fedid1",
				"SecuritydomainOrganizationsPutTest_fedid2" });
		if (principals.length() < 1) {
			fail("Utility.Create.principal(new String[] {\"SecuritydomainOrganizationsPutTest_fedid1\",\"SecuritydomainOrganizationsPutTest_fedid2\"}); did not succeed");
		}

		organizations = Utility.Create
				.organization("SecuritydomainOrganizationsPutTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"SecuritydomainOrganizationsPutTest_org1\"); did not succeed");
		}

		domains = Utility.Create
				.securitydomain("SecuritydomainOrganizationsPutTest_sd1",
						"otherMasterKey",
						"This is a security domain to test the capability of posting one.");
		if (domains.length() < 1) {
			fail("Utility.Create.securitydomain(\"SecuritydomainOrganizationsPutTest_sd1\", \"otherMasterKey\", \"This is a security domain to test the capability of posting one.\"); did not succeed");
		}

		new Authenticator().authenticate(
				"SecuritydomainOrganizationsPutTest_fedid2",
				Const.ALTERNATIVE_SECRET);

		organizations.put(Utility.Create.organization(
				"SecuritydomainOrganizationsPutTest_org2").getJSONObject(0));

		new Authenticator().authenticate(
				"SecuritydomainOrganizationsPutTest_fedid1",
				Const.ALTERNATIVE_SECRET);
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ SecuritydomainOrganizationsPutTest.class.getSimpleName());
		new Authenticator()
				.authenticate(Const.HEXAA_FEDID, Const.MASTER_SECRET);
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove
					.securitydomain(domains.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.persistent.setAdmin();
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Get all attributespecs as this call is restricted to organization
	 * managers or in this case security domain authenticated principals.
	 */
	@Test
	public void testSecuritydomainOrganizationsPut() {
		persistent
				.call(Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
						BasicCall.REST.GET, organizations.getJSONObject(1)
								.getInt("id"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		persistent
				.call(Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
						BasicCall.REST.GET, organizations.getJSONObject(0)
								.getInt("id"));

		try {
			assertEquals(Const.StatusLine.Forbidden, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		new Authenticator()
				.authenticate(Const.HEXAA_FEDID, Const.MASTER_SECRET);

		JSONObject json = new JSONObject();
		json.put("organizations", new int[] { organizations.getJSONObject(0)
				.getInt("id") });
		persistent.setAdmin();
		persistent.call(Const.Api.SECURITYDOMAINS_ID_ORGANIZATIONS,
				BasicCall.REST.PUT, json.toString(), domains.getJSONObject(0)
						.getInt("id"));

		new Authenticator().authenticate(
				"SecuritydomainOrganizationsPutTest_fedid1",
				Const.ALTERNATIVE_SECRET);

		persistent
				.call(Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
						BasicCall.REST.GET, organizations.getJSONObject(0)
								.getInt("id"));

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

}
