package sztaki.hexaa.httputility.apicalls.services;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.internal.AssumptionViolatedException;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Utility class to inherit from for Services related Test classes and gives
 * three easy to use calls to create services, entitlements and
 * entitlementpacks.
 */
public class Services extends CleanTest {

    /**
     * Creates services for all the names in the array, if there are enough
     * entytids in the system. Usage: do not use it on a database where are
     * already existing services, as it will cause 400 Bad Requests and will
     * fail the test class.
     *
     * @param names array of strings with the names of the objects to create.
     * @return A JSONArray populated by the data of the created services.
     */
    public static JSONArray createServices(String[] names) {
        JSONArray services = new JSONArray();
        // GET the existing entityids
        JSONArray jsonEntityArray = (JSONArray) JSONParser.parseJSON(
                persistent.call(
                        Const.Api.ENTITYIDS,
                        BasicCall.REST.GET,
                        null,
                        0,
                        0));
        int i = 0;
        for (String name : names) {
            // Creates the first json object to be POSTed on the server
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("entityid", jsonEntityArray.getString(i++));
            json.put("url", "test." + name + ".test");
            json.put("description", "This is a test service for the " + jsonEntityArray.getString(0) + "service provider entity.");
            // POSTs the json object
            persistent.call(
                    Const.Api.SERVICES,
                    BasicCall.REST.POST,
                    json.toString(),
                    0, 0);

            services.put(json);

            // Fail the test class if the BeforeClass was unsuccessful at creating the necessary services
            try {
                Assume.assumeTrue(persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + Services.class.getName()
                        + " the POST call on /api/services failed");
                fail("POST /api/services was unsuccessful.");
            }
        }

        return services;
    }

    /**
     * Creates entitlements for the names in the array with the service
     * specified by the serviceId.
     *
     * @param serviceId the id for the service.
     * @param names array of strings with the names of the objects to create.
     * @return JSONArray with the created entitlements.
     */
    public static JSONArray createServiceEntitlements(int serviceId, String[] names) {
        // JSONArray to store the return value
        JSONArray entitlements = new JSONArray();
        // Verifying the existance of the service
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.SERVICES_ID,
                                BasicCall.REST.GET,
                                null,
                                serviceId, 0));
        try {
            Assume.assumeTrue(persistent.getStatusLine().equals("HTTP/1.1 200 OK"));
        } catch (AssumptionViolatedException e) {
            System.out.println(
                    "In "
                    + Services.class.getName()
                    + " the suplied serviceId does not exist");
            fail("POST /api/services/{id}/entitlements was unsuccessful.");
        }

        for (String name : names) {

            // Creating the entitlement object
            JSONObject json = new JSONObject();
            json.put("uri", "/testUri" + name);
            json.put("name", name);
            json.put("description", "This is a test entitlement, for the #" + Integer.toString(serviceId) + " service, with name " + name);
            // Store it
            entitlements.put(json);
            // POST it
            persistent.call(
                    Const.Api.SERVICES_ID_ENTITLEMENTS,
                    BasicCall.REST.POST,
                    json.toString(),
                    serviceId, 0);
            // Fail the test class if the BeforeClass was unsuccessful at creating the necessary services
            try {
                Assume.assumeTrue(persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + Services.class.getName()
                        + " the POST call on /api/services/{id}/entitlements failed");
                fail("POST /api/services/{id}/entitlements was unsuccessful.");
            }
        }
        return entitlements;
    }

    /**
     * Creates entitlementpacks for the names in the array with the service
     * specified by the serviceId. For testing purposes the ones that are
     * created with even ids are private, the ones with odd ids are public.
     *
     * @param serviceId the id for the service.
     * @param names array of strings with the names of the objects to create.
     * @return JSONArray with the created entitlementpacks.
     */
    public static JSONArray createServiceEntitlementpacks(int serviceId, String[] names) {
        // JSONArray to store the return value
        JSONArray entitlementpacks = new JSONArray();
        // Verifying the existance of the service
        JSONObject jsonResponse
                = (JSONObject) JSONParser.parseJSON(
                        persistent.call(
                                Const.Api.SERVICES_ID,
                                BasicCall.REST.GET,
                                null,
                                serviceId, 0));
        try {
            Assume.assumeTrue(persistent.getStatusLine().equals("HTTP/1.1 200 OK"));
        } catch (AssumptionViolatedException e) {
            System.out.println(
                    "In "
                    + Services.class.getName()
                    + " the suplied serviceId does not exist");
            fail("POST /api/services/{id}/entitlementpacks was unsuccessful.");
        }

        int i = 0;
        for (String name : names) {

            // Creating the entitlementpack object
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("description", "This is a test entitlement, for the #" + Integer.toString(serviceId) + " service, with name " + name);
            // The ones with even id are private, the ones with odd ids are public
            if (i++ % 2 == 1) {
                json.put("type", "private");
            } else {
                json.put("type", "public");
            }
            // Store it
            entitlementpacks.put(json);
            // POST it
            persistent.call(
                    Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
                    BasicCall.REST.POST,
                    json.toString(),
                    serviceId, 0);
            // Fail the test class if the BeforeClass was unsuccessful at creating the necessary services
            try {
                Assume.assumeTrue(persistent.getStatusLine().equalsIgnoreCase("HTTP/1.1 201 Created"));
            } catch (AssumptionViolatedException e) {
                System.out.println(
                        "In "
                        + Services.class.getName()
                        + " the POST call on /api/services/{id}/entitlementpacks failed");
                fail("POST /api/services/{id}/entitlementpacks was unsuccessful.");
            }
        }
        return entitlementpacks;
    }
}
