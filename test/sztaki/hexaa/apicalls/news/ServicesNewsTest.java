package sztaki.hexaa.apicalls.news;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/services/{id}/news call.
 */
public class ServicesNewsTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ServicesNewsTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates the necessary objects.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("testOrg");
		Utility.Create.service("testService");
		Utility.Create.entitlementpacks(1, "testPack");
		Utility.Link.entitlementpackToOrg(1, 1);
	}

	/**
	 * Test the method.
	 */
	@Test
	public void testOrganizationNews() {
		JSONArray jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_NEWS, BasicCall.REST.GET, null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationsNewsTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse);

		try {
			assertEquals(3, jsonResponse.length());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
