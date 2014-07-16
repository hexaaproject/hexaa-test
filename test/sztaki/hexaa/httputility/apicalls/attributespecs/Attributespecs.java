/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.attributespecs;

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
 * Utility class that gives easy to use calls to attributespecs.
 */
public class Attributespecs extends CleanTest {

    public static int id = 1;

    /**
     * Creates as many attributespecs as many oid is specified in the oids
     * String array. Returns them as a JSONArray. Can create attributespecs with
     * unique oids only, if oids are repeating it will drop an assumption error
     * and fails the test case.
     *
     * @param oids a String array representation of the names to create
     * organizations with.
     * @return JSONArray with all the created organizations in it.
     */
    public static JSONArray createAttributespecs(String[] oids) {
        JSONArray attributespecs = new JSONArray();

        for (String oid : oids) {
            JSONObject json = new JSONObject();
            json.put("oid", oid);
            json.put("friendly_name", "testFriendlyName" + Integer.toString(id++));
            json.put("syntax", "syntaxTest");
            json.put("is_multivalue", false);

            attributespecs.put(json);

            persistent.call(
                    Const.Api.ATTRIBUTESPECS,
                    BasicCall.REST.POST,
                    json.toString());

            try {
                Assume.assumeTrue(
                        persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + Organization.class.getName()
                        + " the POST call on /api/attributespecs failed");
                fail("POST /api/attributespecs was unsuccessful.");
            }
        }
        return attributespecs;
    }
}
