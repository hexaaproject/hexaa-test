package sztaki.hexaa.apicalls.securitydomain;

import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

public class SecuritydomainOrganizationsPutTest extends NormalTest{

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ SecuritydomainOrganizationsPutTest.class.getSimpleName() + " ***");
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
		principals = Utility.Create.principal("SecuritydomainServicesPutTest_fedid");
		
		organizations = Utility.Create.organization("SecuritydomainServicesPutTest_org1");
		
		domains = Utility.Create
				.securitydomain("SecuritydomainOrganizationsPutTest_sd1",
						"otherMasterKey",
						"This is a security domain to test the capability of posting one.");
		if (domains.length() < 1) {
			fail("Utility.Create.securitydomain(\"SecuritydomainOrganizationsPutTest_sd1\", \"otherMasterKey\", \"This is a security domain to test the capability of posting one.\"); did not succeed");
		}
		
		
		new Authenticator().authenticate("SecuritydomainServicesPutTest_fedid", Const.ALTERNATIVE_SECRET);
		
		organizations.put(Utility.Create.organization("SecuritydomainServicesPutTest_org2").getJSONObject(0));
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ SecuritydomainOrganizationsPutTest.class.getSimpleName());
		new Authenticator().authenticate(Const.HEXAA_FEDID, Const.MASTER_SECRET);
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove
					.principal(principals.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove
					.organization(organizations.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove
					.securitydomain(domains.getJSONObject(i).getInt("id"));
		}
	}
	
	@Test
	public void testSecuritydomainOrganizationsPut() {
		System.out.println(persistent.call(Const.Api.ORGANIZATIONS, BasicCall.REST.GET));
		
	}

}
