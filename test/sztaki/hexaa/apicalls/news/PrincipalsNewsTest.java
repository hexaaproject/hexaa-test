package sztaki.hexaa.apicalls.news;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.Authenticator;
import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;
import sztaki.hexaa.CleanTest;

// TODO: még nem tudom mi az aminek meg kellene jelennie
/**
 *
 */
public class PrincipalsNewsTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + PrincipalsNewsTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * Creates the necessary objects.
	 */
	@BeforeClass
	public static void setUpClass() {
		// services = Utility.Create.service(new String[]{"testService1",
		// "testService2"});
		Utility.Create.organization("testOrg");
		Utility.Create.service("testService");
		Utility.Create.entitlementpacks(1, "testPack");
		Utility.Create.entitlements(1, "randomEnt");
		Utility.Link.entitlementToPack(1, 1);
		Utility.Link.entitlementpackToOrg(1, 1);
		Utility.Create.role("randomRole", 1);
		Utility.Create.principal("testForNewsP");
		Utility.Create.invitationToOrg(new String[] { "tesztAdmin@sztaki.hu" },
				null, "random invite message", 1, 1);
		Utility.Create.invitationToOrg(
				new String[] { "testForNewsP@sztaki.hu" }, null,
				"random invite message", 1, 1);
		Utility.Link.principalToRole(1, 2);
		Utility.Remove.organization(1);
	}

	@Test
	public void testPrincipalsNews() {
		JSONArray jsonResponse_id;

		try {
			jsonResponse_id = persistent.getResponseJSONArray(
					Const.Api.PRINCIPALS_PID_NEWS, BasicCall.REST.GET, null, 1,
					1);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationsNewsTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse_id);

		JSONArray jsonResponse_noid;

		try {
			jsonResponse_noid = persistent.getResponseJSONArray(
					Const.Api.PRINCIPAL_NEWS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationsNewsTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse_noid);

		// JSONArray jsonResponse_id;

		try {
			jsonResponse_id = persistent.getResponseJSONArray(
					Const.Api.PRINCIPALS_PID_NEWS, BasicCall.REST.GET, null, 2,
					2);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationsNewsTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse_id);

		new Authenticator().authenticate("testForNewsP");
		Utility.Create.service("testService2");

		// JSONArray jsonResponse_noid;

		try {
			jsonResponse_noid = persistent.getResponseJSONArray(
					Const.Api.PRINCIPAL_NEWS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			Logger.getLogger(OrganizationsNewsTest.class.getName()).log(
					Level.SEVERE, null, ex);
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse_noid);

		new Authenticator().authenticate(Const.HEXAA_FEDID);

		// try {
		// assertEquals(3, jsonResponse_id.length());
		// } catch (AssertionError e) {
		// AssertErrorHandler(e);
		// }
	}
}