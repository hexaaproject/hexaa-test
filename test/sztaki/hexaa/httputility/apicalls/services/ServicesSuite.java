package sztaki.hexaa.httputility.apicalls.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.services.entitlementpacks.*;
import sztaki.hexaa.httputility.apicalls.services.entitlements.*;

/**
 * TestSuite for the Services related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServicesGetTest.class,
    ServicesPostTest.class,
    ServicesEntitlementsPostTest.class,
    ServicesEntitlementsGetTest.class,
    ServicesEntitlementpacksPostTest.class,
    ServicesEntitlementpacksGetTest.class,})
public class ServicesSuite extends BasicTestSuite {
}
