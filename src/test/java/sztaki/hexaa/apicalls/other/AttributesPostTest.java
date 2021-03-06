package sztaki.hexaa.apicalls.other;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.CleanTest;
import sztaki.hexaa.Const;
import sztaki.hexaa.DataProp;
import sztaki.hexaa.Utility;

/**
 * Tests the POST method on the /api/attributes call, that returns all the
 * attributes without the normal authentication process for SSP.
 */
public class AttributesPostTest extends CleanTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + AttributesPostTest.class.getSimpleName()
				+ " ***");
	}

	@BeforeClass
	public static void setUpClass() {
		Utility.Create.attributespec("AS1", "manager");
		Utility.Create.service("TestService1");
		Utility.Create.entitlements(1, "TestEntitlement");
		Utility.Create.entitlementpacks(1, "TestEntitlementpack");
		Utility.Link.entitlementToPack(1, 1);
		Utility.Create.organization("TestOrg");
		Utility.Link.entitlementpackToOrg(1, 1);
		Utility.Link.attributespecsToService(1, 1, true);
	}

	@Test
	public void testAttributesPost() {
		JSONObject json = new JSONObject();

		json.put("fedid", new DataProp().getString("HEXAA_FEDID"));
		json.put("entityid", "https://example.com/ssp");
		json.put("apikey", persistent.getAPIKey(new DataProp().getString("MASTER_SECRET")));

		BasicCall call = new BasicCall();
		String response;

		response = call.call(Const.Api.ATTRIBUTES, BasicCall.REST.POST,
				json.toString());

		System.out.println(response);

		try {
			assertEquals(Const.StatusLine.OK, call.getStatusLine());
		} catch (AssertionError e) {
			fail(e.getLocalizedMessage());
		}

		fail("Missing assertions, only the StatusLine is checked");
	}
}
