package sztaki.hexaa.apicalls.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sztaki.hexaa.BasicTestSuite;
import sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsAddTest;
import sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsGetTest;
import sztaki.hexaa.apicalls.services.attributespecs.ServicesAttributespecsRemoveTest;
import sztaki.hexaa.apicalls.services.entitlementpacks.ServicesEntitlementpacksGetTest;
import sztaki.hexaa.apicalls.services.entitlementpacks.ServicesEntitlementpacksPostTest;
import sztaki.hexaa.apicalls.services.entitlements.ServicesEntitlementsGetTest;
import sztaki.hexaa.apicalls.services.entitlements.ServicesEntitlementsPostTest;
import sztaki.hexaa.apicalls.services.managers.ServicesManagersAddTest;
import sztaki.hexaa.apicalls.services.managers.ServicesManagersGetTest;
import sztaki.hexaa.apicalls.services.managers.ServicesManagersRemoveTest;
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
