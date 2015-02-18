package sztaki.hexaa.apicalls.services.attributespecs;

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
import sztaki.hexaa.apicalls.attributespecs.AttributespecsGetTest;

/**
 *
 */
public class ServicesAttributespecsSetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesAttributespecsSetTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created attributespecs.
	 */
	public static JSONArray attributespecs = new JSONArray();
	/**
	 * JSONArray to store the created services.
	 */
	public static JSONArray services = new JSONArray();

	/**
	 * Creates an organization, two role, a service and a principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		services = Utility.Create
				.service(new String[] { "ServicesAttributespecsSetTest_service1" });
		if (services.length() < 1) {
			fail("Utility.Create.service(new String[] {\"ServicesAttributespecsSetTest_service1\"); did not succeed");
		}
		attributespecs = Utility.Create.attributespec(new String[] {
				"ServicesAttributespecsSetTest_as1",
				"ServicesAttributespecsSetTest_as2" }, "manager");
		if (attributespecs.length() < 2) {
			fail("Utility.Create.attributespec(new String[] {\"ServicesAttributespecsSetTest_as1\", \"ServicesAttributespecsSetTest_as2\" }, \"manager\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ AttributespecsGetTest.class.getSimpleName());
		for (int i = 0; i < attributespecs.length(); i++) {
			Utility.Remove.attributespec(attributespecs.getJSONObject(i)
					.getInt("id"));
		}
		for (int i = 0; i < services.length(); i++) {
			Utility.Remove.service(services.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * PUT the attributespecs to a service as an array.
	 */
	@Test
	public void testServicesAttributespecsSet() {
		Utility.Link.attributespecsToServiceSet(services.getJSONObject(0)
				.getInt("id"), new int[] {
				attributespecs.getJSONObject(0).getInt("id"),
				attributespecs.getJSONObject(1).getInt("id") }, new boolean[] {
				true, true });

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONObject jsonItems;
		try {
			jsonItems = persistent.getResponseJSONObject(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, services.getJSONObject(0).getInt("id"), 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);

		try {
			for (int i = 0; i < jsonResponse.length()
					&& i < attributespecs.length(); i++) {
				assertEquals(
						attributespecs.getJSONObject(i).getInt("id"),
						jsonResponse.getJSONObject(i).getInt(
								"attribute_spec_id"));
			}
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
