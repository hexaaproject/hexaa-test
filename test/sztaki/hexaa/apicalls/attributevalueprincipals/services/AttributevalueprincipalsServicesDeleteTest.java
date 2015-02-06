package sztaki.hexaa.apicalls.attributevalueprincipals.services;

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
public class AttributevalueprincipalsServicesDeleteTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalsServicesDeleteTest.class
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
	public static JSONArray attributevalueprincipal = new JSONArray();

	/**
	 * Creates one attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"AttributevalueprincipalsServicesDeleteTest_service1",
				"AttributevalueprincipalsServicesDeleteTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributevalueprincipalsServicesDeleteTest_service1\", \"AttributevalueprincipalsServicesDeleteTest_service2\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				"AttributevalueprincipalsServicesDeleteTest_pack1");
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), \"AttributevalueprincipalsServicesDeleteTest_pack1\"); did not succeed");
		}
		entitlementpacks.put(Utility.Create.entitlementpacks(
				services.getJSONObject(1).getInt("id"),
				"AttributevalueprincipalsServicesDeleteTest_pack2")
				.getJSONObject(0));
		if (entitlementpacks.length() < 2) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(1).getInt(\"id\"), \"AttributevalueprincipalsServicesDeleteTest_pack2\"); did not succeed");
		}

		organizations = Utility.Create
				.organization("AttributevalueprincipalsServicesDeleteTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization( \"AttributevalueprincipalsServicesDeleteTest_org1\" ); did not succeed");
		}
		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] {
				entitlementpacks.getJSONObject(0).getInt("id"),
				entitlementpacks.getJSONObject(1).getInt("id") });

		attributespecs = Utility.Create
				.attributespec(
						new String[] { "AttributevalueprincipalsServicesDeleteTest_as1" },
						"user");
		if (attributespecs.length() < 1) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueprincipalsServicesDeleteTest_as1\" }, \"manager\"); did not succeed");
		}

		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"),
				new int[] { attributespecs.getJSONObject(0).getInt("id") },
				true);
		Utility.Link.attributespecsToService(
				services.getJSONObject(1).getInt("id"),
				new int[] { attributespecs.getJSONObject(0).getInt("id") },
				true);

		attributevalueprincipal = Utility.Create.attributevalueprincipal(
				"AttributevalueprincipalsServicesDeleteTest_org_value1",
				attributespecs.getJSONObject(0).getInt("id"));
		if (attributevalueprincipal.length() < 1) {
			fail("Utility.Create.attributevalueorganization( \"AttributevalueprincipalsServicesDeleteTest_org_value1\", attributespecs.getJSONObject(0).getInt(\"id\")); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueprincipalsServicesDeleteTest.class
						.getSimpleName());
		for (int i = 0; i < attributevalueprincipal.length(); i++) {
			Utility.Remove.attributevalueorganization(attributevalueprincipal
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
	public void testAttributevalueprincipalsDeleteServices() {
		Utility.Remove.serviceFromAttributevalueprincipals(
				attributevalueprincipal.getJSONObject(0).getInt("id"), services
						.getJSONObject(0).getInt("id"));

		try {
			assertEquals(Const.StatusLine.NoContent,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
					BasicCall.REST.GET, null, attributevalueprincipal
							.getJSONObject(0).getInt("id"), services
							.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			for (int i = 0; i < attributevalueprincipal.length(); i++) {
				JSONAssert.assertNotEquals(
						attributevalueprincipal.getJSONObject(i), jsonResponse,
						false);
			}
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
