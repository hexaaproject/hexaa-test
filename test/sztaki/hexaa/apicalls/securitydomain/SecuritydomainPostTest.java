package sztaki.hexaa.apicalls.securitydomain;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;

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
//		System.out.println("TearDownClass: "
//				+ SecuritydomainPostTest.class.getSimpleName());
//		for (int i = 0; i < attributespecs.length(); i++) {
//			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
//					.getInt("id"));
//		}
	}
	
	@Test
	public void testSecuritydomainPost() {
		JSONObject json = new JSONObject();
		json.put("name", "SecuritydomainPostTest_sd1");
		json.put("scoped_key", "alternativeTestMasterKey");
		
		persistent.setAdmin();
		persistent.call(Const.Api.SECURITYDOMAINS,BasicCall.REST.POST, json.toString());
		
		System.out.println(persistent.call());
		
		try {
			assertEquals(Const.StatusLine.Created,persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		
	}
}
