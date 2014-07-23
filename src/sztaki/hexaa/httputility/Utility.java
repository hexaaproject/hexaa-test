package sztaki.hexaa.httputility;

import java.time.LocalDate;
import java.time.ZoneId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;

/**
 * Utility class for various tasks through the API. Use it with caution, may
 * cause problems as it is not testing for anything, the pre-existing objects
 * have to be provided for it.
 */
public class Utility {

    /**
     * BasicCall to be used inside the method, static.
     */
    static protected BasicCall persistent = new BasicCall();

    /**
     * Utility class within Utility for creation methods.
     */
    public static class Create {

        /**
         * Creates as many attributevalueorganizations as many value is
         * specified in the values String array with the given
         * attributespecification (by asid). Returns them as a JSONArray.
         *
         * @param values a String array representation of the values to create
         * attributevalueorganizations with.
         * @param asid the id of the attributespecification to create the values
         * with.
         * @param orgId the id of the organizations to get the
         * attributespecvalues.
         * @return JSONArray with all the created attributevalueorganizations in
         * it.
         */
        public static JSONArray attributevalueorganizations(String[] values, int asid, int orgId) {
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

            }
            return attributevalues;
        }

        /**
         * Creates as many attributevalueprincipals as many value is specified
         * in the values String array with the given attributespecification (by
         * asid). Returns them as a JSONArray.
         *
         * @param values a String array representation of the values to create
         * attributevalueprincipals with.
         * @param asid the id of the attributespecification to create the values
         * with.
         * @return JSONArray with all the created attributevalueprincipals in
         * it.
         */
        public static JSONArray attributevalueprincipals(String[] values, int asid) {
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

            }
            return attributevalues;
        }

        /**
         * Creates as many attributespecs as many oid is specified in the oids
         * String array. Returns them as a JSONArray. Can create attributespecs
         * with unique oids only.
         *
         * @param oids a String array representation of the names to create
         * organizations with.
         * @return JSONArray with all the created organizations in it.
         */
        public static JSONArray attributespecs(String[] oids) {
            JSONArray attributespecs = new JSONArray();

            for (String oid : oids) {
                JSONObject json = new JSONObject();
                json.put("oid", oid);
                json.put("friendly_name", "testFriendlyName" + oid);
                json.put("syntax", "syntaxTest");
                json.put("is_multivalue", false);

                attributespecs.put(json);

                persistent.call(
                        Const.Api.ATTRIBUTESPECS,
                        BasicCall.REST.POST,
                        json.toString());
            }
            return attributespecs;
        }

        /**
         * Creates entitlements for the names in the array with the service
         * specified by the serviceId.
         *
         * @param serviceId the id for the service.
         * @param names array of strings with the names of the objects to
         * create.
         * @return JSONArray with the created entitlements.
         */
        public static JSONArray entitlements(int serviceId, String[] names) {
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

            }
            return entitlements;
        }

        /**
         * Creates entitlementpacks for the names in the array with the service
         * specified by the serviceId. For testing purposes the ones that are
         * created with even ids are private, the ones with odd ids are public.
         *
         * @param serviceId the id for the service.
         * @param names array of strings with the names of the objects to
         * create.
         * @return JSONArray with the created entitlementpacks.
         */
        public static JSONArray entitlementpacks(int serviceId, String[] names) {
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

            }
            return entitlementpacks;
        }

        /**
         * Creates as many organizations as many name is specified in the names
         * String array. Returns them as a JSONArray. Can create organizations
         * with unique names only.
         *
         * @param names a String array representation of the names to create
         * organizations with.
         * @return JSONArray with all the created organizations in it.
         */
        public static JSONArray organizations(String[] names) {
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

            }
            return organizations;
        }

        /**
         * Creates as many principals as many fedid is specified in the fedids
         * String array. Returns them as a JSONArray. Can create principals with
         * unique fedids only. The principals email address will look like
         * fedid@email.something.
         *
         * @param fedids string array representation of the fedids to create
         * principals with.
         * @return JSONArray with all the created organizations in it.
         */
        public static JSONArray principals(String[] fedids) {
            JSONArray response = new JSONArray();

            for (String fedid : fedids) {
                JSONObject json = new JSONObject();
                json.put("fedid", fedid);
                json.put("email", fedid + "@email.something");

                persistent.call(Const.Api.PRINCIPALS, BasicCall.REST.POST, json.toString());
                if (persistent.getStatusLine().contains("201")) {
                    response.put(json);
                }
            }
            return response;
        }

        /**
         * Creates as many roles as many name is specified in the names String
         * array. Returns them as a JSONArray. Can create roles with unique
         * names only.
         *
         * @param names array of names to create roles with.
         * @param orgId organizations id to create the roles to.
         * @return JSONArray of the POST-ed roles.
         */
        public static JSONArray roles(String[] names, int orgId) {
            JSONArray response = new JSONArray();

            for (String name : names) {
                // Creates the JSON object
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("start_date", LocalDate.now(ZoneId.of("UTC")).toString());
                // POSTs the role
                persistent.call(
                        Const.Api.ORGANIZATIONS_ID_ROLES,
                        BasicCall.REST.POST,
                        json.toString(),
                        1, 1);
                // Just to match the servers format
                json.put("start_date", json.getString("start_date").concat("T00:00:00+0000"));
                response.put(json);
            }

            return response;
        }

        /**
         * Creates services for all the names in the array, if there are enough
         * entytids in the system. Usage: do not use it on a database where are
         * already existing services, as it will cause 400 Bad Requests and will
         * fail the test class.
         *
         * @param names array of strings with the names of the objects to
         * create.
         * @return A JSONArray populated by the data of the created services.
         */
        public static JSONArray services(String[] names) {
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

            }

            return services;
        }
    }

    /**
     * Utility class within Utility for linking methods.
     */
    public static class Link {

        /**
         * PUTs the existing entitlement specified by the entitlementId in the
         * entitlementpack specified by the packId
         *
         * @param entitlementId int: the id of the entitlement
         * @param packId int: id of the entitlementpack
         */
        public static void entitlementToPack(int entitlementId, int packId) {
            persistent.call(
                    Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                    BasicCall.REST.PUT,
                    null,
                    packId, entitlementId);
        }

        /**
         * Links already existing entitlementpacks specified in the packIds
         * array to the existing organizations specified by the orgId.
         *
         * @param orgId the id of the organizations to link.
         * @param packIds the ids of the entitlementpacks to link.
         */
        public static void entitlementpacksToOrg(int orgId, int[] packIds) {
            for (int pack : packIds) {
                // Connect one entitlementpack to an organizations
                persistent.call(
                        Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                        BasicCall.REST.PUT,
                        null,
                        orgId, pack);
                // Accept the entitlementpack
                persistent.call(
                        Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
                        BasicCall.REST.PUT,
                        null,
                        orgId, pack);

            }
        }

        /**
         * Links already existing entitlements specified in the entitlementIds
         * array to the existing role specified by the roleId.
         *
         * @param roleId the id of the role to link to.
         * @param entitlementIds the ids of the entitlements to link.
         */
        public static void entitlementsToRole(int roleId, int[] entitlementIds) {
            for (int id : entitlementIds) {
                persistent.call(
                        Const.Api.ROLES_ID_ENTITLEMENTS_EID,
                        BasicCall.REST.PUT,
                        null,
                        roleId, id);
            }

        }
    }
}
