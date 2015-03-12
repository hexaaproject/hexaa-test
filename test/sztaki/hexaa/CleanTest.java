package sztaki.hexaa;

import org.junit.BeforeClass;

/**
 * Parent class for all TestClasses for unified use of @BeforeClass, BasicCall,
 * TestErrorCollector and AssertErrorHandler.
 */
public class CleanTest extends NormalTest{
	/**
	 * Connects to the server via Runtime.exec and executes a server side script
	 * that drops the database, recreates it and reinitializes it. This method
	 * runs before every child class, this way there is no interference between
	 * classes.
	 */
	@BeforeClass
	public static void cleanTestBasicSetUpClass() {
		System.out.println("BeforeClass @ CleanTest");
		new DatabaseManipulator().dropDatabase();
		persistent.authenticate(new DataProp().getString("HEXAA_FEDID"));
	}
}
