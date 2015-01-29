package sztaki.hexaa.apicalls.services.organizations;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/services/{id}/organization call.
 */
public class ServicesOrganizationsGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesOrganizationsGetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store managers.
	 */
	public static JSONArray organizations = new JSONArray();

	/**
	 * Creates two services an organization an entitlementpack and links the
	 * pack to the organization.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.service(new String[] { "testService1", "testService2" });
		organizations = Utility.Create
				.organization(new String[] { "testOrgSrv1" });
		Utility.Create.entitlementpacks(1, new String[] { "testPack1" });
		Utility.Link.entitlementpackToOrg(1, new int[] { 1 });
	}

	/**
	 * GET the organization linked to the service.
	 */
	@Test
	public void testServicesOrganizationsGet() {
		JSONArray jsonArrayResponse;
		try {
			jsonArrayResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_ORGANIZATIONS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesOrganizationsGetTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(1, jsonArrayResponse.length());
			assertEquals(
					1,
					jsonArrayResponse.getJSONObject(
							jsonArrayResponse.length() - 1).getInt("id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
