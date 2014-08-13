package sztaki.hexaa.httputility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

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
//        System.out.println(System.getProperty("user.dir"));
        
//        new Authenticator().loadProperties();
//        new DatabaseManipulator().dropDatabase();
//        new DatabaseManipulator().dropCache();
//        
//        new Authenticator().authenticate(Const.HEXAA_FEDID);

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("port", "80");
            prop.setProperty("host", "localhost");
            prop.setProperty("fedid", "tesztAdmin@sztaki.hu");
            prop.setProperty("master_secret", "7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
