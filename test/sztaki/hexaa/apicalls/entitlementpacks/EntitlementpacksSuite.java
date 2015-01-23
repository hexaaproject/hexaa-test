package sztaki.hexaa.apicalls.entitlementpacks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;
import sztaki.hexaa.apicalls.entitlementpacks.entitlements.*;

/**
 * TestSuite for the Entitlementpacks related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    EntitlementpacksGetTest.class,
    EntitlementpacksPutPatchTest.class,
    EntitlementpacksDeleteTest.class,
    EntitlementpacksAddEntitlementsTest.class,
    EntitlementpacksGetEntitlementsTest.class,
    EntitlementpacksRemoveEntitlementsTest.class,})
public class EntitlementpacksSuite extends BasicTestSuite {
}
