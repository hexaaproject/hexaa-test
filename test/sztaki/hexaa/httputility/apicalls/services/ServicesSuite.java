package sztaki.hexaa.httputility.apicalls.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsGetTest;
import sztaki.hexaa.httputility.apicalls.services.attributespecs.ServicesAttributespecsPostTest;
import sztaki.hexaa.httputility.apicalls.services.entitlementpacks.*;
import sztaki.hexaa.httputility.apicalls.services.entitlements.*;
import sztaki.hexaa.httputility.apicalls.services.managers.ServicesManagersGetTest;

/**
 * TestSuite for the Services related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServicesGetTest.class,
    ServicesPostTest.class,
    ServicesAttributespecsGetTest.class,
    ServicesAttributespecsPostTest.class,
    ServicesEntitlementpacksGetTest.class,
    ServicesEntitlementpacksPostTest.class,
    ServicesEntitlementsGetTest.class,
    ServicesEntitlementsPostTest.class,
    ServicesManagersGetTest.class})
public class ServicesSuite extends BasicTestSuite {
}
