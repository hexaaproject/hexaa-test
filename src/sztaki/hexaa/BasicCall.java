package sztaki.hexaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import sztaki.hexaa.core.HttpCoreDel;
import sztaki.hexaa.core.HttpCoreGet;
import sztaki.hexaa.core.HttpCorePost;
import sztaki.hexaa.core.HttpCorePut;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.core.HttpCorePatch;

/**
 * Support class that implements the 4 RESTful API calls. Can easily be expanded
 * if needed. The call methods with no String path parameters are Deprecated and
 * should NOT be used anymore, use the call methods WITH String path parameters.
 * Calling the child classes are not supported anymore.
 */
public class BasicCall {

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
     * empty.
     */
    public String getStatusLine() {
        return statusLine;
    }

    /**
     * Returns the header with the specified name if exists, null if not.
     *
     * @param name the name of the header.
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
     * @param f string, format type of the calls, can be ".json" or ""(empty
     * string).
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
    public JSONObject getResponseJSONObject() throws ResponseTypeMismatchException {
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) response);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", response.toString());
        }

        if (serverResponse instanceof JSONObject) {
            return (JSONObject) serverResponse;
        } else if (serverResponse instanceof JSONArray) {
            throw new ResponseTypeMismatchException("JSONArray instead of JSONObject", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONObject", "String", serverResponse);
        }
    }

    /**
     * Returns the string representation of the call's response. Matches the
     * return value of the respective call() method.
     *
     * @return string representation of the call's response
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONArray getResponseJSONArray() throws ResponseTypeMismatchException {
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) response);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", response.toString());
        }

        if (serverResponse instanceof JSONObject) {
            return (JSONArray) serverResponse;
        } else if (serverResponse instanceof JSONArray) {
            throw new ResponseTypeMismatchException("JSONArray instead of JSONObject", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONObject", "String", serverResponse);
        }
    }

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
     * situations see
     * {@link call(String path, REST restCall, String json, int id, int sId)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     */
    public String call(String path, REST restCall) {
        this.setPath(path);
        this.setString(null);
        this.setId(1);
        this.setSId(1);

        return callSwitch(restCall);
    }

    /**
     * The normal call type for creation. Does not have ids or fedid, ids are 1
     * for default, fedid is simply "fedid", if they are needed use
     * {@link call(String path, REST restCall, String json, int id, int sId)} or
     * {@link call(String path, REST restCall, String json, int id, int sId, String fedid)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     */
    public String call(String path, REST restCall, String json) {
        this.setPath(path);
        this.setString(json);
        this.setId(1);
        this.setSId(1);

        return callSwitch(restCall);
    }

    /**
     * The normal call type, use this for most calls. Does not have a fedid, if
     * fedid is required use
     * {@link call(String path, REST restCall, String json, int id, int sId, String fedid)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @param id int, the basic {id} in the urls.
     * @param sId int, all the ids in the url other than {id} and {fedid}.
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     */
    public String call(String path, REST restCall, String json, int id, int sId) {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);

