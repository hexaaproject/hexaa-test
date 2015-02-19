package sztaki.hexaa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.apache.http.Header;
import org.apache.http.HttpMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;

/**
 * Support class that implements the 4 RESTful API calls. Can easily be expanded
 * if needed. The call methods with no String path parameters are Deprecated and
 * should NOT be used anymore, use the call methods WITH String path parameters.
 * Calling the child classes are not supported anymore.
 */
public class BasicCall {

	/**
	 * Enables/disables the admin property for the next call. Disabled by
	 * default.
	 */
	private boolean isAdmin = false;

	/**
	 * Enables the admin property for the next call.
	 */
	public void setAdmin() {
		this.isAdmin = true;
	}

	/**
	 * Enables/disables the offset property for the next call. Disabled by
	 * default.
	 */
	private boolean isOffset = false;
	/**
	 * Value of the offset property. 0 by default.
	 */
	private int offset = 0;

	/**
	 * Sets the provided value as the offset value for the next call.
	 * 
	 * @param offset
	 *            the first item sent in the response.
	 */
	public void setOffset(int offset) {
		this.offset = offset;
		this.isOffset = true;
	}

	/**
	 * Enables/disables the limit property for the next call. Disabled by
	 * default.
	 */
	private boolean isLimit = false;
	/**
	 * Value of the limit property. 0 by default.
	 */
	private int limit = 0;

	/**
	 * Sets the provided value as the limit value for the next call.
	 * 
	 * @param limit
	 *            the number of items sent in the response.
	 */
	public void setLimit(int limit) {
		this.limit = limit;
		this.isLimit = true;
	}

	/**
	 * The normal response of the call, can not be null, but can be an empty
	 * string.
	 */
	private String response = "";
	/**
	 * The StatusLine of the last call, never null, maybe empty before any call
	 * was made.
	 */
	private String statusLine = "";

	/**
	 * The headers of the last call, null before any call was made and maybe
	 * null if the call request fails.
	 */
	private Header[] headers = null;

	/**
	 * The relative path of the URI. Should be in /app.php/api/example format.
	 * Advised to use the {@link Const.Api} for the servers constants values.
	 */
	private String path;

	/**
	 * The fedid to insert into certain url-s.
	 */
	private String fedid;

	/**
	 * The token required by a few calls in the url.
	 */
	private String token;

	/**
	 * The email required by a few calls in the url.
	 */
	@SuppressWarnings("unused")
	private String email;

	/**
	 * The requested ID, always inserted into the {id} part of the url.
	 */
	private int id;

	/**
	 * The possible special ID, inserted in one of the possible formats: {sid},
	 * {asid}, {pid}, {eid}, {epid}.
	 */
	private int sId;

	/**
	 * The JSON part of the message that will be inserted as an entity to the
	 * http requests body.
	 */
	private String json = new String();

	/**
	 * The format that will concated to the end of the call url. By default it
	 * is ".json".
	 */
	private String format = new String(".json");

