package sztaki.hexaa.httputility.apicalls.organizations;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.internal.AssumptionViolatedException;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.services.Services;

/**
 * Utility class to inherit from for Organization related Test classes and gives
 * easy to use calls to create organizations.
 */
public class Organization extends CleanTest {
    
    public static JSONArray organizations = new JSONArray();

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
