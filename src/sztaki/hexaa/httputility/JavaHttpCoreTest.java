package sztaki.hexaa.httputility;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(JavaHttpCoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        System.out.println(new BasicCall().call(Const.Api.PRINCIPAL_SELF, BasicCall.REST.GET));
    }
}
