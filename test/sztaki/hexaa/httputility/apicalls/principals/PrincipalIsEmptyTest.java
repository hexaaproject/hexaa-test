package sztaki.hexaa.httputility.apicalls.principals;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET method on the principal related calls and expecting not found
 * or empty answers.
 */
public class PrincipalIsEmptyTest extends IsEmptyTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + PrincipalIsEmptyTest.class.getSimpleName() + " ***");
    }

    /**
     * GET method tests.
     */
    @Test
    public void testPrincipalisEmpty() {
        expectingEmpty(
                Const.Api.MANAGER_ORGANIZATIONS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.MANAGER_SERVICES,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.MEMBER_ORGANIZATIONS,
                BasicCall.REST.GET);
        expectingFedid(
                Const.Api.PRINCIPAL_SELF,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ENTITLEMENTS,
                BasicCall.REST.GET);
        expectingEmpty(
                Const.Api.PRINCIPAL_ROLES,
                BasicCall.REST.GET);
        expectingFedid(
                Const.Api.PRINCIPALS,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.PRINCIPALS_ASID_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPALS,
                BasicCall.REST.GET);
        expectingFedid(Const.Api.PRINCIPALS_ID_ID,
                BasicCall.REST.GET);
    }
}
