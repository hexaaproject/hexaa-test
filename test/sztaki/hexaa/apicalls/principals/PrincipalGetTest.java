package sztaki.hexaa.apicalls.principals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Tests the GET method on the /api/principal/self and /api/principals calls.
 */
public class PrincipalGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + PrincipalGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * GET info about the current principal.
	 */
	@Test
	public void testPrincipalSelfGet() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
				Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(Const.HEXAA_FEDID, jsonResponse.getString("fedid"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET all the principals and search for the admin.
	 */
	@Test
	public void testPrincipalsGet() {
		JSONObject jsonItems;
		try {
			persistent.setOffset(0);
			persistent.setAdmin();
			jsonItems = persistent.getResponseJSONObject(Const.Api.PRINCIPALS,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		JSONArray jsonResponse = this.getItems(jsonItems);
		
		int i = 0;
		while (i < jsonResponse.length()
				&& jsonResponse.getJSONObject(i).getInt("id") != Const.HEXAA_ID) {
			i++;
		}

		try {
			assertEquals(Const.HEXAA_FEDID,
					jsonResponse.getJSONObject(i).get("fedid"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the principal by fedid.
	 */
	@Test
	public void testPrincipalsGetByFedid() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PRINCIPALS_FEDID, BasicCall.REST.GET, null, 0, 0,
					Const.HEXAA_FEDID);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(Const.HEXAA_FEDID, jsonResponse.getString("fedid"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * GET the principal by id.
	 */
	@Test
	public void testPrincipalsGetById() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.PRINCIPALS_ID_ID, BasicCall.REST.GET, null,
					Const.HEXAA_ID, 0);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(Const.HEXAA_FEDID, jsonResponse.getString("fedid"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
