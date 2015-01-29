package sztaki.hexaa.apicalls.attributevalueprincipals;

import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.apicalls.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the attributespecs related calls and
 * expecting not found or empty answers.
 */
public class AttributevalueprincipalIsEmptyTest extends IsEmptyTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ AttributevalueprincipalIsEmptyTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * GET method tests.
	 */
	@Test
	public void testAttributevalueprincipalIsEmptyGet() {
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.GET);
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES,
				BasicCall.REST.GET);
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
				BasicCall.REST.GET);
	}

	/**
	 * PUT method tests.
	 */
	@Test
	public void testAttributevalueprincipalIsEmptyPut() {
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.PUT);
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
				BasicCall.REST.PUT);
	}

	/**
	 * DELETE method tests.
	 */
	@Test
	public void testAttributevalueprincipalIsEmptyDelete() {
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.DELETE);
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
				BasicCall.REST.DELETE);
	}

	/**
	 * PATCH method tests.
	 */
	@Test
	public void testAttributevalueprincipalIsEmptyPatch() {
		expectingNotFound(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
				BasicCall.REST.PATCH);
	}
}
