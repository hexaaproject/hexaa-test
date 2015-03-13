package sztaki.hexaa.apicalls.attributevalueorganizations.services;

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

/**
 * Tests the PUT method on the
 * /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueorganizationsServicesDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueorganizationsServicesDeleteTest.class
						.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray entitlementpacks = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray attributevalueorganization = new JSONArray();

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"AttributevalueorganizationsServicesDeleteTest_service1",
				"AttributevalueorganizationsServicesDeleteTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributevalueorganizationsServicesDeleteTest_service1\", \"AttributevalueorganizationsServicesDeleteTest_service2\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				"AttributevalueorganizationsServicesDeleteTest_pack1");
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), \"AttributevalueorganizationsServicesDeleteTest_pack1\"); did not succeed");
		}
		entitlementpacks.put(Utility.Create.entitlementpacks(
				services.getJSONObject(1).getInt("id"),
				"AttributevalueorganizationsServicesDeleteTest_pack2")
				.getJSONObject(0));
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(1).getInt(\"id\"), \"AttributevalueorganizationsServicesDeleteTest_pack2\"); did not succeed");
		}

		organizations = Utility.Create
				.organization("AttributevalueorganizationsServicesDeleteTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"AttributevalueorganizationsServicesDeleteTest_org1\" ); did not succeed");
		}
		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] {
				entitlementpacks.getJSONObject(0).getInt("id"),
				entitlementpacks.getJSONObject(1).getInt("id") });

		attributespecs = Utility.Create
				.attributespec(
						new String[] { "AttributevalueorganizationsServicesDeleteTest_as1" },
						"manager");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsServicesDeleteTest_as1\" }, \"manager\"); did not succeed");
		}

		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), new int[] {
						attributespecs.getJSONObject(0).getInt("id") }, true);
		Utility.Link.attributespecsToService(
				services.getJSONObject(1).getInt("id"), new int[] {
						attributespecs.getJSONObject(0).getInt("id") }, true);

		attributevalueorganization = Utility.Create.attributevalueorganization(
				"AttributevalueorganizationsServicesDeleteTest_org_value1",
				attributespecs.getJSONObject(0).getInt("id"), organizations
						.getJSONObject(0).getInt("id"), new int[] {
						services.getJSONObject(0).getInt("id"),
						services.getJSONObject(1).getInt("id") });
		if (attributevalueorganization.length() < 1) {
			fail("Utility.Create.attributevalueorganization( \"AttributevalueorganizationsServicesDeleteTest_org_value1\", attributespecs.getJSONObject(0).getInt(\"id\"), organizations.getJSONObject(0).getInt(\"id\"), new int[] { services.getJSONObject(0).getInt(\"id\"), services.getJSONObject(1).getInt(\"id\") }); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueorganizationsServicesDeleteTest.class.getSimpleName());
		for (int i = 0; i < attributevalueorganization.length(); i++) {
			Utility.Remove
					.attributevalueorganization(attributevalueorganization
							.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < entitlementpacks.length(); i++) {
			Utility.Remove.entitlementpack(entitlementpacks.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * PUT a service to the attributevalue and checks that after the put the
	 * same service is available with /api/services/{id} and
	 * /api/attributevalueorganizations/{id}/services/{sid}.
	 */
	@Test
	public void testAttributevalueorganizationsDeleteServices() {
		Utility.Remove.serviceFromAttributevalueorganizations(
				attributevalueorganization.getJSONObject(0).getInt("id"),
				services.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
					BasicCall.REST.GET, null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			for (int i = 0; i < attributevalueorganization.length(); i++) {
				JSONAssert.assertNotEquals(
						attributevalueorganization.getJSONObject(i),
						jsonResponse, false);
			}
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
