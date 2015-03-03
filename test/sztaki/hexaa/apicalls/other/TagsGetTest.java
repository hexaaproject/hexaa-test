package sztaki.hexaa.apicalls.other;

import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;
import sztaki.hexaa.Utility;

// TODO: nem tudom minek kellene itt látszódnia
public class TagsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + TagsGetTest.class.getSimpleName()
				+ " ***");
	}

	/**
	 * JSONArray to store the created securitydomains.
	 */
	private static JSONArray domains = new JSONArray();

	/**
	 * Creates two attributespecs.
	 */
	@BeforeClass
	public static void setUpClass() {
		domains = Utility.Create
				.securitydomain("TagsGetTest_sd1", "alternativeTestMasterKey",
						"This is a security domain to test the capability of posting one.");
		if (domains.length() < 1) {
			fail("Utility.Create.securitydomain(\"TagsGetTest_sd1\", \"alternativeTestMasterKey\", \"This is a security domain to test the capability of posting one.\"); did not succeed");
		}
	}

	/**
	 * Reverses the setUpClass and the creations during the test.
	 */
	@AfterClass
	public static void tearDownClass() {
		System.out.println("TearDownClass: "
				+ TagsGetTest.class.getSimpleName());
		for (int i = 0; i < domains.length(); i++) {
			Utility.Remove
					.securitydomain(domains.getJSONObject(i).getInt("id"));
		}
	}

	@Test
	public void testTagsGet() {
		JSONArray jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONArray(Const.Api.TAGS,
					BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse.toString());

	}
}