        return callSwitch(restCall);
    }

    /**
     * Call with fedid provided. Use this only if fedid is necessary, otherwise
     * see
     * {@link call(String path, REST restCall, String json, int id, int sId)}
     * and {@link String call(String path, REST restCall)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @param id int, the basic {id} in the urls.
     * @param sId int, all the ids in the url other than {id} and {fedid}.
     * @param fedid String, a special id used only in
     * /api/principals/{fedid}/fedid
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     */
    public String call(String path, REST restCall, String json, int id, int sId, String fedid) {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);
        this.setFedid(fedid);

        return callSwitch(restCall);
    }

    /* *** Calls to get JSONObjects and JSONArrays already parsed *** */
    /**
     * Most basic call type, only use it for simple GET methods, as it does not
     * get the required json/id/sid/fedid for most of the more complex calls
     * like any POST/PUT methods or GET/DELETE methods with required ids. These
     * situations see
     * {@link call(String path, REST restCall, String json, int id, int sId)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONObject getResponseJSONObject(String path, REST restCall) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(null);
        this.setId(1);
        this.setSId(1);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONObject) {
            return (JSONObject) serverResponse;
        } else if (serverResponse instanceof JSONArray) {
            throw new ResponseTypeMismatchException("JSONArray instead of JSONObject", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONObject", "String", serverResponse);
        }
    }

    /**
     * The normal call type for creation. Does not have ids or fedid, ids are 1
     * for default, fedid is simply "fedid", if they are needed use
     * {@link call(String path, REST restCall, String json, int id, int sId)} or
     * {@link call(String path, REST restCall, String json, int id, int sId, String fedid)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONObject getResponseJSONObject(String path, REST restCall, String json) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(json);
        this.setId(1);
        this.setSId(1);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONObject) {
            return (JSONObject) serverResponse;
        } else if (serverResponse instanceof JSONArray) {
            throw new ResponseTypeMismatchException("JSONArray instead of JSONObject", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONObject", "String", serverResponse);
        }
    }

    /**
     * The normal call type, use this for most calls. Does not have a fedid, if
     * fedid is required use
     * {@link call(String path, REST restCall, String json, int id, int sId, String fedid)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @param id int, the basic {id} in the urls.
     * @param sId int, all the ids in the url other than {id} and {fedid}.
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONObject getResponseJSONObject(String path, REST restCall, String json, int id, int sId) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONObject) {
            return (JSONObject) serverResponse;
        } else if (serverResponse instanceof JSONArray) {
            throw new ResponseTypeMismatchException("JSONArray instead of JSONObject", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONObject", "String", serverResponse);
        }
    }

    /**
     * Call with fedid provided. Use this only if fedid is necessary, otherwise
     * see
     * {@link call(String path, REST restCall, String json, int id, int sId)}
     * and {@link String call(String path, REST restCall)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @param id int, the basic {id} in the urls.
     * @param sId int, all the ids in the url other than {id} and {fedid}.
     * @param fedid String, a special id used only in
     * /api/principals/{fedid}/fedid
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONObject getResponseJSONObject(String path, REST restCall, String json, int id, int sId, String fedid) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);
        this.setFedid(fedid);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONObject) {
            return (JSONObject) serverResponse;
        } else if (serverResponse instanceof JSONArray) {
            throw new ResponseTypeMismatchException("JSONArray instead of JSONObject", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONObject", "String", serverResponse);
        }
    }

    /**
     * Most basic call type, only use it for simple GET methods, as it does not
     * get the required json/id/sid/fedid for most of the more complex calls
     * like any POST/PUT methods or GET/DELETE methods with required ids. These
     * situations see
     * {@link call(String path, REST restCall, String json, int id, int sId)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONArray getResponseJSONArray(String path, REST restCall) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(null);
        this.setId(1);
        this.setSId(1);
        //System.out.println(path);
        //System.out.println(this.path);

        Object tempResponse = callSwitch(restCall);

        //System.out.println(this.path);

        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONArray) {
            return (JSONArray) serverResponse;
        } else if (serverResponse instanceof JSONObject) {
            throw new ResponseTypeMismatchException("JSONObject instead of JSONArray", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONArray", "String", serverResponse);
        }
    }

    /**
     * The normal call type for creation. Does not have ids or fedid, ids are 1
     * for default, fedid is simply "fedid", if they are needed use
     * {@link call(String path, REST restCall, String json, int id, int sId)} or
     * {@link call(String path, REST restCall, String json, int id, int sId, String fedid)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONArray getResponseJSONArray(String path, REST restCall, String json) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(json);
        this.setId(1);
        this.setSId(1);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONArray) {
            return (JSONArray) serverResponse;
        } else if (serverResponse instanceof JSONObject) {
            throw new ResponseTypeMismatchException("JSONObject instead of JSONArray", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONArray", "String", serverResponse);
        }
    }

    /**
     * The normal call type, use this for most calls. Does not have a fedid, if
     * fedid is required use
     * {@link call(String path, REST restCall, String json, int id, int sId, String fedid)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @param id int, the basic {id} in the urls.
     * @param sId int, all the ids in the url other than {id} and {fedid}.
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONArray getResponseJSONArray(String path, REST restCall, String json, int id, int sId) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONArray) {
            return (JSONArray) serverResponse;
        } else if (serverResponse instanceof JSONObject) {
            throw new ResponseTypeMismatchException("JSONObject instead of JSONArray", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONArray", "String", serverResponse);
        }
    }

    /**
     * Call with fedid provided. Use this only if fedid is necessary, otherwise
     * see
     * {@link call(String path, REST restCall, String json, int id, int sId)}
     * and {@link String call(String path, REST restCall)}.
     *
     * @param path String, the relative path from the host.
     * @param restCall REST, the type of the call (GET,POST,PUT,DELETE).
     * @param json String, the json message for the http request's body in
     * string format.
     * @param id int, the basic {id} in the urls.
     * @param sId int, all the ids in the url other than {id} and {fedid}.
     * @param fedid String, a special id used only in
     * /api/principals/{fedid}/fedid
     * @return String, the content of the response for the call, for the Status
     * Line/Code see {@link getStatusLine()}.
     * @throws sztaki.hexaa.ResponseTypeMismatchException
     */
    public JSONArray getResponseJSONArray(String path, REST restCall, String json, int id, int sId, String fedid) throws ResponseTypeMismatchException {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);
        this.setFedid(fedid);

        Object tempResponse = callSwitch(restCall);
        Object serverResponse;
        try {
            serverResponse = JSONParser.parseJSON((String) tempResponse);
        } catch (JSONException e) {
            throw new ResponseTypeMismatchException("Non json string", "String", tempResponse.toString());
        }

        if (serverResponse instanceof JSONArray) {
            return (JSONArray) serverResponse;
        } else if (serverResponse instanceof JSONObject) {
            throw new ResponseTypeMismatchException("JSONObject instead of JSONArray", "JSONArray", serverResponse);
        } else {
            throw new ResponseTypeMismatchException("Non json instead of JSONArray", "String", serverResponse);
        }
    }

    /* *** Http handlers *** */
    /**
     * Calls the appropriate http handler. Used by all call types.
     *
     * @param restCall REST, decides between the 4 normal REST call.
     * @return String, returns the response's content in string format.
     */
    protected String callSwitch(REST restCall) {
        if (path.startsWith("/app.php")) {
            CoverageChecker.checkout(restCall + " " + path.substring(8) + ".{_format} ");
        } else {
            CoverageChecker.checkout(restCall + " " + path + ".{_format} ");
        }

        statusLine = "";
        headers = null;
        switch (restCall) {
            case GET:
                return this.setResponse(this.get());
            case POST:
                return this.setResponse(this.post());
            case PUT:
                return this.setResponse(this.put());
            case DELETE:
                return this.setResponse(this.delete());
            case PATCH:
                return this.setResponse(this.patch());
        }

        return "Could not call";
    }

    /**
     * Returns the GET request's response's JSON content in string format, if
     * there is no content empty string will be returned.
     *
     * @return String, JSON content in string format, maybe empty, never null.
     */
    private String get() {
        // The method is ready to work with path's that require id-s 
        String nPath;
        nPath = this.fixPath();

        System.out.print("GET \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreGet httpAction = new HttpCoreGet(nPath);

        CloseableHttpResponse httpResponse = httpAction.get();

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
    private String post() {
        // The method is ready to work with path's that require id-s 
        String nPath;
        nPath = this.fixPath();

        System.out.print("POST \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePost httpAction = new HttpCorePost(nPath);
        httpAction.setJSon(this.json);

        CloseableHttpResponse response = httpAction.post();

        if (response != null) {
            return getContentString(response);
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
    private String put() {
        // The method is ready to work with path's that require id-s
        String nPath;
        nPath = this.fixPath();

        System.out.print("PUT \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePut httpAction = new HttpCorePut(nPath);
        httpAction.setJSon(this.json);

        CloseableHttpResponse httpResponse = httpAction.put();

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
    private String delete() {
        // The method is ready to work with path's that require id-s
        String nPath;
        nPath = this.fixPath();

        System.out.print("DEL \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreDel httpAction = new HttpCoreDel(nPath);

        CloseableHttpResponse httpResponse = httpAction.delete();

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
    private String patch() {
        // The method is ready to work with path's that require id-s
        String nPath;
        nPath = this.fixPath();

        System.out.print("PATCH \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePatch httpAction = new HttpCorePatch(nPath);
        httpAction.setJSon(this.json);

        CloseableHttpResponse httpResponse = httpAction.patch();

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
    private String fixPath() {
        String nPath = this.path;
        if (nPath.startsWith("/api")) {
            nPath = "/app.php".concat(nPath);
        }
        if (nPath.contains("{id}")) {
            nPath = nPath.replace("{id}", Integer.toString(this.id));
        }
        if (nPath.contains("{pid}")) {
            nPath = nPath.replace("{pid}", Integer.toString(this.sId));
        }
        if (nPath.contains("{sid}")) {
            nPath = nPath.replace("{sid}", Integer.toString(this.sId));
        }
        if (nPath.contains("{asid}")) {
            nPath = nPath.replace("{asid}", Integer.toString(this.sId));
        }
        if (nPath.contains("{eid}")) {
            nPath = nPath.replace("{eid}", Integer.toString(this.sId));
        }
        if (nPath.contains("{epid}")) {
            nPath = nPath.replace("{epid}", Integer.toString(this.sId));
        }
        if (nPath.contains("{fedid}")) {
            nPath = nPath.replace("{fedid}", fedid);
        }
        if (nPath.contains("{token}")) {
            nPath = nPath.replace("{token}", token);
        }

        return nPath.concat(this.format);
    }

    /**
     * Consumes the response and returns the content in a non-parsed String.
     *
     * @param response CloseableHttpResponse
     * @return String:response.getEntity().getContent() in a non-parsed String,
     * if response (or the entity or the content) is null than the returned
     * String is empty (but not null)
     */
    private String getContentString(CloseableHttpResponse response) {
        String responseDataString;
        try {
            statusLine = response.getStatusLine().toString();
            headers = response.getAllHeaders();
        } catch (NullPointerException | IllegalStateException ex) {
//            Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
            statusLine = "";
            return "";
        }
        try {
            responseDataString = readContent(response.getEntity().getContent());
        } catch (NullPointerException | IOException | IllegalStateException ex) {
//            Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
        if (responseDataString.equalsIgnoreCase("null")) {
            return "";
        }
        if (responseDataString.contains("503 Service Unavailable")) {
            return responseDataString;
        }

        Object parsedResponse;
        try {
            parsedResponse = JSONParser.parseJSON(responseDataString);
            if (parsedResponse instanceof JSONObject) {
                parsedResponse = removeUpdate((JSONObject) parsedResponse);
            }
            if (parsedResponse instanceof JSONArray) {
                parsedResponse = removeUpdate((JSONArray) parsedResponse);
            }
        } catch (JSONException e) {
            return responseDataString;
        }

        responseDataString = parsedResponse.toString();

        return responseDataString;
    }

    /**
     * Removes the update key from the JSONObjects stored in the given
     * JSONArray. This is needed because the update value is changing whenever
     * someone reach for the object (even with GET) and that would create
     * unnecessary inconsistency.
     *
     * @param array JSONArray with JSONObjects to remove all update keys.
     */
    private JSONArray removeUpdate(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            if (array.get(i) instanceof JSONObject) {
                array.getJSONObject(i).remove("updated_at");
            } else if (array.get(i) instanceof JSONArray) {
                removeUpdate((JSONArray) array.get(i));
            }
        }
        return array;
    }

    private JSONObject removeUpdate(JSONObject object) {
        object.remove("updated_at");
        return object;
    }

    private String readContent(InputStream content) {
//        String data = new String();
//        BufferedReader br;
//        
//        br = new BufferedReader(new InputStreamReader(content));
//        
        java.util.Scanner s = new java.util.Scanner(content).useDelimiter("\\A");
        String data = s.hasNext() ? s.next() : "";
        s.close();

        return data;
    }
}