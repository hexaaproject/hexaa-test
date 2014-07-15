package sztaki.hexaa.httputility.apicalls.entitlementpacks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.entitlementpacks.entitlements.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    EntitlementpacksIsEmptyTest.class,
    EntitlementpacksGetTest.class,
    EntitlementpacksPutTest.class,
    EntitlementpacksDeleteTest.class,
    EntitlementpacksAddEntitlementsTest.class,
    EntitlementpacksGetEntitlementsTest.class,
    EntitlementpacksRemoveEntitlementsTest.class,})
public class EntitlementpacksSuite extends BasicTestSuite {
}
