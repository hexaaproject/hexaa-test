package sztaki.hexaa.apicalls.services.attributespecs;

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
 * Tests the DELETE method on the /api/services/{id}/attributespecs/{asid} call.
 */
public class ServicesAttributespecsRemoveTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ ServicesAttributespecsRemoveTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates two services, two attributespecs and links them together.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.service(new String[] { "testService1", "testService2" });
		Utility.Create.attributespec(new String[] { "asTest1", "asTest2" },
				"user");
		Utility.Link.attributespecsToService(1, new int[] { 1, 2 }, true);
	}

	/**
	 * DELETE(remove) one of the attributespecs from the first service, and
	 * check both services.
	 */
	@Test
	public void testServicesAttributespecsRemove() {
		Utility.Remove.attributespecFromService(1, 1);

		try {
			assertEquals(Const.StatusLine.NoContent,
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
			Logger.getLogger(ServicesAttributespecsRemoveTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(2,
					jsonResponse.getJSONObject(0).getInt("attribute_spec_id"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SERVICES_ID_ATTRIBUTESPECS, BasicCall.REST.GET,
					null, 2, 1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(ServicesAttributespecsRemoveTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(0, jsonResponse.length());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
