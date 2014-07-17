package sztaki.hexaa.httputility.apicalls.organizations;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.internal.AssumptionViolatedException;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Utility class to inherit from for Organization related Test classes and gives
 * easy to use calls to create organizations.
 */
public class Organization extends CleanTest {

    /**
     * Creates as many organizations as many name is specified in the names
     * String array. Returns them as a JSONArray. Can create organizations with
     * unique names only, if names are repeating it will drop an assumption
     * error and fails the test case.
     *
     * @param names a String array representation of the names to create
     * organizations with.
     * @return JSONArray with all the created organizations in it.
     */
    public static JSONArray createOrganization(String[] names) {
        JSONArray organizations = new JSONArray();

        for (String name : names) {
            JSONObject json = new JSONObject();
            json.put("name", name);
            organizations.put(json);

            persistent.call(
                    Const.Api.ORGANIZATIONS,
                    BasicCall.REST.POST,
                    json.toString(),
                    1, 1);

            try {
                Assume.assumeTrue(
                        persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + Organization.class.getName()
                        + " the POST call on /api/organization failed");
                fail("POST /api/organization was unsuccessful.");
            }
        }
        return organizations;
    }
}
