package sztaki.hexaa.httputility.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sztaki.hexaa.httputility.ServerConstants;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 *
 * @author Bana Tibor
 */
public class HttpCoreGet {

    private HttpGet httpGet = null;

    public HttpCoreGet(String path) {
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme(ServerConstants.HEXAA_SCHEME)
                    .setHost(ServerConstants.HEXAA_HOST)
                    .setPort(ServerConstants.HEXAA_PORT)
                    .setPath(path)
                    .build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (uri != null) {
            httpGet = new HttpGet(uri);
            Header hexaa_auth = new BasicHeader(ServerConstants.HEXAA_HEADER, ServerConstants.HEXAA_AUTH);
            httpGet.addHeader(hexaa_auth);
        }
    }

    public CloseableHttpResponse get() {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            response = httpClient.execute(httpGet);
        } catch (IOException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }
}
