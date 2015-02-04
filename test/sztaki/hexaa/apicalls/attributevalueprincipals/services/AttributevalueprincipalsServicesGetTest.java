package sztaki.hexaa.apicalls.attributevalueprincipals.services;

import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/// TODO minimális befektetéssel Normal-ra alakítható
/**
 * Tests the GET method on the /api/attributevalueorganizations/{id}/services
 * and /api/attributevalueorganizations/{id}/services/{id} call.
 */
public class AttributevalueprincipalsServicesGetTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsServicesGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray attributevalueprincipal = new JSONArray();

	/**
	 * JSONArray to store the created service.
	 */
	public static JSONArray services = new JSONArray();

	/**
	 * JSONArray to store the created organization.
	 */
	public static JSONArray organizations = new JSONArray();

	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	public static JSONArray entitlementpacks = new JSONArray();

	/**
	 * JSONArray to store the created attributespecs.
	 */
	public static JSONArray attributespecs = new JSONArray();

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"AttributevalueprincipalsServicesGetTest_service1",
				"AttributevalueprincipalsServicesGetTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributevalueprincipalsServicesGetTest_service1\", \"AttributevalueprincipalsServicesGetTest_service2\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(1,
				"AttributevalueprincipalsServicesGetTest_entitlementpack1");
		entitlementpacks.put(Utility.Create.entitlementpacks(2,
				"AttributevalueprincipalsServicesGetTest_entitlementpack2")
				.getJSONObject(0));
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(new String[] {\"AttributevalueprincipalsServicesGetTest_entitlementpack1\", \"AttributevalueprincipalsServicesGetTest_entitlementpack2\" }); did not succeed");
		}

		organizations = Utility.Create
				.organization("AttributevalueprincipalsServicesGetTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"AttributevalueprincipalsServicesGetTest_org1\" }); did not succeed");
		}
		Utility.Link.entitlementpackToOrg(1, new int[] { 1, 2 });

		attributespecs = Utility.Create.attributespec(
				new String[] { "AttributevalueprincipalsServicesGetTest_as1" },
				"manager");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueprincipalsServicesGetTest_as1\" }, \"manager\"); did not succeed");
		}
		Utility.Link.attributespecsToService(1, new int[] { 1, 2 }, true);
		Utility.Link.attributespecsToService(2, new int[] { 1, 2 }, true);
		attributevalueprincipal = Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsServicesGetTest_org_value1", 1,
				new int[] { 2 });
		if (attributevalueprincipal.length() < 1) {
			fail("Utility.Create.attributevalueprincipal(new String[] {\"AttributevalueprincipalsServicesGetTest_org_value1\" }, \"manager\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueprincipalsServicesGetTest.class
						.getSimpleName());
		for (int i = 0; i < attributevalueprincipal.length(); i++) {
			Utility.Remove.attributevalueprincipal(attributevalueprincipal
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
	 * GET all services for the attributevalue.
	 */
	@Test
	public void testAttributevalueprincipalsServicesGet() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES,
					BasicCall.REST.GET, null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals((JSONArray) attributevalueprincipal
					.getJSONObject(0).get("services"), (JSONArray) jsonResponse
					.get("service_ids"), JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET one service for the attributevalue.
	 */
	@Test
	public void testAttributevalueorganizationsGetServicesByID() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
					BasicCall.REST.GET, null, 1, 2);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONObject service;
		try {
			service = persistent.getResponseJSONObject(Const.Api.SERVICES_ID,
					BasicCall.REST.GET, null, 2, 2);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			JSONAssert.assertEquals(service, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
