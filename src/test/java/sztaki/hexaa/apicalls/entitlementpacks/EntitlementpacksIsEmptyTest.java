package sztaki.hexaa.apicalls.entitlementpacks;

import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.IsEmptyTest;

/**
 * Tests the GET,PUT,DELETE methods on the entitlementpack related calls and
 * expecting not found or empty answers.
 */
public class EntitlementpacksIsEmptyTest extends IsEmptyTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ EntitlementpacksIsEmptyTest.class.getSimpleName() + " ***");
	}

	/**
	 * GET method tests.
	 */
	@Test
	public void testEntitlementpacksIsEmptyGet() {
		expectingZeroItems(Const.Api.ENTITLEMENTPACKS_PUBLIC, BasicCall.REST.GET);

		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.GET);

		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS,
				BasicCall.REST.GET);
	}

	/**
	 * PUT method tests.
	 */
	@Test
	public void testEntitlementpacksIsEmptyPut() {
		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.PUT);
		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
				BasicCall.REST.PUT);
	}

	/**
	 * DELETE method tests.
	 */
	@Test
	public void testEntitlementpacksIsEmptyDelete() {
		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.DELETE);
		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
				BasicCall.REST.DELETE);
	}

	/**
	 * PATCH method tests.
	 */
	@Test
	public void testEntitlementpacksIsEmptyPatch() {
		expectingNotFound(Const.Api.ENTITLEMENTPACKS_ID, BasicCall.REST.PATCH);
	}
}
