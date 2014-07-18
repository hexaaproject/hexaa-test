package sztaki.hexaa.httputility.apicalls;

import java.util.ArrayList;
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

    /**
     * BasicCall to be used to easily make a call(...) method without creating
     * new objects and storing references. Can call the getStatusLine() on it,
     * to get the last calls status line for additional information.
     */
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
     * runs before every child class, this way there is no interference between
     * classes.
     */
    @BeforeClass
    public static void cleanDB() {
        System.out.println("BeforeClass @ CleanTest");
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    //UNUSED
//    @AfterClass
//    public static void checkErrors() {
//    }
//
//    public CleanTest() {
//        this.collector.setName(this.getClass().getSimpleName());
//    }
    //
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

        /**
         * Sets the n as name.
         *
         * @param n new name.
         */
        public void setName(String n) {
            if (n != null) {
                this.testMethodName = n;
            }
        }

        /**
         * Prints the error stack and the class name to the standard error.
         */
        public void printErrorStack() {
            System.err.println(this.testMethodName);
            this.errors.stream().forEach((e) -> {
                System.err.println(e.getLocalizedMessage());
            });
        }
    }
}
