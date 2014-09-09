package sztaki.hexaa.httputility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import sztaki.hexaa.httputility.core.HttpCorePost;

/**
 * Just for random things, basically don't use it.
 */
public class JavaHttpCoreTest {

    /**
     * Main program starting point.
     *
     * @param args
     */
    public static void main(String[] args) {
//        new JavaHttpCoreTest().properties();

        new Authenticator().loadProperties();
//        new DatabaseManipulator().dropDatabase();
//        new DatabaseManipulator().dropCache();
//        
//        new Authenticator().authenticate(Const.HEXAA_FEDID);
        /***
        HttpPost httpAction = null;
        
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme(Const.HEXAA_SCHEME)
                    .setHost(Const.HEXAA_HOST)
                    .setPort(Const.HEXAA_PORT)
                    .setPath(Const.Api.ENTITYIDS)
                    .build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (uri != null) {
            httpAction = new HttpPost(uri);
            Header hexaa_auth = new BasicHeader(Const.HEXAA_HEADER, Const.HEXAA_AUTH);
            httpAction.addHeader(hexaa_auth);
            httpAction.setHeader("Content-type", "application/json");
            httpAction.setHeader("Accept", "application/json");
        }
        
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            response = httpClient.execute(httpAction);
        } catch (IOException ex) {
            Logger.getLogger(HttpCorePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(response.toString());
        */
        System.out.println(new Authenticator().getAPIKey());
        
        BasicCall random = new BasicCall();
        String response = random.call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET);
        
        System.out.println(response);
    }
    
    private void properties() {
        Properties prop = new Properties();
        OutputStream output = null;

        File f = new File(Const.PROPERTIES);
        if (f.exists()) {
            return;
        } else {
            System.out.println("The file config.properties does not exists,"
                    + " creating it with default attributes.");
            System.err.println("The file config.properties does not exists, no"
                    + " properties can be read, if your setup does not match the"
                    + " default this will cause inconsitent tests (mostly failed"
                    + " tests and errors).");
        }

        try {

            output = new FileOutputStream(Const.PROPERTIES);

            // set the properties value
            prop.setProperty("port", "80");
            prop.setProperty("host", "localhost");
            prop.setProperty("fedid", "tesztAdmin@sztaki.hu");
            prop.setProperty("master_secret", "7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }

        }
    }
}
