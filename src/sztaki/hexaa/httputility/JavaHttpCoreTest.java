package sztaki.hexaa.httputility;

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
    }
}
