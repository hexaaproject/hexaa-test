package sztaki.hexaa.apicalls.other;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/properties call.
 */
public class PropertiesGetTest extends NormalTest {

	/**
	 * GET the properties and checks that the basic informations are present.
	 */
	@Test
	public void testPropertiesGet() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PROPERTIES, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		System.out.println(jsonResponse.toString());

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(true, jsonResponse.has("entitlement_base"));
			assertEquals(true, jsonResponse.has("version"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
