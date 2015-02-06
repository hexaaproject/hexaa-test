package sztaki.hexaa.apicalls;

import java.util.logging.Level;
import java.util.logging.Logger;

import sztaki.hexaa.CleanTest;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.*;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;

import sztaki.hexaa.BasicCall.REST;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;

/**
 * Utility class to be inherited by the IsEmpty test classes. Implements
 * CleanTest and provides two methods for easier testing.
 */
public abstract class IsEmptyTest extends CleanTest {

	/**
	 * Calls the rest method on the constApi uri and expects an empty JSON
	 * response and 200 OK.
	 *
	 * @param constApi
	 *            an URI on the host.
	 * @param rest
	 *            a REST call to be called.
	 */
	public void expectingEmpty(String constApi, REST rest) {
		String stringResponse = persistent.call(constApi, rest);
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals("[]", stringResponse);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Calls the rest method on the constApi uri and expects an error code
	 * response and 404 Not Found.
	 *
	 * @param constApi
	 *            an URI on the host.
	 * @param rest
	 *            a REST call to be called.
	 */
	public void expectingNotFound(String constApi, REST rest) {
		String stringResponse = persistent.call(constApi, rest);
		try {
			assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
			assertEquals(
					"{\"code\":404,\"message\":\"Not Found\",\"errors\":null}",
					stringResponse);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Calls the rest method on the constApi uri and expects a fedid response
	 * and 200 OK. Can handle JSON Arrays and Objects as response as well.
	 *
	 * @param constApi
	 *            an URI on the host.
	 * @param rest
	 *            a REST call to be called.
	 */
	public void expectingFedid(String constApi, REST rest) {
		if (JSONParser.parseJSON(persistent.call(constApi, rest)) instanceof JSONObject) {
			JSONObject jsonResponse;
			try {
				jsonResponse = persistent.getResponseJSONObject(constApi, rest);
				try {
					assertEquals(Const.StatusLine.OK,
							persistent.getStatusLine());
					assertEquals(Const.HEXAA_FEDID,
							jsonResponse.getString("fedid"));
				} catch (AssertionError e) {
					AssertErrorHandler(e);
				}
				return;
			} catch (ResponseTypeMismatchException ex) {
				Logger.getLogger(IsEmptyTest.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		} else {
			JSONArray jsonArrayResponse;
			try {
				jsonArrayResponse = persistent.getResponseJSONArray(constApi,
						rest);
				try {
					assertEquals(Const.StatusLine.OK,
							persistent.getStatusLine());
					assertEquals(Const.HEXAA_FEDID, jsonArrayResponse
							.getJSONObject(0).getString("fedid"));
				} catch (AssertionError e) {
					AssertErrorHandler(e);
				}
				return;
			} catch (ResponseTypeMismatchException ex) {
				Logger.getLogger(IsEmptyTest.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	public void expectingZeroItems(String constApi, REST rest) {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(constApi, rest);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK,
					persistent.getStatusLine());
			assertEquals("0", jsonResponse.get("item_number"));
			JSONAssert.assertEquals(new JSONArray(),
					jsonResponse.getJSONArray("items"), false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