	/* *** Setter/getter methods *** */
	protected void setMaster(String path, REST restCall, String json, int id,
			int sId, String fedid) {
		this.setPath(path);
		this.setString(json);
		this.setId(id);
		this.setSId(sId);
		this.setFedid(fedid);
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 */
	protected void setPath(String path) {
		this.path = path;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 */
	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the special id.
	 *
	 * @param sId
	 */
	protected void setSId(int sId) {
		this.sId = sId;
	}

	/**
	 * Sets the JSON string.
	 *
	 * @param json
	 */
	protected void setString(String json) {
		if (json == null) {
			json = new String();
		}
		this.json = json;
	}

	/**
	 * Sets the fedid.
	 *
	 * @param fedid
	 */
	protected void setFedid(String fedid) {
		this.fedid = fedid;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Sets the token.
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the status line associated with the last call. Persist until a
	 * new call is placed and gives an empty String before any call.
	 *
	 * @return String: the status line of the last call, never null, maybe
	 *         empty.
	 */
	public String getStatusLine() {
		return statusLine;
	}

	/**
	 * Returns the header with the specified name if exists, null if not.
	 *
	 * @param name
	 *            the name of the header.
	 * @return the header with the specified name.
	 */
	public Header getHeader(String name) {
		for (Header h : headers) {
			if (h.getName().contains(name)) {
				return h;
			}
		}
		return null;
	}

	/**
	 * Prints all possible data for debug purposes.
	 */
	public void printData() {
		System.out.println("\t" + statusLine);
		System.out.println("\t" + path);
		System.out.println("\t" + Integer.toString(id));
		System.out.println("\t" + Integer.toString(sId));
		System.out.println("\t" + json);
	}

	/**
	 * Set the format to the specified value. Can only be ".json" or ""(empty
	 * string) at the moment.
	 *
	 * @param f
	 *            string, format type of the calls, can be ".json" or ""(empty
	 *            string).
	 */
	public void setFormat(String f) {
		if (f.equals(".json") || f.equals("")) {
			this.format = f;
		}
	}

	/**
	 * Returns the string representation of the call's response. Matches the
	 * return value of the respective call() method.
	 *
	 * @return string representation of the call's response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Returns the string representation of the call's response. Matches the
	 * return value of the respective call() method.
	 *
	 * @return string representation of the call's response
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject()
			throws ResponseTypeMismatchException {
		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) response);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", response.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * Returns the string representation of the call's response. Matches the
	 * return value of the respective call() method.
	 *
	 * @return string representation of the call's response
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray()
			throws ResponseTypeMismatchException {
		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) response);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", response.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * Sets the parameter as the response string and returns it unchanged.
	 * 
	 * @param r
	 *            string to be set as the this.response string.
	 * @return string: this.response.
	 */
	protected String setResponse(String r) {
		this.response = r;
		return response;
	}

	/* *** Constructor *** */
	/**
	 * Constructor
	 */
	public BasicCall() {
		this.id = 0;

		this.sId = 0;

		this.path = null;

		this.json = "";

		this.fedid = "fedid";

		this.token = "token";

		this.email = "email";
	}

	/**
	 * Enumeration to easily differentiate between the 4 types of calls. Values:
	 * GET, POST, PUT, DELETE.
	 */
	public enum REST {
		/**
		 * Use it for GET methods.
		 */
		GET,
		/**
		 * Use it for POST methods.
		 */
		POST,
		/**
		 * Use it for PUT methods.
		 */
		PUT,
		/**
		 * Use it for DELETE methods.
		 */
		DELETE,
		/**
		 * Use it for PATCH methods.
		 */
		PATCH,
	}

	/* *** Normal calls, returns the response json as a String *** */
	/**
	 * Most basic call type, only use it for simple GET methods, as it does not
	 * get the required json/id/sid/fedid for most of the more complex calls
	 * like any POST/PUT methods or GET/DELETE methods with required ids. These
	 * situations see {@link call(String path, REST restCall, String json, int
	 * id, int sId)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 */
	public String call(String path, REST restCall) {
		return call(path, restCall, "", 0, 0, "fedid");
	}

	/**
	 * The normal call type for creation. Does not have ids or fedid, ids are 1
	 * for default, fedid is simply "fedid", if they are needed use {@link
	 * call(String path, REST restCall, String json, int id, int sId)} or
	 * {@link call(String path, REST restCall, String json, int id, int sId,
	 * String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json) {
		return call(path, restCall, json, 0, 0, "fedid");
	}

	/**
	 * The normal call type, use this for get calls with 1 required id. Does not
	 * have a fedid, if fedid is required use {@link call(String path, REST
	 * restCall, String json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 */
	public String call(String path, REST restCall, int id) {
		return call(path, restCall, "", id, 0, "fedid");
	}

	/**
	 * The normal call type, use this for get calls with 2 required id. Does not
	 * have a fedid, if fedid is required use {@link call(String path, REST
	 * restCall, String json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 */
	public String call(String path, REST restCall, int id, int sId) {
		return call(path, restCall, "", id, sId, "fedid");
	}

	/**
	 * The normal call type, use this for most calls. Does not have a fedid, if
	 * fedid is required use {@link call(String path, REST restCall, String
	 * json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json, int id, int sId) {
		return call(path, restCall, json, id, sId, "fedid");
	}

	/**
	 * Call with fedid provided. Use this only if fedid is necessary, otherwise
	 * see {@link call(String path, REST restCall, String json, int id, int
	 * sId)} and {@link String call(String path, REST restCall)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @param fedid
	 *            String, a special id used only in
	 *            /api/principals/{fedid}/fedid
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json, int id,
			int sId, String fedid) {
		this.setMaster(path, restCall, json, id, sId, fedid);

		return this.callSwitch(restCall);
	}

	/* *** Calls to get JSONObjects and JSONArrays already parsed *** */
	/**
	 * Most basic call type, only use it for simple GET methods, as it does not
	 * get the required json/id/sid/fedid for most of the more complex calls
	 * like any POST/PUT methods or GET/DELETE methods with required ids. These
	 * situations see {@link call(String path, REST restCall, String json, int
	 * id, int sId)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall)
			throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONObject(path, restCall, "", 0,
				0, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * The normal call type for creation. Does not have ids or fedid, ids are 1
	 * for default, fedid is simply "fedid", if they are needed use {@link
	 * call(String path, REST restCall, String json, int id, int sId)} or
	 * {@link call(String path, REST restCall, String json, int id, int sId,
	 * String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json) throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONObject(path, restCall, json,
				0, 0, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * The normal call type, use this for get calls with 1 required id. Does not
	 * have a fedid, if fedid is required use {@link call(String path, REST
	 * restCall, String json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall, int id)
			throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONObject(path, restCall, "",
				id, 0, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * The normal call type, use this for get calls with 2 required id. Does not
	 * have a fedid, if fedid is required use {@link call(String path, REST
	 * restCall, String json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall, int id,
			int sId) throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONObject(path, restCall, "",
				id, sId, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * The normal call type, use this for most calls. Does not have a fedid, if
	 * fedid is required use {@link call(String path, REST restCall, String
	 * json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json, int id, int sId) throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONObject(path, restCall, json,
				id, sId, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * Call with fedid provided. Use this only if fedid is necessary, otherwise
	 * see {@link call(String path, REST restCall, String json, int id, int
	 * sId)} and {@link String call(String path, REST restCall)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @param fedid
	 *            String, a special id used only in
	 *            /api/principals/{fedid}/fedid
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json, int id, int sId, String fedid)
			throws ResponseTypeMismatchException {
		this.setMaster(path, restCall, json, id, sId, fedid);

		Object tempResponse = this.callSwitch(restCall);

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(
					"JSONArray instead of JSONObject", "JSONArray",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONObject", "String", serverResponse);
		}
	}

	/**
	 * Most basic call type, only use it for simple GET methods, as it does not
	 * get the required json/id/sid/fedid for most of the more complex calls
	 * like any POST/PUT methods or GET/DELETE methods with required ids. These
	 * situations see {@link call(String path, REST restCall, String json, int
	 * id, int sId)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall)
			throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONArray(path, restCall, "", 0,
				0, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(
					"JSONObject instead of JSONArray", "JSONObject",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONArray", "String", serverResponse);
		}
	}

	/**
	 * The normal call type for creation. Does not have ids or fedid, ids are 1
	 * for default, fedid is simply "fedid", if they are needed use {@link
	 * call(String path, REST restCall, String json, int id, int sId)} or
	 * {@link call(String path, REST restCall, String json, int id, int sId,
	 * String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json) throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONArray(path, restCall, json,
				0, 0, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(
					"JSONObject instead of JSONArray", "JSONObject",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONArray", "String", serverResponse);
		}
	}

	/**
	 * The normal call type, use this for get calls with 1 required id. Does not
	 * have a fedid, if fedid is required use {@link call(String path, REST
	 * restCall, String json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall, int id)
			throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONArray(path, restCall, "", id,
				0, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(
					"JSONObject instead of JSONArray", "JSONObject",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONArray", "String", serverResponse);
		}
	}

	/**
	 * The normal call type, use this for get calls with 2 required id. Does not
	 * have a fedid, if fedid is required use {@link call(String path, REST
	 * restCall, String json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall, int id,
			int sId) throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONArray(path, restCall, "", id,
				sId, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(
					"JSONObject instead of JSONArray", "JSONObject",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONArray", "String", serverResponse);
		}
	}

	/**
	 * The normal call type, use this for most calls. Does not have a fedid, if
	 * fedid is required use {@link call(String path, REST restCall, String
	 * json, int id, int sId, String fedid)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json, int id, int sId) throws ResponseTypeMismatchException {
		Object tempResponse = this.getResponseJSONArray(path, restCall, json,
				id, sId, "fedid");

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(
					"JSONObject instead of JSONArray", "JSONObject",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONArray", "String", serverResponse);
		}
	}

	/**
	 * Call with fedid provided. Use this only if fedid is necessary, otherwise
	 * see {@link call(String path, REST restCall, String json, int id, int
	 * sId)} and {@link String call(String path, REST restCall)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @param fedid
	 *            String, a special id used only in
	 *            /api/principals/{fedid}/fedid
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json, int id, int sId, String fedid)
			throws ResponseTypeMismatchException {
		this.setMaster(path, restCall, json, id, sId, fedid);

		Object tempResponse = this.callSwitch(restCall);

		Object serverResponse;
		try {
			serverResponse = JSONParser.parseJSON((String) tempResponse);
		} catch (JSONException e) {
			throw new ResponseTypeMismatchException("Non json string",
					"String", tempResponse.toString());
		}

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(
					"JSONObject instead of JSONArray", "JSONObject",
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(
					"Non json instead of JSONArray", "String", serverResponse);
		}
	}

	/* *** HTTP handlers *** */
	/**
	 * Calls the appropriate http handler. Used by all call types.
	 *
	 * @param restCall
	 *            REST, decides between the 4 normal REST call.
	 * @return String, returns the response's content in string format.
	 */
	protected String callSwitch(REST restCall) {
		CoverageChecker.checkout(restCall + " " + path + " ");

		// Resets the local variables in case the class is used in a static
		// instance
		statusLine = "";
		headers = null;

		// Decides which method should be used
		switch (restCall) {
		case GET:
			return this.setResponse(this.get(buildHexaaURI(this
					.fixPath(this.path))));
		case POST:
			return this.setResponse(this.post(buildHexaaURI(this
					.fixPath(this.path))));
		case PUT:
			return this.setResponse(this.put(buildHexaaURI(this
					.fixPath(this.path))));
		case DELETE:
			return this.setResponse(this.delete(buildHexaaURI(this
					.fixPath(this.path))));
		case PATCH:
			return this.setResponse(this.patch(buildHexaaURI(this
					.fixPath(this.path))));
		}

		return "Could not call";
	}

	/**
	 * Returns the GET request's response's JSON content in string format, if
	 * there is no content empty string will be returned.
	 *
	 * @return String, JSON content in string format, maybe empty, never null.
	 */
	private String get(URI uri) {

		System.out.print("GET \t");
		System.out.print(uri);

		// Getting the response from the server, this is
		// wrapped in the javahttputility.core package
		HttpGet httpAction = new HttpGet(uri);
		httpAction = (HttpGet) createAction(httpAction, uri);

		CloseableHttpResponse httpResponse = execute(httpAction);

		if (httpResponse != null) {
			return getContentString(httpResponse);
		}
		statusLine = "Request failed";
		return "Request failed";

	}

	/**
	 * Uses the supplied JSON for the POST request and returns the response's
	 * JSON content in string format, if there is no content empty string will
	 * be returned.
	 *
	 * @return String, JSON content in string format, maybe empty, never null.
	 */
	private String post(URI uri) {

		System.out.print("POST \t");
		System.out.print(uri);

		// Getting the response from the server, this is
		// wrapped in the javahttputility.core package
		HttpPost httpAction = new HttpPost(uri);
		httpAction = (HttpPost) createAction(httpAction, uri);
		if (json != null) {
			httpAction.setEntity(createEntity(this.json));
		}

		CloseableHttpResponse httpResponse = execute(httpAction);

		if (httpResponse != null) {
			return getContentString(httpResponse);
		}
		statusLine = "Request failed";
		return "Request failed";
	}

	/**
	 * Uses the supplied JSON for the PUT request and returns the response's
	 * JSON content in string format, if there is no content empty string will
	 * be returned.
	 *
	 * @return String, JSON content in string format, maybe empty, never null.
	 */
	private String put(URI uri) {

		System.out.print("PUT \t");
		System.out.print(uri);

		// Getting the response from the server, this is
		// wrapped in the javahttputility.core package
		HttpPut httpAction = new HttpPut(uri);
		httpAction = (HttpPut) createAction(httpAction, uri);
		if (json != null) {
			httpAction.setEntity(createEntity(this.json));
		}

		CloseableHttpResponse httpResponse = execute(httpAction);

		if (httpResponse != null) {
			return getContentString(httpResponse);
		}
		statusLine = "Request failed";
		return "Request failed";
	}

	/**
	 * Returns the DELETE request's response's JSON content in string format, if
	 * there is no content empty string will be returned.
	 *
	 * @return String, JSON content in string format, maybe empty, never null.
	 */
	private String delete(URI uri) {

		System.out.print("DELETE \t");
		System.out.print(uri);

		// Getting the response from the server, this is
		// wrapped in the javahttputility.core package
		HttpDelete httpAction = new HttpDelete(uri);
		httpAction = (HttpDelete) createAction(httpAction, uri);

		CloseableHttpResponse httpResponse = execute(httpAction);

		if (httpResponse != null) {
			return getContentString(httpResponse);
		}
		statusLine = "Request failed";
		return "Request failed";
	}

	/**
	 * Uses the supplied JSON for the PATCH request and returns the response's
	 * JSON content in string format, if there is no content empty string will
	 * be returned.
	 *
	 * @return String, JSON content in string format, maybe empty, never null.
	 */
	private String patch(URI uri) {

		System.out.print("PATCH \t");
		System.out.print(uri);

		// Getting the response from the server, this is
		// wrapped in the javahttputility.core package
		HttpPatch httpAction = new HttpPatch(uri);
		httpAction = (HttpPatch) createAction(httpAction, uri);
		if (json != null) {
			httpAction.setEntity(createEntity(this.json));
		}

		CloseableHttpResponse httpResponse = execute(httpAction);

		if (httpResponse != null) {
			return getContentString(httpResponse);
		}
		statusLine = "Request failed";
		return "Request failed";
	}

	/* *** Utility methods *** */
	/**
	 * In the constans values of paths (found in {@link Const.Api}) the uris
	 * only contains {id}/{sid}/etc tags, not the actual ids (as these are only
	 * constans values), so the actual ids are replaced in the original string
	 * here, and concats .json at the end as well.
	 *
	 * @return
	 */
	private String fixPath(String path) {
		if (path.startsWith("/api")) {
			path = "/app.php".concat(path);
		}
		if (path.contains("{id}")) {
			path = path.replace("{id}", Integer.toString(this.id));
		}
		if (path.contains("{pid}")) {
			path = path.replace("{pid}", Integer.toString(this.sId));
		}
		if (path.contains("{sid}")) {
			path = path.replace("{sid}", Integer.toString(this.sId));
		}
		if (path.contains("{asid}")) {
			path = path.replace("{asid}", Integer.toString(this.sId));
		}
		if (path.contains("{eid}")) {
			path = path.replace("{eid}", Integer.toString(this.sId));
		}
		if (path.contains("{epid}")) {
			path = path.replace("{epid}", Integer.toString(this.sId));
		}
		if (path.contains("{fedid}")) {
			path = path.replace("{fedid}", fedid);
		}
		if (path.contains("{token}")) {
			path = path.replace("{token}", token);
		}

		if (path.contains(".{_format}")) {
			path = path.replace(".{_format}", this.format);
		}

		return path;
	}

	/**
	 * Consumes the response and returns the content in a non-parsed String.
	 *
	 * @param response
	 *            CloseableHttpResponse
	 * @return String:response.getEntity().getContent() in a non-parsed String,
	 *         if response (or the entity or the content) is null than the
	 *         returned String is empty (but not null)
	 */
	private String getContentString(CloseableHttpResponse response) {
		String responseDataString;

		Instant instant = Instant.now();
		System.out.print("\t" + instant.toString() + "\t");

		try {
			statusLine = response.getStatusLine().toString();
			headers = response.getAllHeaders();
		} catch (NullPointerException | IllegalStateException ex) {
			statusLine = "";
			System.out.println("  *  ");
			return "";
		}
		try {
			responseDataString = readContent(response.getEntity().getContent());
		} catch (NullPointerException | IOException | IllegalStateException ex) {
			System.out.println("  *  " + statusLine + "  *  ");
			return "";
		}

		if (responseDataString.equalsIgnoreCase("null")) {
			System.out.println("  *  " + statusLine + "  *  ");
			return "";
		}
		if (responseDataString.contains("503 Service Unavailable")) {
			System.out.println("  *  " + statusLine + "  *  "
					+ responseDataString);
			return responseDataString;
		}

		Object parsedResponse;
		try {
			parsedResponse = JSONParser.parseJSON(responseDataString);
			parsedResponse = recursiveJSONManipulator(parsedResponse);
		} catch (JSONException e) {
			System.out.println("  *  " + statusLine + "  *   "
					+ responseDataString);
			return responseDataString;
		}

		responseDataString = parsedResponse.toString();

		System.out
				.println("  *  " + statusLine + "  *   " + responseDataString);
		return responseDataString;
	}

	/**
	 * Reads a custom InputStream into a String. Using scanners.
	 * 
	 * @param content
	 *            InputStream to read.
	 * @return String representation of the stream.
	 */
	private String readContent(InputStream content) {
		java.util.Scanner s = new java.util.Scanner(content);
		java.util.Scanner scanner = s.useDelimiter("\\A");
		String data = scanner.hasNext() ? scanner.next() : "";
		s.close();

		return data;
	}

	/**
	 * A recursive method that iterates through any json object and executes the
	 * proper functions. Does not change the input object, returns a new one.
	 * Function: If any "id" key has a respected value of String it changes the
	 * value to int. Function: removes the updated_at keys as these are
	 * irrelevant for the test cases and causing problems.
	 * 
	 * @param object
	 *            a json object or array, remains unchanged, return non json
	 *            objects without any changes.
	 * @return a new json object containing mostly the same information as the
	 *         original, only changes done by the functions.
	 */
	private Object recursiveJSONManipulator(Object object) {
		if (object instanceof JSONArray) {
			JSONArray json = (JSONArray) object;
			JSONArray temp = new JSONArray();
			for (int i = 0; i < json.length(); i++) {
				// Recursion
				temp.put(recursiveJSONManipulator(json.get(i)));
			}
			return temp;
		}
		if (object instanceof JSONObject) {
			JSONObject json = (JSONObject) object;
			JSONObject temp = new JSONObject();
			if (JSONObject.getNames(json) == null) {
				return object;
			}
			for (String s : JSONObject.getNames(json)) {
				if (json.get(s) instanceof JSONObject) {
					// Recursion
					temp.put(s, recursiveJSONManipulator(json.getJSONObject(s)));
				} else if (json.get(s) instanceof JSONArray) {
					// Recursion
					temp.put(s, recursiveJSONManipulator(json.getJSONArray(s)));
				} else if (s.equals("id") && json.get(s) instanceof String) {
					// Function: If any "id" key has a respected value of String
					// it changes the value to int
					temp.put(s, Integer.parseInt(json.getString(s)));
				} else if (s.equals("updated_at")) {
					// Function: removes the updated_at keys as these are
					// irrelevant for the test cases and causing problems

					// if you want to change an object/array in a jsonobject add
					// it here in an else if, please document all changes in the
					// method description as well.
				} else {
					// Everything else remains unchanged
					temp.put(s, json.get(s));
				}
			}
			return temp;
		}
		return object;
	}

	/**
	 * Imported methods from the depecrated classes of the sztaki.hexaa.core
	 * package.
	 */

	/**
	 * Executes the PUT action on the path given in the constructor.
	 *
	 * @return returns a CloseableHttpResponse
	 */
	private CloseableHttpResponse execute(HttpMessage httpAction) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.custom()
				.disableRedirectHandling().build();

		try {
			response = httpClient.execute((HttpUriRequest) httpAction);
		} catch (IOException ex) {
		}

		return response;
	}

	/**
	 * Builds the proper uri for the call.
	 * 
	 * @param path
	 *            path of the required uri
	 * @return the full uri.
	 */
	private URI buildHexaaURI(String path) {
		URI uri = null;
		try {
			if (isAdmin && isOffset && isLimit) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path).addParameter("admin", "true")
						.addParameter("offset", Integer.toString(this.offset))
						.addParameter("limit", Integer.toString(this.limit))
						.build();
			} else if (isAdmin && isOffset) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path).addParameter("admin", "true")
						.addParameter("offset", Integer.toString(this.offset))
						.build();
			} else if (isAdmin && isLimit) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path).addParameter("admin", "true")
						.addParameter("limit", Integer.toString(this.limit))
						.build();
			} else if (isOffset && isLimit) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path)
						.addParameter("offset", Integer.toString(this.offset))
						.addParameter("limit", Integer.toString(this.limit))
						.build();
			} else if (isAdmin) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path).addParameter("admin", "true").build();
			} else if (isOffset) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path)
						.addParameter("offset", Integer.toString(this.offset))
						.build();
			} else if (isLimit) {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path)
						.addParameter("limit", Integer.toString(this.limit))
						.build();
			} else {
				uri = new URIBuilder().setScheme(Const.HEXAA_SCHEME)
						.setHost(Const.HEXAA_HOST).setPort(Const.HEXAA_PORT)
						.setPath(path).build();
			}
		} catch (URISyntaxException ex) {
			System.out.println("Error in uri build: " + ex.getMessage());
		} finally {
			this.isAdmin = false;
			this.isOffset = false;
			this.isLimit = false;
		}
		return uri;
	}

	/**
	 * Sets the required headers of the action (X-HEXAA-AUTH nad Content-type).
	 *
	 * @param httpAction
	 *            a HttpMessage, will be returned with set headers.
	 * @param uri
	 *            the uri of the HttpMessage to be built with.
	 */
	private HttpMessage createAction(HttpMessage httpAction, URI uri) {
		if (uri != null) {
			Header hexaa_auth = new BasicHeader(Const.HEXAA_HEADER,
					Const.HEXAA_AUTH);
			httpAction.addHeader(hexaa_auth);
			httpAction.setHeader("Content-type", "application/json");
			httpAction.setHeader("Accept", "application/json");
		}
		return httpAction;
	}

	/**
	 * Creates a BasicHttpEntity from the json payload, with utf-8 encoding.
	 * 
	 * @param json
	 *            payload for the HttpMessages.
	 * @return BasicHttpEntity from the json input string.
	 */
	private BasicHttpEntity createEntity(String json) {
		if (json == null) {
			json = new String();
		}

		BasicHttpEntity entity = new BasicHttpEntity();
		entity.setContent(new ByteArrayInputStream(json
				.getBytes(StandardCharsets.UTF_8)));
		entity.setContentLength(json.length());

		return entity;
	}
}
