package sztaki.hexaa.apicalls.other;

import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;

// TODO: kell egy jobb módszer ezeknek a kezelésére, jelenleg hardcodeolva vannak
public class EntityidsGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + EntityidsGetTest.class.getSimpleName()
				+ " ***");
	}

	@Test
	public void testEntityidsGet() {
		JSONObject jsonResponse;

		try {
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.ENTITYIDS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		System.out.println(jsonResponse.toString());
	}

}
