package sztaki.hexaa;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.runners.model.MultipleFailureException;

/**
 * Parent class for all TestClasses for unified use of @BeforeClass, BasicCall,
 * TestErrorCollector and AssertErrorHandler.
 */
public class NormalTest {

	/**
	 * BasicCall to be used to easily make a call(...) method without creating
	 * new objects and storing references. Can call the getStatusLine() on it,
	 * to get the last calls status line for additional information.
	 */
	static protected JSONCall persistent = new JSONCall();

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
	public static void normalTestBasicSetUpClass() {
		System.out.println("BeforeClass @ NormalTest");
		if (persistent.authenticate(new DataProp().getString("HEXAA_FEDID")) == 1) {
			fail("Unable to authenticate");
		}
	}

	public JSONArray getItems(JSONObject jsonItems) {
		if (jsonItems.has("code")) {
			fail("Got error json instead of items: " + jsonItems.toString());
			return new JSONArray();
		}

		if (jsonItems.has("items")) {
			return jsonItems.getJSONArray("items");
		}

		return new JSONArray();

	}

	/**
	 * Handles the AssertationErrors in a unified way, can be easily expanded
	 * for better utility.
	 *
	 * @param e
	 *            AssertationError
	 */
	public void AssertErrorHandler(Throwable e) {
		System.out.println(persistent.getStatusLine());
		if (persistent.getStatusLine().equals(Const.StatusLine.BadRequest)) {
			fail(persistent.call());
		}
		System.out.println(e.getLocalizedMessage());
		collector.addError(e);
	}

	/**
	 * ErrorCollector for the AssertationErrors happening during the execution
	 * of the test classes, this way the TestSuite won't stop running after
	 * minor errors, but finish and the errors are displayed all together.
	 * Stores the error and the testclasses name. This class is necessary for
	 * non-jsonassert assertions to work properly.
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
		 * @param n
		 *            new name.
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
