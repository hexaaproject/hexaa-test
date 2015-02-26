package sztaki.hexaa;

import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

public class AllIsEmptyMethodNotAllowedParallelTest extends CleanTest {
	@SuppressWarnings("rawtypes")
	Class[] cls = {
			sztaki.hexaa.apicalls.attributevalueorganizations.AttributevalueorganizationIsEmptyTest.class,
			sztaki.hexaa.apicalls.attributevalueprincipals.AttributevalueprincipalIsEmptyTest.class,
			sztaki.hexaa.apicalls.attributespecs.AttributespecsIsEmptyTest.class,
			sztaki.hexaa.apicalls.entitlementpacks.EntitlementpacksIsEmptyTest.class,
			sztaki.hexaa.apicalls.entitlements.EntitlementsIsEmptyTest.class,
			sztaki.hexaa.apicalls.organizations.OrganizationIsEmptyTest.class,
			sztaki.hexaa.apicalls.principals.PrincipalIsEmptyTest.class,
			sztaki.hexaa.apicalls.roles.RolesIsEmptyTest.class,
			sztaki.hexaa.apicalls.services.ServicesIsEmptyTest.class,
			sztaki.hexaa.MethodNotAllowedTest.class, };

	@Test
	public void ParallelTests() {	// Good luck using the std output for any kind of debugging, haha

//		// Parallel among classes
//		JUnitCore.runClasses(ParallelComputer.classes(), cls);
//
//		// Parallel among methods in a class
//		JUnitCore.runClasses(ParallelComputer.methods(), cls);

		// Parallel all methods in all classes
		JUnitCore.runClasses(new ParallelComputer(true, true), cls);
	}

}
