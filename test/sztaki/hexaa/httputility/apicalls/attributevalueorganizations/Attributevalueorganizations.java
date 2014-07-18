package sztaki.hexaa.httputility.apicalls.attributevalueorganizations;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.internal.AssumptionViolatedException;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;

/**
 * Utility class for attributevalueorganizations for easy to use creation.
 */
public class Attributevalueorganizations extends CleanTest {

    /**
     * Creates as many attributevalueorganizations as many value is specified in
     * the values String array with the given attributespecification (by asid).
     * Returns them as a JSONArray.
     *
     * @param values a String array representation of the values to create
     * attributevalueorganizations with.
     * @param asid the id of the attributespecification to create the values
     * with.
     * @param orgId the id of the organization to get the attributespecvalues.
     * @return JSONArray with all the created attributevalueorganizations in it.
     */
    public static JSONArray createAttributevalueorganizations(String[] values, int asid, int orgId) {
        // Array for the return value
        JSONArray attributevalues = new JSONArray();
        // For every value it creates a json object and calls the /api/organizations/{id}/attributevalueorganizations/{asid}
        for (String value : values) {
            JSONObject json = new JSONObject();
            json.put("value", value);
            attributevalues.put(json);

            persistent.call(
                    Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATIONS_ASID,
                    BasicCall.REST.POST,
                    json.toString(),
                    orgId, asid);

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
