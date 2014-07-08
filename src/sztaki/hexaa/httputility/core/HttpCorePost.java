package sztaki.hexaa.httputility.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import sztaki.hexaa.httputility.Const;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 *
 * @author Bana Tibor
 */
public class HttpCorePost {

    private HttpPost httpAction = null;

    public HttpCorePost(String path) {
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
            httpAction = new HttpPost(uri);
            Header hexaa_auth = new BasicHeader(Const.HEXAA_HEADER, Const.HEXAA_AUTH);
            httpAction.addHeader(hexaa_auth);
            httpAction.setHeader("Content-type", "application/json");
            
        }
    }

    public CloseableHttpResponse post() {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            response = httpClient.execute(httpAction);
        } catch (IOException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    public void setJSon(String json) {
        if (json == null) {
            json = new String();
        }

        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
        entity.setContentLength(json.length());
        httpAction.setEntity(entity);
    }
}
