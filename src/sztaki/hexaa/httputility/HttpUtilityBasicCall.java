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
 * Abstract super class to support implementation of API calls on a defined URI.
 * The inheriting class has to specify: - the URI - the available call types -
 * override the unsafe call methods.
 *
 * @author Bana Tibor
 */
public abstract class HttpUtilityBasicCall {

    // The URI path for the call
    private String path;

    // The requested ID and validation and requirement options
    private int id;
    private boolean validId;
//    private boolean requireId_get;
//    private boolean requireId_post;
//    private boolean requireId_put;
//    private boolean requireId_delete;

    // The possible special ID and validation and requirement options
    private int sId;
    private boolean validSId;
//    private boolean requireSId_get;
//    private boolean requireSId_post;
//    private boolean requireSId_put;
//    private boolean requireSId_delete;

    // The JSON payload in a string format
    private String json;

    // Boolean-s to enable/disable the named variables
    protected boolean getEnabled;
    protected boolean postEnabled;
    protected boolean putEnabled;
    protected boolean deleteEnabled;

    /* *** Setter methods *** */
    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
        this.validId = true;
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
//
//    public final void setIdRequirement(boolean get, boolean post, boolean put, boolean delete) {
//        requireId_get = get;
//        requireId_post = post;
//        requireId_put = put;
//        requireId_delete = delete;
//    }
//
//    public final void setIdRequirement(boolean all) {
//        requireId_get = all;
//        requireId_post = all;
//        requireId_put = all;
//        requireId_delete = all;
//    }
//
//    public final void setSIdRequirement(boolean get, boolean post, boolean put, boolean delete) {
//        requireSId_get = get;
//        requireSId_post = post;
//        requireSId_put = put;
//        requireSId_delete = delete;
//    }
//
//    public final void setSIdRequirement(boolean all) {
//        requireSId_get = all;
//        requireSId_post = all;
//        requireSId_put = all;
//        requireSId_delete = all;
//    }

    /* *** Constructor *** */
    public HttpUtilityBasicCall() {
        this.deleteEnabled = true;
        this.putEnabled = true;
        this.postEnabled = true;
        this.getEnabled = true;

        this.id = 0;
        this.validId = false;
//        this.setIdRequirement(false);

        this.sId = 0;
        this.validSId = false;
//        this.setSIdRequirement(false);

        this.path = null;
    }

    /**
     * Enumeration to easily differentiate between the 4 types of calls. Values:
     * GET, POST, PUT, DELETE.
     */
    public enum REST {

        GET, POST, PUT, DELETE
    }

    /**
     * Method for API calls that do not require any ids or JSON.
     *
     * @param restCall
     * @return
     */
    public String call(REST restCall) {
        this.setString(null);
        this.setId(0);
        this.setSId(0);

        return callSwitch(restCall);
    }

    public String call(REST restCall, String json) {
        this.setString(json);
        this.setId(0);
        this.setSId(0);

        return callSwitch(restCall);
    }

    public String call(REST restCall, int id, int sId) {
        this.setString(null);
        this.setId(id);
        this.setSId(sId);

        return callSwitch(restCall);
    }

    public String call(REST restCall, String json, int id, int sId) {
        this.setString(json);
        this.setId(id);
        this.setSId(sId);

        return callSwitch(restCall);
    }

    protected String callSwitch(REST restCall) {
        // You can enable/disable the get/post/put/delete
        // methods, advised to do it in constructor
        switch (restCall) {
            case GET:
                if (getEnabled) {
                    return this.get();
                } else {
                    return "Get call is disabled";
                }
            case POST:
                if (postEnabled) {
                    return this.post();
                } else {
                    return "Post call is disabled";
                }
            case PUT:
                if (putEnabled) {
                    return this.put();
                } else {
                    return "Put call is disabled";
                }
            case DELETE:
                if (deleteEnabled) {
                    return this.delete();
                } else {
                    return "Delete call is disabled";
                }
        }

        return "Could not call";
    }

    /* *** Http handlers *** */
    /**
     * Returns the JSON payload from the respective response's content
     *
     * @return String: JSON payload
     */
    private String get() {
        // The method is ready to work with path's that require id-s 
        String nPath;
        nPath = this.path;
        if (this.validId) {
            nPath = this.path.replace("{id}", Integer.toString(this.id));
        }

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreGet entityids = new HttpCoreGet(nPath);
        CloseableHttpResponse response = entityids.get();

        // Initializing the parser and the required objects and reader
            /*JSONParser parser;
         parser = new JSONParser();*/
        BufferedReader br = null;
        /*
         JSONObject oData = null;
         JSONArray aData = null;*/
        String responseDataString = new String();
        try {
            // Reading the JSON payload in bytes
            try {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, "No Content", ex);
            }
            // Concating the read bytes together
            if (br != null) {
                String temp;
                temp = br.readLine();
                while (temp != null) {
                    responseDataString = responseDataString.concat(temp);
                    temp = br.readLine();
                }

                // Parsing the string into JSON data
                /* else if (responseDataString.charAt(0) == '[') {
                 aData = (JSONArray) parser.parse(responseDataString);
                 System.out.println(aData.toString());
                 } else if (responseDataString.charAt(0) == '{') {
                 oData = (JSONObject) parser.parse(responseDataString);
                 System.out.println(oData.toString());
                 }*/
            }

        } catch (IllegalStateException | IOException ex) {
            Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpUtilityBasicCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return responseDataString;

    }

    /**
     * Uses the supplied json for the request and returns the json
     *
     * @return
     */
    private String post() {
        // The method is ready to work with path's that require id-s 
        String nPath;
        nPath = this.path;
        if (this.validId) {
            nPath = this.path.replace("{id}", Integer.toString(this.id));
        }

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePost entityids = new HttpCorePost(nPath);
        if (json != null) {
            entityids.setJSon(this.json);
        }
        CloseableHttpResponse response = entityids.post();

        BufferedReader br = null;

        String responseDataString = new String();
        try {
            // Reading the JSON payload in bytes
            try {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, "No Content", ex);
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
            Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpUtilityBasicCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return responseDataString;
    }

    /**
     *
     * @return
     */
    private String put() {
        // The method is ready to work with path's that require id-s
        String nPath;
        nPath = this.path;
        if (this.validId) {
            nPath = this.path.replace("{id}", Integer.toString(this.id));
        }

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePut entityids = new HttpCorePut(nPath);
        entityids.setJSon(this.json);
        CloseableHttpResponse response = entityids.put();

        BufferedReader br = null;

        String responseDataString = new String();
        try {
            // Reading the JSON payload in bytes
            try {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, "No Content", ex);
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
            Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpUtilityBasicCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return responseDataString;
    }

    /**
     *
     * @return
     */
    private String delete() {
        // The method is ready to work with path's that require id-s
        String nPath;
        nPath = this.path;
        if (this.validId) {
            nPath = this.path.replace("{id}", Integer.toString(this.id));
        }

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreDelete entityids = new HttpCoreDelete(nPath);
        CloseableHttpResponse response = entityids.delete();

        BufferedReader br = null;

        String responseDataString = new String();
        try {
            // Reading the JSON payload in bytes
            try {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, "No Content", ex);
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
            Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpUtilityBasicCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return responseDataString;
    }
}
