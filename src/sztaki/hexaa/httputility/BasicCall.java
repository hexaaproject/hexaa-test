package sztaki.hexaa.httputility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
public class BasicCall {

    // The URI path for the call
    private String path;
    private String fedid;

    // The requested ID and validation and requirement options
    private int id;

    // The possible special ID and validation and requirement options
    private int sId;

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

    /* *** Constructor *** */
    public BasicCall() {
        this.deleteEnabled = true;
        this.putEnabled = true;
        this.postEnabled = true;
        this.getEnabled = true;

        this.id = 0;
//        this.setIdRequirement(false);

        this.sId = 0;
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

    /* *** Normal calls, returns the response json *** */
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
    
    public String call(String path, REST restCall) {
        this.setPath(path);
        this.setString(null);
        this.setId(0);
        this.setSId(0);

        return callSwitch(restCall);
    }

    public String call(String path, REST restCall, String json, int id, int sId) {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);

        return callSwitch(restCall);
    }

    public String call(String path, REST restCall, String json, int id, int sId, String fedid) {
        this.setPath(path);
        this.setString(json);
        this.setId(id);
        this.setSId(sId);
        this.setFedid(fedid);

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
     * Returns the JSON payload from the GET response's content
     *
     * @return String: JSON payload
     */
    private String get() {
        // The method is ready to work with path's that require id-s 
        String nPath;
        nPath = this.fixPath();
        
        System.out.print("GET \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreGet entityids = new HttpCoreGet(nPath);
        CloseableHttpResponse response = entityids.get();

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

        // Initializing the required reader
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
                    Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return responseDataString;

    }

    /**
     * Uses the supplied json for the POST request and returns the json
     *
     * @return
     */
    private String post() {
        // The method is ready to work with path's that require id-s 
        String nPath;
        nPath = this.fixPath();
        
        System.out.print("POST \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePost entityids = new HttpCorePost(nPath);
        entityids.setJSon(this.json);

        CloseableHttpResponse response = entityids.post();

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
                    Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
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
        nPath = this.fixPath();
        
        System.out.print("PUT \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCorePut entityids = new HttpCorePut(nPath);
        entityids.setJSon(this.json);

        CloseableHttpResponse response = entityids.put();

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
                    Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
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
        nPath = this.fixPath();
        
        System.out.print("DELETE \t");
        System.out.println(nPath);

        // Getting the response from the server, this is
        // wrapped in the javahttputility.core package
        HttpCoreDelete entityids = new HttpCoreDelete(nPath);
        CloseableHttpResponse response = entityids.delete();

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
                    Logger.getLogger(BasicCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return responseDataString;
    }

    public String fixPath() {
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
}
