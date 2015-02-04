package sztaki.hexaa.apicalls.services.attributespecs;

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
 *
 */
public class ServicesAttributespecsSetTest extends CleanTest {

	/**
	 * JSONArray to store the created attributespecs.
	 */
	public static JSONArray attributespecs = new JSONArray();

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesAttributespecsSetTest.class.getSimpleName() + " ***");
	}

	/**
	 * Creates an organization, two role, a service and a principal.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create
				.service(new String[] { "ServicesAttributespecsSetTest_service1" });
		attributespecs = Utility.Create.attributespec(new String[] {
				"ServicesAttributespecsSetTest_as1",
				"ServicesAttributespecsSetTest_as2" }, "manager");
	}

	/**
	 * PUT the attributespecs to a service as an array.
	 */
	@Test
	public void testServicesAttributespecsSet() {
		Utility.Link.attributespecsToServiceByArray(1, new int[] { 1, 2 },
				new boolean[] { true, true });

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		JSONArray jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, 1, 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesAttributespecsSetTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			for (int i = 0; i < jsonResponse.length()
					&& i < attributespecs.length(); i++) {
				assertEquals(
						attributespecs.getJSONObject(i).getInt("id"),
						jsonResponse.getJSONObject(i).getInt(
								"attribute_spec_id"));
			}
		} catch (AssertionError e) {
			System.out.println("expected: " + attributespecs + "\n"
					+ "actual:   " + jsonResponse);
			AssertErrorHandler(e);
		}
	}
}
