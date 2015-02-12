package sztaki.hexaa.apicalls.organizations.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.apicalls.attributevalueorganizations.services.AttributevalueorganizationsServicesGetTest;

/**
 * Test the GET method related to Attributes and Attributevalue on
 * organizations.
 */
public class OrganizationsAttributesGet extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationsAttributesGet.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created service.
	 */
	public static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created entitlementpacks.
	 */
	public static JSONArray entitlementpacks = new JSONArray();
	/**
	 * JSONArray to store the created organization.
	 */
	public static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	public static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created attributevalues.
	 */
	public static JSONArray attributevalueorganization = new JSONArray();

	/**
	 * Creates all the necessary objects on the server to test the methods to
	 * GET the attributes and attributevalues of an organization.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "AttributevalueorganizationsServicesGetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] {\"AttributevalueorganizationsServicesGetTest_service1\" }); did not succeed");
		}

		entitlementpacks = Utility.Create.entitlementpacks(services
				.getJSONObject(0).getInt("id"),
				"AttributevalueorganizationsServicesGetTest_entitlementpack1");
		if (entitlementpacks.length() < 1) {
			fail("Utility.Create.entitlementpacks(services.getJSONObject(0).getInt(\"id\"), \"AttributevalueorganizationsServicesGetTest_entitlementpack1\"); did not succeed");
		}

		organizations = Utility.Create
				.organization("AttributevalueorganizationsServicesGetTest_org1");
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(\"AttributevalueorganizationsServicesGetTest_org1\"); did not succeed");
		}

		Utility.Link.entitlementpackToOrg(organizations.getJSONObject(0)
				.getInt("id"), new int[] { entitlementpacks.getJSONObject(0)
				.getInt("id") });

		attributespecs = Utility.Create.attributespec(new String[] {
				"AttributevalueorganizationsServicesGetTest_as1",
				"AttributevalueorganizationsServicesGetTest_as2" }, "manager");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsServicesGetTest_as1\", \"AttributevalueorganizationsServicesGetTest_as2\" }, \"manager\"); did not succeed");
		}

		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), new int[] {
						attributespecs.getJSONObject(0).getInt("id"),
						attributespecs.getJSONObject(1).getInt("id") }, true);

		Utility.Link.entitlementpackToOrg(1, new int[] { 1 });

		attributevalueorganization = Utility.Create.attributevalueorganization(
				"AttributevalueorganizationsServicesGetTest_org_value1",
				attributespecs.getJSONObject(0).getInt("id"), organizations
						.getJSONObject(0).getInt("id"));
		attributevalueorganization
				.put(Utility.Create
						.attributevalueorganization(
								"AttributevalueorganizationsServicesGetTest_org_value1",
								attributespecs.getJSONObject(1).getInt("id"),
								organizations.getJSONObject(0).getInt("id"))
						.getJSONObject(0));
		if (attributevalueorganization.length() < 2) {
			fail("Utility.Create.attributevalueorganization( \"AttributevalueorganizationsServicesGetTest_org_value1\",attributespecs.getJSONObject(0).getInt(\"id\"), organizations.getJSONObject(0).getInt(\"id\"), new int[] { services.getJSONObject(1).getInt(\"id\") } ); did not succeed");
		}
		
		for (int i = 0; i < attributevalueorganization.length();i++) {
			attributevalueorganization.getJSONObject(i).put(
					"organization_id",
					attributevalueorganization.getJSONObject(i).remove(
							"organization"));
			attributevalueorganization.getJSONObject(i).put(
					"attribute_spec_id",
					attributevalueorganization.getJSONObject(i).remove(
							"attribute_spec"));
			attributevalueorganization.getJSONObject(i).put("service_ids",
					attributevalueorganization.getJSONObject(i).remove("services"));
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueorganizationsServicesGetTest.class
						.getSimpleName());
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
	 * GET the available attributespecs.
	 */
	@Test
	public void testOrganizationsAttributespecsGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
					BasicCall.REST.GET, null, organizations.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributespecs, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET a specific attributespecs values.
	 */
	@Test
	public void testOrganizationsAttributevaluesBySpecsGet() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent
					.getResponseJSONObject(
							Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS_ASID_ATTRIBUTEVALUEORGANIZATIONS,
							BasicCall.REST.GET,
							null,
							organizations.getJSONObject(0).getInt("id"),
							attributevalueorganization.getJSONObject(0).getInt(
									"id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributevalueorganization.getJSONObject(0), jsonResponse.getJSONObject(0),
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET all the attributevalues.
	 */
	@Test
	public void testOrganizationsAllAttributevalues() {
		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
					BasicCall.REST.GET, null, organizations.getJSONObject(0)
							.getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(attributevalueorganization, jsonResponse,
					JSONCompareMode.LENIENT);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
