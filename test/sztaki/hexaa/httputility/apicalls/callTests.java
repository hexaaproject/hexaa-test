package sztaki.hexaa.httputility.apicalls;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.apicalls.attributes.*;
import sztaki.hexaa.httputility.apicalls.services.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    InvitationTest.class,
    AttributespecsEmptyTest.class,
    AttributespecsInsertTest.class,
    AttributespecsNonEmptyTest.class,
    ServicesEmptyTest.class,
    ServicesInsertTest.class


})
public class callTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
