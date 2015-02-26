package sztaki.hexaa.apicalls.securitydomain;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.Utility;

public class SecuritydomainPostTest extends NormalTest {
	
	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ SecuritydomainPostTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray domains = new JSONArray();
	

	/**
	 * Creates two securitydomains.
	 */
	@BeforeClass
	public static void setUpClass() {
//		attributespecs = Utility.Create.attributespec(new String[] {
//				"AttributespecsGetTest_as1", "AttributespecsGetTest_as2" },
//				"user");
//		if (attributespecs.length() < 2) {
//			fail("Utility.Create.attributespec(new String[] {\"AttributespecsGetTest_as1\", \"AttributespecsGetTest_as2\" }, \"user\"); did not succeed");
//		}
	}
	
	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ SecuritydomainPostTest.class.getSimpleName());
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove.securitydomain(domains.getJSONObject(i)
					.getInt("id"));
		}
	}
	
	@Test
	public void testSecuritydomainPost() {		
		domains = Utility.Create.securitydomain("SecuritydomainPostTest_sd1", "alternativeTestMasterKey", "This is a security domain to test the capability of posting one.");
		
		try {
			assertEquals(Const.StatusLine.Created,Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		
	}
}
