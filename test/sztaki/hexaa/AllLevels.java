package sztaki.hexaa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs the level differentiated tests in order.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.leveledtests.LevelOneTestSuite.class,
    sztaki.hexaa.leveledtests.LevelTwoTestSuite.class,
    sztaki.hexaa.leveledtests.LevelThreeTestSuite.class,
    sztaki.hexaa.leveledtests.LevelFourTestSuite.class,
    sztaki.hexaa.leveledtests.LevelFiveTestSuite.class,
})
public class AllLevels {
    
}
