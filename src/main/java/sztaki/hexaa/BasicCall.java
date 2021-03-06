package sztaki.hexaa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
 * Support class that implements the 5 API calls, and provides further
 * functionality for them.
 */
public class BasicCall {

	/* *** Setter/getter methods and variables *** */
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
	 * The format that will concated to the end of the call url. By default it
	 * is ".json". Currently not modifiable exists for the sake of compatibility
	 * for later changes.
	 */
	private String format = ".json";

	/**
	 * Usable by call methods to set all the required parameters in one go.
	 * 
	 * @param path
	 *            The relative path of the URI.
	 * @param json
	 *            The JSON part of the message that will be inserted as an
	 *            entity to the http requests body.
	 * @param id
	 *            The requested ID, always inserted into the {id} part of the
	 *            url.
	 * @param sId
	 *            The possible special ID, inserted in one of the possible
	 *            formats: {sid}, {asid}, {pid}, {eid}, {epid}.
	 * @param fedid
	 *            The fedid to insert into certain url-s.
	 */
	protected void setMaster(String path, String json, int id, int sId,
			String fedid) {
		this.setPath(path);
		this.setJson(json);
		this.setId(id);
		this.setSId(sId);
		this.setFedid(fedid);
	}

	/**
	 * The relative path of the URI. Should be in /app.php/api/example or
	 * /api/example.{_format} format.
	 */
	private String path = null;

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            String, the path to set.
	 */
	protected void setPath(String path) {
		this.path = path;
	}

	/**
	 * The requested ID, always inserted into the {id} part of the url.
	 */
	private int id = 0;

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            int, the id to set, used to replace the {id} parts of the uri.
	 */
	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * The possible special ID, inserted in one of the possible formats: {sid},
	 * {asid}, {pid}, {eid}, {epid}.
	 */
	private int sId = 0;

	/**
	 * Sets the special id.
	 *
	 * @param sId
	 *            int, the special id to set, used to replace the {sid}, {asid},
	 *            {pid}, {eid} or {epid} parts of the uri.
	 */
	protected void setSId(int sId) {
		this.sId = sId;
	}

	/**
	 * The JSON part of the message that will be inserted as an entity to the
	 * http requests body.
	 */
	private String json = "";

	/**
	 * Sets the JSON string.
	 *
	 * @param json
	 *            String, the string representation of the json data as the
	 *            payload of the html request.
	 */
	protected void setJson(String json) {
		if (json == null) {
			json = new String();
		}
		this.json = json;
	}

	/**
	 * The fedid to insert into certain url-s.
	 */
	private String fedid = "fedid";

	/**
	 * Sets the fedid.
	 *
	 * @param fedid
	 *            String, the fedid to replace the {fedid} part of the uri.
	 */
	protected void setFedid(String fedid) {
		this.fedid = fedid;
	}

	/**
	 * The token required by a few calls in the url.
	 */
	private String token = "token";

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            String, the token to replace the {token} part of the uri.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * The email required by a few calls in the url.
	 */
	@SuppressWarnings("unused")
	private String email = "email";

	/**
	 * Sets the token.
	 *
	 * @param email
	 *            String, the email to replace the {email} part of the uri.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * The StatusLine of the last call, never null, maybe empty before any call
	 * was made.
	 */
	private String statusLine = "";

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
	 * The headers of the last call, null before any call were made and can be
	 * null if the call request fails.
	 */
	private Header[] headers = null;

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
	 * The servers last response. It can be string, jsonobject or jsonarray. Can
	 * be null only before any call happened. Can be empty string.
	 */
	protected Object response = null;

	/**
	 * Parses the parameter string to jsonobject, jsonarray or string, and
	 * stores it as the response Object, returns this object as well. Calls the
	 * {@link #recursiveJSONManipulator(Object object)} to execute the proper
	 * functions on the response data.
	 * 
	 * @param responseDataString
	 *            String to parse and store it as the response Object.
	 * @return Object, returns the parsed object.
	 */
	protected Object setResponse(String responseDataString) {
		Object parsedResponse;
		try {
			parsedResponse = JSONParser.parseJSON(responseDataString);
			parsedResponse = recursiveJSONManipulator(parsedResponse);
		} catch (JSONException e) {
			this.response = responseDataString;
			return responseDataString;
		}
		this.response = parsedResponse;
		return parsedResponse;
	}

