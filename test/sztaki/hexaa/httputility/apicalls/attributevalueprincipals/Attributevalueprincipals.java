package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.internal.AssumptionViolatedException;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;

/**
 * Utility class for attributevalueprincipals for easy to use creation.
 */
public class Attributevalueprincipals extends CleanTest {

    /**
     * Creates as many attributevalueprincipals as many value is specified in
     * the values String array with the given attributespecification (by asid).
     * Returns them as a JSONArray.
     *
     * @param values a String array representation of the values to create
     * attributevalueprincipals with.
     * @param asid the id of the attributespecification to create the values
     * with.
     * @return JSONArray with all the created attributevalueprincipals in it.
     */
    public static JSONArray createAttributevalueprincipals(String[] values, int asid) {
        // Array for the return value
        JSONArray attributevalues = new JSONArray();
        // For every value it creates a json object and calls the /api/attributevalueprincipals/{asid}
        for (String value : values) {
            JSONObject json = new JSONObject();
            json.put("value", value);
            attributevalues.put(json);

            persistent.call(
                    Const.Api.ATTRIBUTEVALUEPRINCIPALS_ASID,
                    BasicCall.REST.POST,
                    json.toString(),
                    asid, asid);

            try {
                Assume.assumeTrue(
                        persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + Organization.class.getName()
                        + " the POST call on /api/attributevalueprincipals failed");
                fail("POST /api/attributevalueprincipals was unsuccessful.");
            }
        }
        return attributevalues;
    }
}
