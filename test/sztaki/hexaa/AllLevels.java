package sztaki.hexaa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs the level differentiated tests in order.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		sztaki.hexaa.leveledtests.Level1Alternative.class,
		sztaki.hexaa.leveledtests.Level2TestSuite.class,
		sztaki.hexaa.leveledtests.Level3TestSuite.class,
		sztaki.hexaa.leveledtests.Level4TestSuite.class,
		sztaki.hexaa.leveledtests.Level5TestSuite.class, })
public class AllLevels {

}
