package sztaki.hexaa.httputility.apicalls;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.runners.model.MultipleFailureException;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
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
     * The error code after the checkArrayError or checkObjectError.
     */
    static protected int errorCode = 0;

    /**
     * The error message after the checkArrayError or checkObjectError.
     */
    static protected String errorMsg = new String();

    /**
     * Check if the object is a JSONObject or not, true if there is some problem
     * and sets the error code and message in the case of an error.
     *
     * @param json an object to decide if its a valid JSONArray or not.
     * @return true if there is an error, false if there is no problem.
     */
    public static boolean checkObjectError(Object json) {
        if (json instanceof JSONArray) {
            errorMsg = ((JSONObject) json).getString("message");
            errorCode = ((JSONObject) json).getInt("code");

            return true;
        } else if (json instanceof JSONObject) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the object is a JSONArray or not, true if there is some problem
     * and sets the error code and message in the case of an error.
     *
     * @param json an object to decide if its a valid JSONArray or not.
     * @return true if there is an error, false if there is no problem.
     */
    public static boolean checkArrayError(Object json) {
        if (json instanceof JSONObject) {
            errorMsg = ((JSONObject) json).getString("message");
            errorCode = ((JSONObject) json).getInt("code");

            return true;
        } else if (json instanceof JSONArray) {
            return false;
        } else {
            return true;
        }
    }

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
        new Authenticator().loadProperties();
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate(Const.HEXAA_FEDID);
    }

    /**
     * Handles the AssertationErrors in a unified way, can be easily expanded
     * for better utility.
     *
     * @param e AssertationError
     */
    public void AssertErrorHandler(Throwable e) {
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

        /**
         * List to collect the thrown errors for future use.
         */
        private ArrayList<Throwable> errors = new ArrayList<>();
        /**
         * String representing the method where the error was thrown. Not yet
         * implemented.
         */
        private String testMethodName = "";

        /**
         * Overriden from ErrorCollector.
         *
         * @throws Throwable
         */
        @Override
        public void verify() throws Throwable {
            MultipleFailureException.assertEmpty(this.errors);
        }

        /**
         * Overriden from ErrorCollector.
         *
         * @param e
         */
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
