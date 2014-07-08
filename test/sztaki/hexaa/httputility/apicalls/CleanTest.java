package sztaki.hexaa.httputility.apicalls;

import org.junit.BeforeClass;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;

public class CleanTest {
    @BeforeClass
    public static void cleanDB() {
        System.out.println("BeforeClass @ CleanTest");
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }
    
}