	/**
	 * The api key required by the server to authenticate. The
	 * BasicCall.authenticate() method updates it.
	 */
	private static String HEXAA_AUTH = "";
	/**
	 * Integer, containing the numerical id of the current principal_self. The
	 * BasicCall.authenticate() method updates it.
	 */
	public static int HEXAA_ID = 0;

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
	 * Returns the last calls server response in the form of a String. Always a
	 * string, can be "null" string.
	 * 
	 * @return the String representation of the BasicCall.response.
	 */
	public String call() {
		return String.valueOf(this.response);
	}

	/**
	 * Most basic call type, only use it for simple GET methods. Does not have a
	 * fedid, json, id or sId, uses the default values: fedid - "fedid", json -
	 * empty json, id - 0, sId - 0; with the method
	 * {@link #call(String path, REST restCall, String json, int id, int sId, String fedid)}
	 * .
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall) {
		return call(path, restCall, "", 0, 0, "fedid");
	}

	/**
	 * The normal call type for creation. Does not have a fedid, id or sId, uses
	 * the default values: fedid - "fedid", id - 0, sId - 0; with the method
	 * {@link #call(String path, REST restCall, String json, int id, int sId, String fedid)}
	 * .
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json) {
		return call(path, restCall, json, 0, 0, "fedid");
	}

	/**
	 * The normal call type, use this for get calls with 1 required id. Does not
	 * have a fedid, json or sId, uses the default values: fedid - "fedid", json
	 * - empty json, sId - 0; with the method
	 * {@link #call(String path, REST restCall, String json, int id, int sId, String fedid)}
	 * .
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall, int id) {
		return call(path, restCall, "", id, 0, "fedid");
	}

	/**
	 * The normal call type, use this for most calls. Does not have a fedid,
	 * uses the default values: fedid - "fedid"; with the method
	 * {@link #call(String path, REST restCall, String json, int id, int sId, String fedid)}
	 * .
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json, int id) {
		return call(path, restCall, json, id, 0, "fedid");
	}

	/**
	 * The normal call type, use this for get calls with 2 required id. Does not
	 * have a fedid or json payload, uses the default values: fedid - "fedid",
	 * json - empty json; with the method
	 * {@link #call(String path, REST restCall, String json, int id, int sId, String fedid)}
	 * .
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall, int id, int sId) {
		return call(path, restCall, "", id, sId, "fedid");
	}

	/**
	 * The normal call type, use this for most calls. Does not have a fedid,
	 * uses the default values: fedid - "fedid"; with the method
	 * {@link #call(String path, REST restCall, String json, int id, int sId, String fedid)}
	 * .
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
	 * @param json
	 *            String, the json message for the http request's body in string
	 *            format.
	 * @param id
	 *            int, the basic {id} in the urls.
	 * @param sId
	 *            int, all the ids in the url other than {id} and {fedid}.
	 * @return String, the content of the response for the call, for the Status
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json, int id, int sId) {
		return call(path, restCall, json, id, sId, "fedid");
	}

	/**
	 * Call with fedid provided. Use this only if fedid is necessary, otherwise
	 * see {@link #call(String path, REST restCall, String json, int id, int sId)}
	 * and {@link #call(String path, REST restCall)}.
	 *
	 * @param path
	 *            String, the relative path from the host.
	 * @param restCall
	 *            REST, the type of the call (GET,POST,PUT,PATCH,DELETE).
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
	 *         Line/Code see {@link #getStatusLine()}.
	 */
	public String call(String path, REST restCall, String json, int id,
			int sId, String fedid) {
		this.setMaster(path, json, id, sId, fedid);

		Object serverResponse = this.callSwitch(restCall);

		return String.valueOf(serverResponse);
	}

