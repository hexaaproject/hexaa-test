package sztaki.hexaa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;

import sztaki.hexaa.BasicCall.REST;

/**
 * Utility class to be inherited by the IsEmpty test classes. Implements
 * CleanTest and provides two methods for easier testing.
 */
public abstract class IsEmptyTest extends NormalTest {

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
		BasicCall expectingEmptyCall = new BasicCall();
		String stringResponse = expectingEmptyCall.call(constApi, rest);
		try {
			assertEquals(Const.StatusLine.OK,
					expectingEmptyCall.getStatusLine());
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
		expectingNotFound(constApi, rest, false);
	}

	/**
	 * Calls the rest method on the constApi uri and expects an error code
	 * response and 404 Not Found.
	 *
	 * @param constApi
	 *            an URI on the host.
	 * @param rest
	 *            a REST call to be called.
	 * @param isAdmin
	 *            boolean, true if the call should be used as admin for the
	 *            proper result
	 */
	public void expectingNotFound(String constApi, REST rest, boolean isAdmin) {
		BasicCall expectingNotFoundCall = new BasicCall();

		if (isAdmin) {
			expectingNotFoundCall.setAdmin();
		}

		String stringResponse = expectingNotFoundCall.call(constApi, rest);
		try {
			assertEquals(Const.StatusLine.NotFound,
					expectingNotFoundCall.getStatusLine());
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
		JSONCall expectingFedidCall = new JSONCall();
		if (JSONParser.parseJSON(expectingFedidCall.call(constApi, rest,
				BasicCall.HEXAA_ID)) instanceof JSONObject) {
			JSONObject jsonResponse;
			try {
				expectingFedidCall.setAdmin();
				expectingFedidCall.setOffset(0);
				jsonResponse = expectingFedidCall.getResponseJSONObject(
						constApi, rest, BasicCall.HEXAA_ID);
				if (jsonResponse.has("item_number")) {
					if (Integer.valueOf(jsonResponse.get("item_number")
							.toString()) > 0) {
						try {
							assertEquals(Const.StatusLine.OK,
									expectingFedidCall.getStatusLine());
							assertEquals(
									new DataProp().getString("HEXAA_FEDID"),
									jsonResponse.getJSONArray("items")
											.getJSONObject(0)
											.getString("fedid"));
						} catch (AssertionError e) {
							AssertErrorHandler(e);
						}
					}
				} else {
					try {
						assertEquals(Const.StatusLine.OK,
								expectingFedidCall.getStatusLine());
						assertEquals(new DataProp().getString("HEXAA_FEDID"),
								jsonResponse.getString("fedid"));
					} catch (AssertionError e) {
						AssertErrorHandler(e);
					}
				}
				return;
			} catch (ResponseTypeMismatchException ex) {
				fail(ex.getFullMessage());
				return;
			}
		} else {
			JSONArray jsonArrayResponse;
			try {
				jsonArrayResponse = expectingFedidCall.getResponseJSONArray(
						constApi, rest, BasicCall.HEXAA_ID);
				try {
					assertEquals(Const.StatusLine.OK,
							expectingFedidCall.getStatusLine());
					assertEquals(
							new DataProp().getString("HEXAA_FEDID"),
							jsonArrayResponse.getJSONObject(0).getString(
									"fedid"));
				} catch (AssertionError e) {
					AssertErrorHandler(e);
				}
				return;
			} catch (ResponseTypeMismatchException ex) {
				fail(ex.getFullMessage());
				return;
			}
		}
	}

	public void expectingZeroItems(String constApi, REST rest) {
		JSONCall expectingZeroItemsCall = new JSONCall();
		JSONObject jsonResponse;
		try {
			expectingZeroItemsCall.setOffset(0);
			jsonResponse = expectingZeroItemsCall.getResponseJSONObject(
					constApi, rest);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK,
					expectingZeroItemsCall.getStatusLine());
			assertEquals(0, jsonResponse.get("item_number"));
			JSONAssert.assertEquals(new JSONArray(),
					jsonResponse.getJSONArray("items"), false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
