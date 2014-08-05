package sztaki.hexaa.httputility.apicalls.roles;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.apicalls.BasicTestSuite;
import sztaki.hexaa.httputility.apicalls.roles.entitlements.*;
import sztaki.hexaa.httputility.apicalls.roles.principals.*;

/**
 * TestSuite for the Roles related test cases, runs them all and does the
 * starting and finishing utility jobs inherited from BasicTestSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    RolesPostTest.class,
    RolesDeleteTest.class,
    RolesPutTest.class,
    RolesGetTest.class,
    RolesEntitlementsAddTest.class,
    RolesEntitlementsGetTest.class,
    RolesEntitlementsRemoveTest.class,
    RolesPrincipalsAddTest.class,
    RolesPrincipalsGetTest.class,
    RolesPrincipalsRemoveTest.class,})
public class RolesSuite extends BasicTestSuite {
}
