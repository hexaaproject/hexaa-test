package sztaki.hexaa.httputility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    sztaki.hexaa.httputility.leveledtests.LevelTwoTestSuite.class,
    sztaki.hexaa.httputility.leveledtests.LevelOneTestSuite.class,
    sztaki.hexaa.httputility.leveledtests.LevelThreeTestSuite.class,
    sztaki.hexaa.httputility.leveledtests.LevelFiveTestSuite.class,
    sztaki.hexaa.httputility.leveledtests.LevelFourTestSuite.class
})
public class AllLevels {
    
}