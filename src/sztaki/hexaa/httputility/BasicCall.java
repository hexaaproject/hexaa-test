package sztaki.hexaa.httputility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import sztaki.hexaa.httputility.core.HttpCoreDelete;
import sztaki.hexaa.httputility.core.HttpCoreGet;
import sztaki.hexaa.httputility.core.HttpCorePost;
import sztaki.hexaa.httputility.core.HttpCorePut;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * Support class that implements the 4 RESTful API calls. Can easily be expanded
 * if needed. The call methods with no String path parameters are Deprecated and
 * should NOT be used anymore, use the call methods WITH String path parameters.
 * Calling the child classes are not supported anymore.
 */
public class BasicCall {

    /**
     * The StatusLine of the last call, never null, maybe empty before any call
     * was made.
     */
    private String statusLine = "";

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
    private String json;

    /* *** Setter/getter methods *** */
    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public void setString(String json) {
        if (json == null) {
            json = new String();
        }
        this.json = json;
    }

    public void setFedid(String fedid) {
        this.fedid = fedid;
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
     * Prints all possible data for debug purposes.
     */
    public void printData() {
        System.out.println("\t" + statusLine);
        System.out.println("\t" + path);
        System.out.println("\t" + Integer.toString(id));
        System.out.println("\t" + Integer.toString(sId));
        System.out.println("\t" + json);
    }
    /* *** Constructor *** */

    public BasicCall() {
        this.id = 0;

        this.sId = 0;

        this.path = null;

        this.fedid = "fedid";
    }

    /**
     * Enumeration to easily differentiate between the 4 types of calls. Values:
     * GET, POST, PUT, DELETE.
     */
    public enum REST {

        GET, POST, PUT, DELETE
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

    /**
     * Calls the appropriate http handler.
     *
     * @param restCall REST, decides between the 4 normal REST call.
     * @return String, returns the response's content in string format.
     */
    protected String callSwitch(REST restCall) {
        switch (restCall) {
            case GET:
                return this.get();
            case POST:
                return this.post();
            case PUT:
                return this.put();
            case DELETE:
                return this.delete();
        }

        return "Could not call";
    }

    /* *** Http handlers *** */
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

        CloseableHttpResponse response = httpAction.get();

        return getContentString(response);

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

        return getContentString(response);
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

        CloseableHttpResponse response = httpAction.put();

        return getContentString(response);
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

        System.out.print("DELETE \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreDelete httpAction = new HttpCoreDelete(nPath);

        CloseableHttpResponse response = httpAction.delete();

        return getContentString(response);
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
        if (nPath.contains("{id}")) {
            nPath = this.path.replace("{id}", Integer.toString(this.id));
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

        return nPath.concat(".json");
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

        statusLine = "";
        statusLine = response.getStatusLine().toString();

        try {
            // If there is no content body we have to return an empty string
            if (response == null
                    || response.getEntity() == null
                    || response.getEntity().getContent() == null) {
                return "";
            }
        } catch (IOException | IllegalStateException ex) {
            Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader br = null;

        String responseDataString = new String();

        try {
            // Reading the JSON payload in bytes
            try {
                br = new BufferedReader(
                        new InputStreamReader(
                                response.getEntity().getContent()));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(
                        JavaHttpCoreTest.class.getName()).log(
                                Level.SEVERE, "No Content", ex);
            }
            // Concating the read bytes together
            if (br != null) {
                String temp;
                temp = br.readLine();
                while (temp != null) {
                    responseDataString = responseDataString.concat(temp);
                    temp = br.readLine();
                }
            }

        } catch (IllegalStateException | IOException ex) {
            Logger.getLogger(
                    JavaHttpCoreTest.class.getName()).log(
                            Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(
                            BasicCall.class.getName()).log(
                                    Level.SEVERE, null, ex);
                }
            }
        }
        return responseDataString;
    }
}
