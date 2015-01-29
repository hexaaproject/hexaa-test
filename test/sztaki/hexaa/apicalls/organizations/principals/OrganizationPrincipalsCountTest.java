package sztaki.hexaa.apicalls.organizations.principals;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Tests the GET method on the /api/organizations/{id}/member/count and
 * /api/organizations/{id}/manager/count calls.
 */
public class OrganizationPrincipalsCountTest extends CleanTest {

	/**
	 * Creates an organization and principals and links them to be members and
	 * manager.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("countTests");
		Utility.Create.principal(new String[] { "countTestP1", "countTestP2" });

		Utility.Link.managerToOrganization(1, 2);
		Utility.Link.memberToOrganization(1, 3);
	}

	/**
	 * Tests the ../member/count.
	 */
	@Test
	public void testOrganizationMemberCount() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent
					.getResponseJSONObject(
							Const.Api.ORGANIZATIONS_ID_MEMBER_COUNT,
							BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationPrincipalsCountTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(3, jsonResponse.get("count"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	/**
	 * Tests the ../manager/count.
	 */
	@Test
	public void testOrganizationManagerCount() {
		JSONObject jsonResponse;
		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_MANAGER_COUNT,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationPrincipalsCountTest.class.getName())
					.log(Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals(2, jsonResponse.get("count"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