	/* *** HTTP handlers *** */
	/**
	 * Calls the appropriate http handler. Also interacts with the
	 * CoverageChecker, resets the statusLine and headers before the call is
	 * made, updates the response as well.
	 *
	 * @param restCall
	 *            REST, decides between the 5 normal REST call.
	 * @return Object, can be JSONObject, JSONArray or String, can not be null,
	 *         can be empty string.
	 */
	protected Object callSwitch(REST restCall) {
		if (new DataProp().getString("coverage_checker").equals("true")) {
			CoverageChecker.checkout(restCall + " " + path + " ");
		}

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
	 * Returns the GET request's response. The response is already parsed, it's
	 * either a JSONObject, a JSONArray or a String if some error occured.
	 * 
	 * @param uri
	 *            URI object, full uri created with uri builder.
	 *
	 * @return Object, can be JSONObject, JSONArray or String, can not be null,
	 *         can be empty string.
	 */
	private String get(URI uri) {

		System.out.print("GET \t");
		System.out.print(uri);

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
	 * Returns the POST request's response. The response is already parsed, it's
	 * either a JSONObject, a JSONArray or a String if some error occured.
	 * 
	 * @param uri
	 *            URI object, full uri created with uri builder.
	 *
	 * @return Object, can be JSONObject, JSONArray or String, can not be null,
	 *         can be empty string.
	 */
	private String post(URI uri) {

		System.out.print("POST \t");
		System.out.print(uri);

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
	 * Returns the PUT request's response. The response is already parsed, it's
	 * either a JSONObject, a JSONArray or a String if some error occured.
	 * 
	 * @param uri
	 *            URI object, full uri created with uri builder.
	 *
	 * @return Object, can be JSONObject, JSONArray or String, can not be null,
	 *         can be empty string.
	 */
	private String put(URI uri) {

		System.out.print("PUT \t");
		System.out.print(uri);

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
	 * Returns the DELETE request's response. The response is already parsed,
	 * it's either a JSONObject, a JSONArray or a String if some error occured.
	 * 
	 * @param uri
	 *            URI object, full uri created with uri builder.
	 *
	 * @return Object, can be JSONObject, JSONArray or String, can not be null,
	 *         can be empty string.
	 */
	private String delete(URI uri) {

		System.out.print("DELETE \t");
		System.out.print(uri);

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
	 * Returns the PATCH request's response. The response is already parsed,
	 * it's either a JSONObject, a JSONArray or a String if some error occured.
	 * 
	 * @param uri
	 *            URI object, full uri created with uri builder.
	 *
	 * @return Object, can be JSONObject, JSONArray or String, can not be null,
	 *         can be empty string.
	 */
	private String patch(URI uri) {

		System.out.print("PATCH \t");
		System.out.print(uri);

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
	 * Replaces the dummy data in the uri strings.
	 * 
	 * @param path
	 *            String, the uri with dummy data. If there is no dummy data in
	 *            the uri ({id},{token},etc) it is returned unchanged.
	 *
	 * @return String, uri with proper data.
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
		if (!path.endsWith(this.format)) {
			path = path.concat(this.format);
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

		System.out.println("  *  " + statusLine + "  *   "
				+ responseDataString.toString());
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
	 *            a json object or array, remains unchanged, returns non json
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

	/* *** HTTP Utility methods *** */
	/**
	 * Executes the provided action.
	 * 
	 * @param httpAction
	 *            HttpMessage , a message that will be executed on a
	 *            CloseableHttpClient.
	 *
	 * @return CloseableHttpResponse, the response of the executed httpAction.
	 */
	private CloseableHttpResponse execute(HttpMessage httpAction) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.custom()
				.disableRedirectHandling().build();

		try {
			response = httpClient.execute((HttpUriRequest) httpAction);
		} catch (IOException ex) {
			System.err.println("Unable to execute HTTP action: "
					+ ex.getMessage());
		}

		return response;
	}

	/**
	 * Builds the proper uri for the call. This method also adds the required
	 * parameters if they are enabled (admin, limit, offset).
	 * 
	 * @param path
	 *            path of the required uri
	 * @return the full uri.
	 */
	private URI buildHexaaURI(String path) {
		URI uri = null;
		// Creates the basic uri.
		URIBuilder builder = new URIBuilder()
				.setScheme(new DataProp().getString("HEXAA_SCHEME"))
				.setHost(new DataProp().getString("HEXAA_HOST"))
				.setPort(new DataProp().getInt("HEXAA_PORT")).setPath(path);
		// Adds specific parameters.
		if (isAdmin) {
			builder.addParameter("admin", "true");
		}
		if (isOffset) {
			builder.addParameter("offset", Integer.toString(this.offset));
		}
		if (isLimit) {
			builder.addParameter("limit", Integer.toString(this.limit));
		}
		// Add additional parameters here and document it in the method
		// description as well.
		try {
			uri = builder.build();
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
	 * 
	 * @return HttpMessage, builds the httpAction properly.
	 */
	private HttpMessage createAction(HttpMessage httpAction, URI uri) {
		if (uri != null) {
			Header hexaa_auth = new BasicHeader(
					new DataProp().getString("HEXAA_HEADER"),
					BasicCall.HEXAA_AUTH);
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

	/* *** Authentication methods *** */
	/**
	 * Alternative call for {@link #authenticate(String fedid, String secret)}
	 * for non differentiated master_secret authentications and legacy purposes.
	 * Always uses the default mastersecret that can be reached with
	 * DataProp().getString("MASTER_SECRET").
	 * 
	 * @param fedid
	 *            the fedid to authenticate with, the default hexaa_test admin
	 *            fedid can be reached by DataProp().getString("HEXAA_FEDID"),
	 *            if not use a valid e-mail format.
	 * 
	 * @return int, returns 1 in case of error, 0 otherwise.
	 */
	public int authenticate(String fedid) {
		return this.authenticate(fedid,
				new DataProp().getString("MASTER_SECRET"));
	}

	/**
	 * Checks if the session is authenticated or not, and authenticates if
	 * necessary. Gets a short time limited API key and uses the /api/token GET
	 * method to get the usual 1 hour limited API key. The given fedid has to be
	 * in a valid e-mail format (some@thing.example).
	 *
	 * @param fedid
	 *            the fedid to authenticate with, the default hexaa_test admin
	 *            fedid can be reached by DataProp().getString("HEXAA_FEDID"),
	 *            if not use a valid e-mail format.
	 * 
	 * @param secret
	 *            String, the master secret to authenticate with.
	 * 
	 * @return int, returns 1 in case of error, 0 otherwise.
	 */
	public int authenticate(String fedid, String secret) {

		System.out.print("** AUTHENTICATE **\t");
		String response = this.call(Const.Api.PRINCIPAL_SELF, REST.GET);

		if (!response.contains(fedid)) {

			JSONCall postToken = new JSONCall();

			JSONObject json = new JSONObject();
			json.put("fedid", fedid);
			json.put("apikey", this.getAPIKey(secret));
			json.put("email", fedid);
			json.put("display_name", fedid + "_name");

			JSONObject jsonResponse;
			try {
				System.out.print("** AUTHENTICATE **\t");
				jsonResponse = postToken.getResponseJSONObject(
						Const.Api.TOKENS, BasicCall.REST.POST, json.toString());
			} catch (ResponseTypeMismatchException ex) {
				System.err
						.println("Unable to authenticate, please make sure that the server is reachable, and config.properties is correct.");
				return 1;
			}

			System.out.print("** AUTHENTICATE **\t");
			System.out.println(jsonResponse.toString());

			if (jsonResponse.has("token")) {
				BasicCall.HEXAA_AUTH = jsonResponse.get("token").toString();
			} else {
				System.err.println("Unable to authenticate. TempToken: "
						+ json.get("apikey"));
				return 1;
			}

			JSONObject principalSelf;
			try {
				System.out.print("** AUTHENTICATE **\t");
				principalSelf = postToken.getResponseJSONObject(
						Const.Api.PRINCIPAL_SELF, REST.GET);
				BasicCall.HEXAA_ID = principalSelf.getInt("id");
			} catch (ResponseTypeMismatchException | JSONException ex) {
				System.err.println("Unable to find principal: "
						+ ex.getMessage());
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Provides the necessary data and hashing for the temporal API key.
	 * 
	 * @param secret
	 *            String, the master secret to authenticate with.
	 *
	 * @return String, temporal API key for limited time of authentication.
	 */
	public String getAPIKey(String secret) {
		String timestamp = null;
		String sha256hex;

		// Set a ZoneId so we can get zone specific time, in this case "UTC"
		ZoneId id = ZoneId.of("UTC");
		LocalDateTime date = LocalDateTime.now(id);
		try {
			// Format the date to the required pattern
			DateTimeFormatter format = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm");
			timestamp = date.format(format);
		} catch (DateTimeException exc) {
			throw exc;
		}

		secret = secret.concat(timestamp);

		sha256hex = org.apache.commons.codec.digest.DigestUtils
				.sha256Hex(secret);
		if (sha256hex == null) {
			System.exit(0);
		}

		return sha256hex;
	}

}
