package sztaki.hexaa.httputility;

import java.io.File;
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
