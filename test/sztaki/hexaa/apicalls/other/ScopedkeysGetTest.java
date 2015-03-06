package sztaki.hexaa.apicalls.other;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import sztaki.hexaa.BasicCall;
import sztaki.hexaa.Const;
import sztaki.hexaa.NormalTest;
import sztaki.hexaa.ResponseTypeMismatchException;

public class ScopedkeysGetTest extends NormalTest {

	/**
	 * Print the class name on the output.
	 */
	@BeforeClass
	public static void classInformation() {
		System.out.println("***\t " + ScopedkeysGetTest.class.getSimpleName()
				+ " ***");
	}

	@Test
	public void testScopedkeysGetArray() {
		JSONArray jsonResponse;
		try {
			persistent.setAdmin();
			jsonResponse = persistent.getResponseJSONArray(
					Const.Api.SCOPEDKEYS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals("defaultMasterKey", jsonResponse.getString(0));
			assertEquals("otherMasterKey", jsonResponse.getString(1));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

	@Test
	public void testScopedkeysGetWithItems() {
		JSONObject jsonResponse;
		try {
			persistent.setAdmin();
			persistent.setOffset(0);
			jsonResponse = persistent.getResponseJSONObject(
					Const.Api.SCOPEDKEYS, BasicCall.REST.GET);
		} catch (ResponseTypeMismatchException ex) {
			fail(ex.getFullMessage());
			return;
		}

		try {
			assertEquals("defaultMasterKey", jsonResponse.getJSONArray("items")
					.getString(0));
			assertEquals("otherMasterKey",
					jsonResponse.getJSONArray("items").getString(1));
		} catch (AssertionError e) {
			AssertErrorHandler(e);
		}
	}

}
