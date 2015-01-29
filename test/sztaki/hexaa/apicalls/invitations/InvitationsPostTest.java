package sztaki.hexaa.apicalls.invitations;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.Const;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

/**
 * Test the POST method on the /api/invitations call.
 */
public class InvitationsPostTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + InvitationsPostTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates an organizations.
	 */
	@BeforeClass
	public static void setUpClass() {
		Utility.Create.organization("TestOrgName1");
	}

	/**
	 * POST one invitation.
	 */
	@Test
	public void testInvitationsPost() {
		Utility.Create.invitationToOrg(null, "http://hexaa.eduid.hu/hexaaui",
				"This is a test invitation.", 0, 1);

		try {
			assertEquals(Const.StatusLine.Created,
					Utility.persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
