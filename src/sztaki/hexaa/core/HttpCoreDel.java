package sztaki.hexaa.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sztaki.hexaa.Const;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 * Basic wrapper class for DEL functions using the org.apache.http.client.
 */
public class HttpCoreDel {

    private HttpDelete httpAction = null;

    /**
     * Builds a new URI with the given path and creates a HttpDelete action with
     * it, also sets the required headers (X-HEXAA-AUTH nad Content-type).
     *
     * @param path String that represents the URI path of the call. must be a
     * complete path (with ids injected and .json at the end).
     */
    public HttpCoreDel(String path) {

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme(Const.HEXAA_SCHEME)
                    .setHost(Const.HEXAA_HOST)
                    .setPort(Const.HEXAA_PORT)
                    .setPath(path)
                    .build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (uri != null) {
            httpAction = new HttpDelete(uri);
            Header hexaa_auth = new BasicHeader(Const.HEXAA_HEADER, Const.HEXAA_AUTH);
            httpAction.addHeader(hexaa_auth);
            httpAction.setHeader("Content-type", "application/json");
            httpAction.setHeader("Accept", "application/json");
        }
    }

    /**
     * Executes the DEL action on the path given in the constructor.
     *
     * @return returns a CloseableHttpResponse.
     */
    public CloseableHttpResponse delete() {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            response = httpClient.execute(httpAction);
        } catch (IOException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }
}
