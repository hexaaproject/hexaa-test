package sztaki.hexaa;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONCall extends BasicCall {
	/* *** Methods for JSONObject handling *** */
	/**
	 * Check the provided Object to be a JSONObject. Always returns a
	 * JSONObject, if the serverResponse parameter is not a JSONObject than a
	 * ResponseTypeMismatchException is thrown with appropriate message.
	 * 
	 * @param serverResponse
	 *            Object expected to be a JSONObject.
	 * @return JSONObject representing the serverResponse if it is a JSONObject.
	 * @throws ResponseTypeMismatchException
	 *             thrown if the serverResponse is not a JSONObject.
	 */
	private JSONObject checkJSONObject(Object serverResponse)
			throws ResponseTypeMismatchException {

		if (serverResponse instanceof JSONObject) {
			return (JSONObject) serverResponse;
		} else if (serverResponse instanceof JSONArray) {
			throw new ResponseTypeMismatchException(JSONObject.class,
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(JSONObject.class,
					serverResponse);
		}
	}

	/**
	 * Returns the string representation of the call's response. Matches the
	 * return value of the respective call() method.
	 *
	 * @return JSONObject representation of the call's response
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject()
			throws ResponseTypeMismatchException {
		return checkJSONObject(this.response);
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall)
			throws ResponseTypeMismatchException {
		return this.getResponseJSONObject(path, restCall, "", 0, 0, "fedid");
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json) throws ResponseTypeMismatchException {
		return this.getResponseJSONObject(path, restCall, json, 0, 0, "fedid");
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall, int id)
			throws ResponseTypeMismatchException {
		return this.getResponseJSONObject(path, restCall, "", id, 0, "fedid");
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json, int id) throws ResponseTypeMismatchException {
		return this.getResponseJSONObject(path, restCall, json, id, 0, "fedid");
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall, int id,
			int sId) throws ResponseTypeMismatchException {
		return this.getResponseJSONObject(path, restCall, "", id, sId, "fedid");
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json, int id, int sId) throws ResponseTypeMismatchException {
		return this.getResponseJSONObject(path, restCall, json, id, sId,
				"fedid");
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
	 * @return JSONObject, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONObject getResponseJSONObject(String path, REST restCall,
			String json, int id, int sId, String fedid)
			throws ResponseTypeMismatchException {
		this.setMaster(path, json, id, sId, fedid);

		return checkJSONObject(this.callSwitch(restCall));
	}

	/* *** Methods for JSONArray handling *** */
	/**
	 * Check the provided Object to be a JSONArray. Always returns a JSONArray,
	 * if the serverResponse parameter is not a JSONArray than a
	 * ResponseTypeMismatchException is thrown with appropriate message.
	 * 
	 * @param serverResponse
	 *            Object expected to be a JSONArray.
	 * @return JSONArray representing the serverResponse if it is a JSONArray.
	 * @throws ResponseTypeMismatchException
	 *             thrown if the serverResponse is not a JSONArray.
	 */
	private JSONArray checkJSONArray(Object serverResponse)
			throws ResponseTypeMismatchException {

		if (serverResponse instanceof JSONArray) {
			return (JSONArray) serverResponse;
		} else if (serverResponse instanceof JSONObject) {
			throw new ResponseTypeMismatchException(JSONArray.class,
					serverResponse);
		} else {
			throw new ResponseTypeMismatchException(JSONArray.class,
					serverResponse);
		}
	}

	/**
	 * Returns the JSONArray representation of the call's response. Matches the
	 * return value of the respective call() method.
	 *
	 * @return JSONArray representation of the call's response
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray()
			throws ResponseTypeMismatchException {
		return this.checkJSONArray(this.response);
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall)
			throws ResponseTypeMismatchException {
		return this.getResponseJSONArray(path, restCall, "", 0, 0, "fedid");
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json) throws ResponseTypeMismatchException {
		return this.getResponseJSONArray(path, restCall, json, 0, 0, "fedid");
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall, int id)
			throws ResponseTypeMismatchException {
		return this.getResponseJSONArray(path, restCall, "", id, 0, "fedid");
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json, int id) throws ResponseTypeMismatchException {
		return this.getResponseJSONArray(path, restCall, json, id, 0, "fedid");
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall, int id,
			int sId) throws ResponseTypeMismatchException {
		return this.getResponseJSONArray(path, restCall, "", id, sId, "fedid");
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json, int id, int sId) throws ResponseTypeMismatchException {
		return this
				.getResponseJSONArray(path, restCall, json, id, sId, "fedid");
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
	 * @return JSONArray, the content of the response for the call, for the
	 *         Status Line/Code see {@link getStatusLine()}.
	 * @throws sztaki.hexaa.ResponseTypeMismatchException
	 */
	public JSONArray getResponseJSONArray(String path, REST restCall,
			String json, int id, int sId, String fedid)
			throws ResponseTypeMismatchException {
		this.setMaster(path, json, id, sId, fedid);

		return this.checkJSONArray(this.callSwitch(restCall));
	}

}
