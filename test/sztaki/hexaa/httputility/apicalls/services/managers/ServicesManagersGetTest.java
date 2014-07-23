package sztaki.hexaa.httputility.apicalls.services.managers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

public class ServicesManagersGetTest extends CleanTest {

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each and creates 2 attributespecs to work
     * with as well.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.services(new String[]{"testService1", "testService2"});
    }

    @Test
    public void testServicesManagersGet() {
        // TODO
    }

}
