package sztaki.hexaa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
// TODO more attributespecs - is_multivalue false test needed!
// TODO tag-ekkel kell láthatóság tesztelés - azonos tag-ek esetén /public entitlementek között látszódnia kell a privátnak is
// TODO securitydomain organization/services tesztek
// TODO vo isolation: a vo member nem látja a vo member listáját (üres tömb)
// TODO role isolation: a role member nem látja a role member listáját
/**
 * Runs the level differentiated tests in order.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ sztaki.hexaa.leveledtests.Level1TestSuite.class,
		sztaki.hexaa.leveledtests.Level2TestSuite.class,
		sztaki.hexaa.leveledtests.Level3TestSuite.class,
		sztaki.hexaa.leveledtests.Level4TestSuite.class,
		sztaki.hexaa.leveledtests.Level5TestSuite.class, })
public class AllLevels {

}
