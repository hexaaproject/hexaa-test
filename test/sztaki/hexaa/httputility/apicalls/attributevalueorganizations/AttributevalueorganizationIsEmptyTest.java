package sztaki.hexaa.httputility.apicalls.attributevalueorganizations;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.httputility.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the attributespecs related calls and
 * expecting not found or empty answers.
 */
public class AttributevalueorganizationIsEmptyTest extends IsEmptyTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + AttributevalueorganizationIsEmptyTest.class.getSimpleName() + " ***");
    }

    /**
     * GET method tests.
     */
    @Test
    public void testAttributevalueorganizationIsEmptyGet() {
        expectingNotFound(
                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES,
                BasicCall.REST.GET);
        expectingNotFound(
                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
                BasicCall.REST.GET);
    }

    /**
     * PUT method tests.
     */
    @Test
    public void testAttributevalueorganizationIsEmptyPut() {
        expectingNotFound(
                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
                BasicCall.REST.PUT);
        expectingNotFound(
                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
                BasicCall.REST.PUT);
    }

    /**
     * DELETE method tests.
     */
    @Test
    public void testAttributevalueorganizationIsEmptyDelete() {
        expectingNotFound(Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
                BasicCall.REST.DELETE);
        expectingNotFound(Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
                BasicCall.REST.DELETE);
    }

    /**
     * PATCH method tests.
     */
    @Test
    public void testAttributevalueorganizationIsEmptyPatch() {
        expectingNotFound(
                Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
                BasicCall.REST.PATCH);
    }
}
