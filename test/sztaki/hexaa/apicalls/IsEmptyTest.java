package sztaki.hexaa.apicalls;

import sztaki.hexaa.CleanTest;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.BasicCall.REST;
import sztaki.hexaa.Const;

/**
 * Utility class to be inherited by the IsEmpty test classes. Implements
 * CleanTest and provides two methods for easier testing.
 */
public abstract class IsEmptyTest extends CleanTest {

    /**
     * Calls the rest method on the constApi uri and expects an empty JSON
     * response and 200 OK.
     *
     * @param constApi an URI on the host.
     * @param rest a REST call to be called.
     */
    public void expectingEmpty(String constApi, REST rest) {
        String stringResponse
                = persistent.call(
                        constApi,
                        rest);
        try {
            assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
            assertEquals("[]", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Calls the rest method on the constApi uri and expects an error code
     * response and 404 Not Found.
     *
     * @param constApi an URI on the host.
     * @param rest a REST call to be called.
     */
    public void expectingNotFound(String constApi, REST rest) {
        String stringResponse
                = persistent.call(
                        constApi,
                        rest);
        try {
            assertEquals(Const.StatusLine.NotFound, persistent.getStatusLine());
            assertEquals("{\"code\":404,\"message\":\"Not Found\",\"errors\":null}", stringResponse);
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    /**
     * Calls the rest method on the constApi uri and expects a fedid response
     * and 200 OK. Can handle JSON Arrays and Objects as response as well.
     *
     * @param constApi an URI on the host.
     * @param rest a REST call to be called.
     */
    public void expectingFedid(String constApi, REST rest) {
        Object object = JSONParser.parseJSON(
                persistent.call(
                        constApi,
                        rest));
        if (object instanceof JSONArray) {
            JSONArray jsonResponse = (JSONArray) object;
            try {
                assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
                assertEquals(
                        Const.HEXAA_FEDID,
                        jsonResponse.getJSONObject(0).getString("fedid"));
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }

        if (object instanceof JSONObject) {
            JSONObject jsonResponse = (JSONObject) object;
            try {
                assertEquals(Const.StatusLine.OK, persistent.getStatusLine());
                assertEquals(
                        Const.HEXAA_FEDID,
                        jsonResponse.getString("fedid"));
            } catch (AssertionError e) {
                AssertErrorHandler(e);
            }
        }
    }
}