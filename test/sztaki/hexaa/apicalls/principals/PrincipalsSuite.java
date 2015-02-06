package sztaki.hexaa.apicalls.principals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.BasicTestSuite;

/**
 * TestSuite for the Principals related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ PrincipalGetAdminTest.class, PrincipalGetTest.class,
		PrincipalsAttributespecsPrivateGetTest.class,
		PrincipalsAttributespecsPublicGetTest.class,
		PrincipalsAttributevaluesGetTest.class, PrincipalsDeleteSelfTest.class,
		PrincipalsDeleteTest.class, PrincipalsEntitlementsGetTest.class,
		PrincipalsGetManagersOrganizationsTest.class,
		PrincipalsManagerGetServicesTest.class,
		PrincipalsMemeberGetOrganizationsTest.class, PrincipalsPostTest.class,
		PrincipalsRoleGetTest.class, })
public class PrincipalsSuite extends BasicTestSuite {
}
