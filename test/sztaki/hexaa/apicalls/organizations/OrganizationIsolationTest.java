package sztaki.hexaa.apicalls.organizations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.DataProp;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

public class OrganizationIsolationTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t "
				+ OrganizationIsolationTest.class.getSimpleName() + " ***");
	}

	/**
	 * JSONArray to store the created organizations.
	 */
	private static JSONArray organizations = new JSONArray();
	/**
	 * JSONArray to store the created principals.
	 */
	private static JSONArray principals = new JSONArray();

	/**
	 * Creates 2 organizations.
	 */
	@BeforeClass
	public static void setUpClass() {
		organizations = Utility.Create.organizationMemberIsolation(
				"OrganizationIsolationTest_org1", true);
		if (organizations.length() < 1) {
			fail("Utility.Create.organizationMemberIsolation( \"OrganizationIsolationTest_org1\", true); did not succeed");
		}
		organizations.put(Utility.Create.organization(
				"OrganizationIsolationTest_org2").getJSONObject(0));
		if (organizations.length() < 2) {
			fail("Utility.Create.organization( \"OrganizationIsolationTest_org2\"); did not succeed");
		}
		
		principals = Utility.Create
				.principal(new String[] { "OrganizationIsolationTest_pri1" });
		if (principals.length() < 1) {
			fail("Utility.Create.principal(new String[] {\"OrganizationIsolationTest_pri1\" }); did not succeed");
		}
		
		Utility.Link.memberToOrganization(organizations.getJSONObject(0)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));
		Utility.Link.memberToOrganization(organizations.getJSONObject(1)
				.getInt("id"), principals.getJSONObject(0).getInt("id"));

		persistent.authenticate("OrganizationIsolationTest_pri1");
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ OrganizationIsolationTest.class.getSimpleName());
		persistent.authenticate(new DataProp().getString("HEXAA_FEDID"));
		for (int i = 0; i < organizations.length(); i++) {
			Utility.Remove.organization(organizations.getJSONObject(i).getInt(
					"id"));
		}
		for (int i = 0; i < principals.length(); i++) {
			Utility.Remove.principal(principals.getJSONObject(i).getInt("id"));
		}
	}

	/**
	 * Test for creating a new organization and verify its existence.
	 */
	@Test
	public void testOrganizationIsolation() {
		JSONObject jsonResponse;
		try {
			persistent.setOffset(0);
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_MEMBERS, BasicCall.REST.GET,
					organizations.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.Conflict, persistent.getStatusLine());
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			persistent.setOffset(0);
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET,
					organizations.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations.getJSONObject(0),
					jsonResponse, false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	public void controlTestOrganizationIsolation() {
		JSONObject jsonResponse;
		try {
			persistent.setOffset(0);
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID_MEMBERS, BasicCall.REST.GET,
					organizations.getJSONObject(1).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			assertEquals(2, jsonResponse.getInt("item_number"));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}

		try {
			persistent.setOffset(0);
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ORGANIZATIONS_ID, BasicCall.REST.GET,
					organizations.getJSONObject(0).getInt("id"));
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}
		try {
			assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
			JSONAssert.assertEquals(organizations.getJSONObject(0),
					jsonResponse, false);
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}
}
