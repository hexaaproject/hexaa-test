package sztaki.hexaa.apicalls.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;
import sztaki.hexaa.apicalls.services.attributespecs.*;
import sztaki.hexaa.apicalls.services.entitlementpacks.*;
import sztaki.hexaa.apicalls.services.entitlements.*;
import sztaki.hexaa.apicalls.services.managers.*;
import sztaki.hexaa.apicalls.services.organizations.ServicesOrganizationsGetTest;

/**
 * TestSuite for the Services related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ServicesPostTest.class, ServicesGetTest.class,
		ServicesDeleteTest.class, ServicesPutTest.class,
		ServicesAttributespecsAddTest.class,
		ServicesAttributespecsGetTest.class,
		ServicesAttributespecsRemoveTest.class,
		ServicesEntitlementpacksPostTest.class,
		ServicesEntitlementpacksGetTest.class,
		ServicesEntitlementsPostTest.class, ServicesEntitlementsGetTest.class,
		ServicesManagersAddTest.class, ServicesManagersGetTest.class,
		ServicesManagersRemoveTest.class, ServicesOrganizationsGetTest.class, })
public class ServicesSuite extends BasicTestSuite {
}
