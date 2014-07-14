package sztaki.hexaa.httputility.apicalls.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.services.entitlementpacks.*;
import sztaki.hexaa.httputility.apicalls.services.entitlements.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServicesGetTest.class,
    ServicesPostTest.class,
    ServicesIsEmptyTest.class,
    ServicesEntitlementsPostTest.class,
    ServicesEntitlementsGetTest.class,
    ServicesEntitlementpacksPostTest.class,
    ServicesEntitlementpacksGetTest.class,})
/**
 * TestSuite for the Services related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
public class ServicesSuite extends BasicTestSuite {

}
