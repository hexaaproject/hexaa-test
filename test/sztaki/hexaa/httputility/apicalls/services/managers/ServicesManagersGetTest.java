package sztaki.hexaa.httputility.apicalls.services.managers;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.httputility.apicalls.services.Services;
import static sztaki.hexaa.httputility.apicalls.services.Services.createServices;

public class ServicesManagersGetTest extends Services {

    /**
     * Uses the first 2 entityids specified in the /hexaa/app/parameters.yml
     * file and creates a service for each and creates 2 attributespecs to work
     * with as well.
     */
    @BeforeClass
    public static void setUpClass() {
        createServices(new String[]{"testService1", "testService2"});
    }
    
    @Test
    public void testServicesManagersGet() {
        fail("TODO: Can't post principals yet");
    }
    
}
