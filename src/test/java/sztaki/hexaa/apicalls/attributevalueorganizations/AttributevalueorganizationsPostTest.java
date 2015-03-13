package sztaki.hexaa.apicalls.attributevalueorganizations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

/**
 * Tests the POST method on the /api/attributevalueorganizations/{asid} call.
 */
public class AttributevalueorganizationsPostTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueorganizationsPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributevalueorganizations = new JSONArray();
	/**
	 * JSONArray to store the created attributespecs.
	 */
	private static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	private static JSONArray services = new JSONArray();
	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();

	/**
	 * Creates two services, two attributespecs and links them together.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create.service(new String[] {
				"AttributevalueorganizationsPostTest_service1",
				"AttributevalueorganizationsPostTest_service2" });
		if (services.length() < 2) {
			fail("Utility.Create.service(new String[] {\"AttributevalueorganizationsPostTest_service1\", \"AttributevalueorganizationsPostTest_service2\" }); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(new String[] {
				"AttributevalueorganizationsPostTest_as1",
				"AttributevalueorganizationsPostTest_as2" }, "manager");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"AttributevalueorganizationsPostTest_as1\", \"AttributevalueorganizationsPostTest_as2\" }, \"user\"); did not succeed");
		}
		organizations = Utility.Create.organization(new String[] {
				"AttributevalueorganizationsPostTest_org1",
				"AttributevalueorganizationsPostTest_org2" });
		if (organizations.length() < 1) {
			fail("Utility.Create.organization(new String[] {\"AttributevalueorganizationsPostTest_org1\", \"AttributevalueorganizationsPostTest_org2\" }); did not succeed");
		}
		Utility.Link.attributespecsToService(
				services.getJSONObject(0).getInt("id"), new int[] {
						attributespecs.getJSONObject(0).getInt("id"),
						attributespecs.getJSONObject(1).getInt("id") }, false);
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributevalueorganizationsPostTest.class.getSimpleName());
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < attributevalueorganizations.length(); i++) {
			Utility.Remove
					.attributevalueorganization(attributevalueorganizations
							.getJSONObject(i).getInt("id"));
		}
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Creates an attributevalueorganization with one value and one of the
	 * created attributespecs, and verifies it with GET.
	 */
	@Test
	public void testAttributevalueorganizationsPost() {
		attributevalueorganizations = Utility.Create
				.attributevalueorganization(
						"AttributevalueorganizationsPostTest_av_org1",
						attributespecs.getJSONObject(0).getInt("id"),
						organizations.getJSONObject(0).getInt("id"));
		
		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
			return;
		}

		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATION,
					BasicCall.REST.GET, null, organizations
							.getJSONObject(0).getInt("id"), 1);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonArrayResponse = jsonItems.getJSONArray("items");

		try {
			assertEquals(attributevalueorganizations.getJSONObject(0).get("organization"),
					jsonArrayResponse.getJSONObject(0).get("organization_id"));
			assertEquals(attributevalueorganizations.getJSONObject(0).get("attribute_spec"),
					jsonArrayResponse.getJSONObject(0).get("attribute_spec_id"));
			assertEquals(attributevalueorganizations.getJSONObject(0).get("id"),
					jsonArrayResponse.getJSONObject(0).get("id"));
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
