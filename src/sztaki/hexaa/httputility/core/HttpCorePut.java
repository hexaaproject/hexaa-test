package sztaki.hexaa.httputility.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import sztaki.hexaa.httputility.ServerConstants;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

/**
 *
 * @author Bana Tibor
 */
public class HttpCorePut {

    private HttpPut httpPut = null;

    public HttpCorePut(String path) {
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
            httpPut = new HttpPut(uri);
            Header hexaa_auth = new BasicHeader(ServerConstants.HEXAA_HEADER, ServerConstants.HEXAA_AUTH);
            httpPut.addHeader(hexaa_auth);
        }
    }

    public CloseableHttpResponse put() {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            response = httpClient.execute(httpPut);
        } catch (IOException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    public void setJSon(String json) {
        BasicHttpEntity entity = new BasicHttpEntity();

        if (json == null) {
            json = new String();
        }

        entity.setContent(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
        entity.setContentLength(json.length());
        httpPut.setEntity(entity);
    }
}
