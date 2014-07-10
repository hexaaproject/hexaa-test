package sztaki.hexaa.httputility.apicalls;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.runners.model.MultipleFailureException;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.DatabaseManipulator;

/**
 * Parent class for all TestClasses for unified use of @BeforeClass, BasicCall,
 * TestErrorCollector and AssertErrorHandler.
 */
public class CleanTest {

    static protected BasicCall persistent = new BasicCall();

    /**
     * This Rule allows the TestErrorCollector to bypass the AssertationErrors
     * so none of those will stop the running of the tests
     */
    @Rule
    public TestErrorCollector collector = new TestErrorCollector();

    /**
     * Connects to the server via Runtime.exec and executes a server side script
     * that drops the database, recreates it and reinitializes it. This method
     * runs before every child method, this way there is no interference between
     * classes.
     */
    @BeforeClass
    public static void cleanDB() {
        System.out.println("BeforeClass @ CleanTest");
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    @AfterClass
    public static void checkErrors() {
    }

    public CleanTest() {
        this.collector.setName(this.getClass().getSimpleName());
    }

    /**
     * Handles the AssertationErrors in a unified way, can be easily expanded
     * for better utility.
     *
     * @param e AssertationError
     */
    public void AssertErrorHandler(AssertionError e) {
        System.out.println(persistent.getStatusLine());
        System.out.println(e.getLocalizedMessage());
        collector.addError(e);
    }

    /**
     * ErrorCollector for the AssertationErrors happening during the execution
     * of the test classes, this way the TestSuite won't stop running after
     * minor errors, but finish and the errors are displayed all together.
     * Stores the error and the testclasses name.
     */
    public class TestErrorCollector extends ErrorCollector {

        private ArrayList<Throwable> errors = new ArrayList<>();
        private String testMethodName = "";

        public TestErrorCollector() {
            super();
        }

        @Override
        public void verify() throws Throwable {
            MultipleFailureException.assertEmpty(this.errors);
        }

        @Override
        public void addError(Throwable e) {
            if (!this.errors.contains(e)) {
                this.errors.add(e);
            }
        }

        public void setName(String n) {
            if (n != null) {
                this.testMethodName = n;
            }
        }

        public void printErrorStack() {
            System.err.println(this.testMethodName);
            this.errors.stream().forEach((e) -> {
                System.err.println(e.getLocalizedMessage());
            });
        }
    }
}
