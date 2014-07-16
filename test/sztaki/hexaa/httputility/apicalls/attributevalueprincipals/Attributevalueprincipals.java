package sztaki.hexaa.httputility.apicalls.attributevalueprincipals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.internal.AssumptionViolatedException;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.organizations.Organization;

public class Attributevalueprincipals extends CleanTest {

    /**
     * Creates as many attributevalueprincipals as many value is specified in
     * the values String array. Returns them as a JSONArray.
     *
     * @param values a String array representation of the values to create
     * attributevalueprincipals with.
     * @return JSONArray with all the created attributevalueprincipals in it.
     */
    public static JSONArray createOrganization(String[] values) {
        JSONArray attributevalues = new JSONArray();

        for (String value : values) {
            JSONObject json = new JSONObject();
            json.put("value", value);
            attributevalues.put(json);
//TODO: ez így még nincs kész, át kell nézni és be kell fejezni, kell bele attributespecs id-ja is
            persistent.call(
                    Const.Api.ATTRIBUTEVALUEPRINCIPALS_ASID,
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
        return attributevalues;
    }
}
