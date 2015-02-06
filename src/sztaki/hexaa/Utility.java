package sztaki.hexaa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for various tasks through the API. Use it with caution, may
 * cause problems as it is not testing for anything, the pre-existing objects
 * have to be provided for it.
 */
public class Utility {

	/**
	 * BasicCall to be used inside the method, static.
	 */
	static public BasicCall persistent = new BasicCall();

	/**
	 * 
	 */
	static private boolean isAdmin = true;

	/**
	 * Disables the auto admin feature for the next call.
	 */
	public static void nonAdmin() {
		isAdmin = false;
	}

	/**
	 * Re-enables the auto admin feature.
	 */
	public static void isAdmin() {
		isAdmin = true;
	}

	/**
	 * Utility class within Utility for creation methods.
	 */
	public static class Create {

		/**
		 * Creates as many attributevalueorganizations as many value is
		 * specified in the values String array with the given
		 * attributespecification (by asid). Returns them as a JSONArray.
		 *
		 * @param values
		 *            a String array representation of the values to create
		 *            attributevalueorganizations with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @param orgId
		 *            the id of the organization to get the attributespecvalues.
		 * @param services
		 *            int array of service ids.
		 * @return JSONArray with all the created attributevalueorganizations in
		 *         it.
		 */
		public static JSONArray attributevalueorganization(String[] values,
				int asid, int orgId, int[] services) {
			// Array for the return value
			JSONArray attributevalues = new JSONArray();
			// For every value it creates a json object and calls the
			// /api/organizations/{id}/attributevalueorganizations/{asid}
			for (String value : values) {
				JSONObject json = new JSONObject();
				json.put("value", value);
				json.put("services", new JSONArray(services));
				json.put("attribute_spec", asid);
				json.put("organization", orgId);

				persistent.call(Const.Api.ATTRIBUTEVALUEORGANIZATIONS,
						BasicCall.REST.POST, json.toString());

				if (persistent.getHeader("Location") != null) {
					List<Integer> id = getNumber(persistent.getHeader(
							"Location").getValue());

					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					attributevalues.put(json);
				}

			}
			return attributevalues;
		}

		/**
		 * Alternative call for
		 * {@link #attributevalueorganization(String[], int, int)} for single
		 * attributevalueorganization creation.
		 *
		 * @param values
		 *            a String array representation of the values to create
		 *            attributevalueorganizations with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @param orgId
		 *            the id of the organization to get the attributespecvalues.
		 * @return JSONArray with all the created attributevalueorganizations in
		 *         it.
		 */
		public static JSONArray attributevalueorganization(String[] values,
				int asid, int orgId) {
			return attributevalueorganization(values, asid, orgId, new int[] {});
		}

		/**
		 * Alternative call for
		 * {@link #attributevalueorganization(String[], int, int)} for single
		 * attributevalueorganization creation.
		 *
		 * @param value
		 *            the value to create attributevalueorganization with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @param orgId
		 *            the id of the organization to get the attributespecvalues.
		 * @return JSONArray with all the created attributevalueorganizations in
		 *         it.
		 */
		public static JSONArray attributevalueorganization(String value,
				int asid, int orgId) {
			return attributevalueorganization(new String[] { value }, asid,
					orgId);
		}

		/**
		 * Alternative call for
		 * {@link #attributevalueorganization(String[], int, int)} for single
		 * attributevalueorganization creation.
		 *
		 * @param value
		 *            the value to create attributevalueorganization with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @param orgId
		 *            the id of the organization to get the attributespecvalues.
		 * @param service
		 *            int array of the service ids.
		 * @return JSONArray with all the created attributevalueorganizations in
		 *         it.
		 */
		public static JSONArray attributevalueorganization(String value,
				int asid, int orgId, int[] service) {
			return attributevalueorganization(new String[] { value }, asid,
					orgId, service);
		}

		/**
		 * Creates as many attributevalueprincipals as many value is specified
		 * in the values String array with the given attributespecification (by
		 * asid). Returns them as a JSONArray.
		 *
		 * @param values
		 *            a String array representation of the values to create
		 *            attributevalueprincipals with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @param services
		 *            array of services to add to, the array can have 0 items.
		 * @return JSONArray with all the created attributevalueprincipals in
		 *         it.
		 */
		public static JSONArray attributevalueprincipal(String[] values,
				int asid, int[] services) {
			JSONArray attributevalues = new JSONArray();
			for (String value : values) {
				JSONObject json = new JSONObject();
				json.put("value", value);
				json.put("services", new JSONArray(services));
				json.put("attribute_spec", asid);

				persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS,
						BasicCall.REST.POST, json.toString());

				if (persistent.getHeader("Location") != null) {
					List<Integer> id = getNumber(persistent.getHeader(
							"Location").getValue());

					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					attributevalues.put(json);
				}
			}
			return attributevalues;
		}

		/**
		 * Alternative call for {@link attributevalueprincipal(String[] values,
		 * int asid, int[] services)} for single attributevalueorganization
		 * creation.
		 *
		 * @param value
		 *            the value to create attributevalueprincipals with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @return JSONArray with all the created attributevalueprincipals in
		 *         it.
		 */
		public static JSONArray attributevalueprincipal(String value, int asid) {
			return attributevalueprincipal(new String[] { value }, asid,
					new int[] {});
		}

		/**
		 * Alternative call for {@link attributevalueprincipal(String[] values,
		 * int asid, int[] services)} for single attributevalueorganization
		 * creation.
		 *
		 * @param values
		 *            the values to create attributevalueprincipals with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @return JSONArray with all the created attributevalueprincipals in
		 *         it.
		 */
		public static JSONArray attributevalueprincipal(String[] values,
				int asid) {
			return attributevalueprincipal(values, asid, new int[] {});
		}

		/**
		 * Alternative call for {@link attributevalueprincipal(String[] values,
		 * int asid, int[] services)} for single attributevalueorganization
		 * creation.
		 *
		 * @param value
		 *            the value to create attributevalueprincipals with.
		 * @param asid
		 *            the id of the attributespecification to create the values
		 *            with.
		 * @param services
		 *            array of services to add to, the array can have 0 items.
		 * @return JSONArray with all the created attributevalueprincipals in
		 *         it.
		 */
		public static JSONArray attributevalueprincipal(String value, int asid,
				int[] services) {
			return attributevalueprincipal(new String[] { value }, asid,
					services);
		}

		/**
		 * Creates as many attributespecs as many oid is specified in the oids
		 * String array. Returns them as a JSONArray. Can create attributespecs
		 * with unique oids only.
		 *
		 * @param uri
		 *            a String array representation of the names to create
		 *            attributespecs with.
		 * @param maintainer
		 *            String should be user or manager according to the usage:
		 *            use the user if it is used by a principal, use manager if
		 *            it is used by an organization.
		 * @return JSONArray with all the created attributespecs in it.
		 */
		public static JSONArray attributespec(String[] uri, String maintainer) {
			JSONArray attributespecs = new JSONArray();

			// for (int i = 0; i < oids.length; i++) {
			// if (oids[i].length() <= 3) {
			// oids[i] = "oid".concat(oids[i]);
			// }
			// }

			for (String oid : uri) {
				JSONObject json = new JSONObject();
				json.put("uri", oid);
				json.put("name", "testFriendlyName" + oid);
				json.put("syntax", "string");
				json.put("is_multivalue", true);
				json.put("maintainer", maintainer);

				if (isAdmin == true) {
					persistent.isAdmin = true;
				}
				persistent.call(Const.Api.ATTRIBUTESPECS, BasicCall.REST.POST,
						json.toString());

				if (persistent.getHeader("Location") != null) {
					String locHeader = persistent.getHeader("Location")
							.getValue();
					// System.out.println(locHeader);
					List<Integer> id = getNumber(locHeader);

					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					attributespecs.put(json);
				}
			}
			return attributespecs;
		}

		/**
		 * Alternative call for {@link attributespec(String[] oids)} for single
		 * attributespec creation.
		 *
		 * @param oid
		 *            the names to create attributespec with. If it is less than
		 *            3 character "oid" will be concatenated to the beginning of
		 *            the string.
		 * @param user
		 *            String should be user or manager according to the usage:
		 *            use the user if it is used by a principal, use manager if
		 *            it is used by an organization.
		 * @return JSONArray with the created attributespec in it.
		 */
		public static JSONArray attributespec(String oid, String user) {
			return attributespec(new String[] { oid }, user);
		}

		/**
		 * Creates entitlements for the names in the array with the service
		 * specified by the serviceId.
		 *
		 * @param serviceId
		 *            the id for the service.
		 * @param names
		 *            array of strings with the names of the objects to create.
		 * @return JSONArray with the created entitlements.
		 */
		public static JSONArray entitlements(int serviceId, String[] names) {
			// JSONArray to store the return value
			JSONArray entitlements = new JSONArray();

			for (String name : names) {

				// Creating the entitlement object
				JSONObject json = new JSONObject();
				json.put("uri", Const.URI_PREFIX + Integer.toString(serviceId)
						+ ":" + "testUri" + name);
				json.put("name", name);
				json.put("description", "This is a test entitlement, for the #"
						+ Integer.toString(serviceId) + " service, with name "
						+ name);

				// POST it
				persistent.call(Const.Api.SERVICES_ID_ENTITLEMENTS,
						BasicCall.REST.POST, json.toString(), serviceId, 0);

				if (persistent.getHeader("Location") != null) {
					String locHeader = persistent.getHeader("Location")
							.getValue();
					// System.out.println(locHeader);
					List<Integer> id = getNumber(locHeader);

					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					// Store it
					entitlements.put(json);
				}

			}
			return entitlements;
		}

		/**
		 * Alternative call for {@link entitlements(int serviceId, String[]
		 * names)} for single entitlement.
		 *
		 * @param serviceId
		 *            the id for the service.
		 * @param names
		 *            the name of the object to create.
		 * @return JSONArray with the created entitlements.
		 */
		public static JSONArray entitlements(int serviceId, String names) {
			return entitlements(serviceId, new String[] { names });
		}

		public static JSONArray entitlementpacks(int serviceId, String[] names) {
			return entitlementpacks(serviceId, names, true);
		}

		public static JSONArray entitlementpacksPrivate(int serviceId,
				String[] names) {
			return entitlementpacks(serviceId, names, false);
		}

		/**
		 * Creates entitlementpacks for the names in the array with the service
		 * specified by the serviceId. All entitlementpacks created is public.
		 *
		 * @param serviceId
		 *            the id for the service.
		 * @param names
		 *            array of strings with the names of the objects to create.
		 * @return JSONArray with the created entitlementpacks.
		 */
		public static JSONArray entitlementpacks(int serviceId, String[] names,
				boolean isPublic) {
			// JSONArray to store the return value
			JSONArray entitlementpacks = new JSONArray();

			for (String name : names) {

				// Creating the entitlementpack object
				JSONObject json = new JSONObject();
				json.put("name", name);
				json.put("description", "This is a test entitlement, for the #"
						+ Integer.toString(serviceId) + " service, with name "
						+ name);
				if (isPublic) {
					json.put("type", "public");
				} else {
					json.put("type", "private");
				}

				// POST it
				persistent.call(Const.Api.SERVICES_ID_ENTITLEMENTPACKS,
						BasicCall.REST.POST, json.toString(), serviceId, 0);

				if (persistent.getHeader("Location") != null) {

					List<Integer> numbers = getNumber(persistent.getHeader(
							"Location").getValue());
					json.put("id", numbers.get(numbers.size() - 1));

					// Store it
					entitlementpacks.put(json);
				}
			}
			return entitlementpacks;
		}

		/**
		 * Alternative call for {@link entitlementpacks(int serviceId, String[]
		 * names)} for single entitlementpack.
		 *
		 * @param serviceId
		 *            the id for the service.
		 * @param names
		 *            string with the name of the object to create.
		 * @return JSONArray with the created entitlementpacks.
		 */
		public static JSONArray entitlementpacks(int serviceId, String names) {
			return entitlementpacks(serviceId, new String[] { names });
		}

		/**
		 * Creates a new invitation with the specified parameters. Use the
		 * specific invitation methods if exist. Some of the parameters are not
		 * required, these parameters can be null or 0, for more information see
		 * the individual parameters or the API documentation.
		 *
		 * @param emails
		 *            array of strings, must contain valid email addresses, can
		 *            be null.
		 * @param url
		 *            string, must contain a valid url, can be null.
		 * @param redirect
		 *            boolean, can not be null, false by default.
		 * @param as_manager
		 *            boolean, can not be null, false by default.
		 * @param message
		 *            string, required, if null the request will fail (with 400
		 *            Bad Request)
		 * @param start_date
		 *            LocalDateTime, can be null.
		 * @param end_date
		 *            LocalDateTime, can be null.
		 * @param limit
		 *            int, empty=infinite by default, can be 0.
		 * @param locale
		 *            string, can be null.
		 * @param role
		 *            int, the id of the role to invite to, requires
		 *            organization, can be 0.
		 * @param organization
		 *            int, the id of the organization to invite to, can be null.
		 * @param service
		 *            int, the id of the service to invite to, can be null.
		 * @return JSONObject, the json object sent as the request body.
		 */
		public static JSONObject invitation(String[] emails, String url,
				boolean redirect, boolean as_manager, String message,
				LocalDateTime start_date, LocalDateTime end_date, int limit,
				String locale, int role, int organization, int service) {
			JSONObject json = new JSONObject();
			if (emails != null) {
				json.put("emails", emails);
			}
			if (url != null) {
				json.put("landing_url", url);
			}
			if (redirect != false) {
				json.put("do_redirect", redirect);
			}
			if (as_manager != false) {
				json.put("as_manager", as_manager);
			}
			if (message != null) {
				json.put("message", message);
			}
			if (start_date != null) {
				json.put("start_date", start_date);
			}
			if (end_date != null) {
				json.put("end_date", end_date);
			}
			if (limit != 0) {
				json.put("limit", limit);
			}
			if (locale != null) {
				json.put("locale", locale);
			}
			if (role != 0) {
				json.put("role", role);
			}
			if (organization != 0) {
				json.put("organization", organization);
			}
			if (service != 0) {
				json.put("service", service);
			}

			persistent.call(Const.Api.INVITATIONS, BasicCall.REST.POST,
					json.toString());

			if (persistent.getHeader("Location") != null) {

				List<Integer> numbers = getNumber(persistent.getHeader(
						"Location").getValue());
				json.put("id", numbers.get(numbers.size() - 1));
			}

			return json;
		}

		/**
		 * Creates a new invitation to the specified organization (and role).
		 * The unnecessary parameters are omitted. Some of the parameters are
		 * not required, these parameters can be null or 0, for more information
		 * see the individual parameters or the API documentation.
		 *
		 * @param emails
		 *            array of strings, must contain valid email addresses, can
		 *            be null.
		 * @param url
		 *            string, must contain a valid url, can be null.
		 * @param message
		 *            string, required, if null the request will fail (with 400
		 *            Bad Request)
		 * @param role
		 *            int, the id of the role to invite to, requires
		 *            organization, can be 0.
		 * @param organization
		 *            int, the id of the organization to invite to, can be null.
		 * @return JSONObject, the json object sent as the request body.
		 */
		public static JSONObject invitationToOrg(String[] emails, String url,
				String message, int role, int organization) {
			return invitation(emails, url, false, false, message, null, null,
					0, null, role, organization, 0);
		}

		/**
		 * Creates a new invitation to the specified service. The unnecessary
		 * parameters are omitted. Some of the parameters are not required,
		 * these parameters can be null or 0, for more information see the
		 * individual parameters or the API documentation.
		 *
		 * @param emails
		 *            array of strings, must contain valid email addresses, can
		 *            be null.
		 * @param url
		 *            string, must contain a valid url, can be null.
		 * @param message
		 *            string, required, if null the request will fail (with 400
		 *            Bad Request)
		 * @param service
		 *            int, the id of the service to invite to, can be null.
		 * @return JSONObject, the json object sent as the request body.
		 */
		public static JSONObject invitationToService(String[] emails,
				String url, String message, int service) {
			return invitation(emails, url, false, false, message, null, null,
					0, null, 0, 0, service);
		}

		/**
		 * Creates as many organization as many name is specified in the names
		 * String array. Returns them as a JSONArray. Can create organization
		 * with unique names only.
		 *
		 * @param names
		 *            a String array representation of the names to create
		 *            organization with.
		 * @return JSONArray with all the created organization in it.
		 */
		public static JSONArray organization(String[] names) {
			JSONArray organizations = new JSONArray();

			for (String name : names) {
				JSONObject json = new JSONObject();
				json.put("name", name);

				persistent.call(Const.Api.ORGANIZATIONS, BasicCall.REST.POST,
						json.toString(), 1, 1);
				System.out.println(persistent.getHeader("Location"));
				if (persistent.getHeader("Location") != null) {
					List<Integer> id = getNumber(persistent.getHeader(
							"Location").getValue());
					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					organizations.put(json);
				}

			}
			return organizations;
		}

		/**
		 * Alternative for the {@link #organization(String[])} for single
		 * organization creation.
		 *
		 * @param name
		 *            the names to create organization with.
		 * @return JSONArray with all the created organization in it.
		 */
		public static JSONArray organization(String name) {
			return organization(new String[] { name });
		}

		/**
		 * Creates as many principal as many fedid is specified in the fedids
		 * String array. Returns them as a JSONArray. Can create principal with
		 * unique fedids only. The principal email address will look like
		 * fedid@email.something.
		 *
		 * @param fedids
		 *            string array representation of the fedids to create
		 *            principal with.
		 * @return JSONArray with all the created organization in it.
		 */
		public static JSONArray principal(String[] fedids) {
			JSONArray response = new JSONArray();

			for (String fedid : fedids) {
				JSONObject json = new JSONObject();
				json.put("fedid", fedid);
				json.put("email", fedid + "@email.something");
				json.put("display_name", fedid + "_name");

				persistent.call(Const.Api.PRINCIPALS, BasicCall.REST.POST,
						json.toString());

				if (persistent.getHeader("Location") != null) {
					List<Integer> id = getNumber(persistent.getHeader(
							"Location").getValue());

					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					response.put(json);
				}
			}
			return response;
		}

		/**
		 * Alternative call for {@link #principal(String[])} for single
		 * principal creation.
		 *
		 * @param fedid
		 *            the fedid to create principal with.
		 * @return JSONArray with all the created organization in it.
		 */
		public static JSONArray principal(String fedid) {
			return principal(new String[] { fedid });
		}

		/**
		 * Creates as many role as many name is specified in the names String
		 * array. Returns them as a JSONArray. Can create role with unique names
		 * only.
		 *
		 * @param names
		 *            array of names to create role with.
		 * @param orgId
		 *            organization id to create the role to.
		 * @return JSONArray of the POST-ed role.
		 */
		public static JSONArray role(String[] names, int orgId) {
			JSONArray response = new JSONArray();

			for (String name : names) {
				// Creates the JSON object
				JSONObject json = new JSONObject();
				json.put("name", name);
				// json.put("start_date",
				// LocalDate.now(ZoneId.of("UTC")).toString());
				// POSTs the role
				persistent.call(Const.Api.ORGANIZATIONS_ID_ROLES,
						BasicCall.REST.POST, json.toString(), orgId, 1);

				if (persistent.getHeader("Location") != null) {
					List<Integer> id = getNumber(persistent.getHeader(
							"Location").getValue());

					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					response.put(json);
				}
			}

			return response;
		}

		/**
		 * Alternative call for {@link #role(String[], int)} for single role
		 * creation.
		 *
		 * @param name
		 *            name to create a role with.
		 * @param orgId
		 *            organization id to create the role to.
		 * @return JSONArray of the POST-ed role.
		 */
		public static JSONArray role(String name, int orgId) {
			return role(new String[] { name }, orgId);
		}

		/**
		 * Creates services for all the names in the array, if there are enough
		 * entityids in the system. Usage: do not use it on a database where are
		 * existing services, as it will cause 400 Bad Requests and will fail
		 * the test class.
		 *
		 * @param names
		 *            array of strings with the names of the services to create.
		 * @return A JSONArray populated by the data of the created services.
		 */
		public static JSONArray service(String[] names) {
			JSONArray services = new JSONArray();

			for (String name : names) {
				// Creates the json object to be POSTed on the server
				JSONObject json = new JSONObject();
				json.put("name", name);
				json.put("entityid", "https://example.com/ssp");
				json.put("url", "test." + name + ".test");
				json.put("description", "This is a test service for the "
						+ "https://example.com/ssp"
						+ "service provider entity.");
				// POSTs the json object
				persistent.call(Const.Api.SERVICES, BasicCall.REST.POST,
						json.toString(), 0, 0);

				if (persistent.getHeader("Location") != null) {
					String locHeader = persistent.getHeader("Location")
							.getValue();
					// System.out.println(locHeader);
					List<Integer> id = getNumber(locHeader);
					System.out.println("\t\t" + locHeader);
					if (id.size() == 1) {
						json.put("id", id.get(0));
					}

					services.put(json);

					BasicCall enableService = new BasicCall();
					enableService.setToken(new DatabaseManipulator()
							.getServiceEnableToken());
					enableService.call(Const.Api.SERVICES_TOKEN_ENABLE,
							BasicCall.REST.PUT);
					if (enableService.getStatusLine().contains(
							Const.StatusLine.Created)) {
						// System.out.println("Service enabled!");
					} else {
						System.out.println(enableService.getStatusLine());
					}
				}
			}

			return services;
		}

		/**
		 * Alternative call for {@link #service(String[])} for single role
		 * creation.
		 *
		 * @param name
		 *            the name of the service to create.
		 * @return JSONArray of the POST-ed service.
		 */
		public static JSONArray service(String name) {
			return Create.service(new String[] { name });
		}

		/**
		 *
		 * @param enable_entitlements
		 * @param enable_attribute_specs
		 * @param principal
		 * @param service
		 * @return
		 */
		public static JSONArray consent(boolean enable_entitlements,
				int[] enable_attribute_specs, int principal, int service) {
			JSONArray consents = new JSONArray();

			JSONObject json = new JSONObject();

			json.put("enable_entitlements", enable_entitlements);
			json.put("enabled_attribute_specs", enable_attribute_specs);
			if (principal > 0) {
				json.put("principal", principal);
			}
			json.put("service", service);

			persistent.call(Const.Api.CONSENTS, BasicCall.REST.POST,
					json.toString());

			json.remove("enabled_attribute_specs");
			json.put("enabled_attribute_spec_ids", new JSONArray(
					enable_attribute_specs));
			json.put("service_id", json.remove("service"));

			consents.put(json);

			return consents;
		}

		public static JSONArray consent(boolean enable_entitlements,
				int[] enable_attribute_specs, int service) {
			return consent(enable_entitlements, enable_attribute_specs, 0,
					service);
		}

	}

	/**
	 * Utility class within Utility for linking methods.
	 */
	public static class Link {

		/**
		 * Links the existing entitlements specified by the entitlements in the
		 * entitlementpack specified by the packId, using the array method.
		 *
		 * @param entitlements
		 *            array of entitlement ids
		 * @param packId
		 *            int: id of the entitlementpack
		 */
		public static void entitlementToPackByArray(int[] entitlements,
				int packId) {
			JSONObject json = new JSONObject();
			json.put("entitlements", entitlements);
			persistent.call(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENT,
					BasicCall.REST.PUT, json.toString(), packId, packId);
		}

		/**
		 * Links the existing entitlement specified by the entitlementId in the
		 * entitlementpack specified by the packId
		 *
		 * @param entitlementId
		 *            int: the id of the entitlement
		 * @param packId
		 *            int: id of the entitlementpack
		 */
		public static void entitlementToPack(int entitlementId, int packId) {
			persistent.call(Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
					BasicCall.REST.PUT, null, packId, entitlementId);
		}

		/**
		 * Links an existing entitlementpack to organization, but waits for
		 * accept, so the entitlementpacks will have a pending status.
		 *
		 * @param orgId
		 *            id of the organization to link to.
		 * @param pack
		 *            id of the entitlementpack to link.
		 */
		public static void entitlementpackToOrgRequest(int orgId, int pack) {
			persistent.call(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
					BasicCall.REST.PUT, null, orgId, pack);
		}

		/**
		 * Links existing entitlementpacks specified in the packs array to the
		 * existing organization specified by the orgId, using the array method.
		 *
		 * @param orgId
		 *            id of the organization to link to.
		 * @param packs
		 *            id of the entitlementpack to link.
		 */
		public static void entitlementpackToOrgByArray(int orgId, int[] packs) {
			JSONObject json = new JSONObject();
			json.put("entitlement_packs", packs);
			persistent.call(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACK,
					BasicCall.REST.PUT, json.toString(), orgId, orgId);
		}

		/**
		 * Links existing entitlementpacks specified in the packIds array to the
		 * existing organization specified by the orgId.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param packIds
		 *            the ids of the entitlementpacks to link.
		 */
		public static void entitlementpackToOrg(int orgId, int[] packIds) {
			for (int pack : packIds) {
				// Connect one entitlementpack to an organization
				persistent.call(
						Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
						BasicCall.REST.PUT, null, orgId, pack);
				// Accept the entitlementpack
				persistent
						.call(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
								BasicCall.REST.PUT, null, orgId, pack);

			}
		}

		/**
		 * Alternative for the {@link #entitlementpackToOrg(int, int[])} for
		 * single entitlementpack.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param packIds
		 *            the ids of the entitlementpacks to link.
		 */
		public static void entitlementpackToOrg(int orgId, int packIds) {
			entitlementpackToOrg(orgId, new int[] { packIds });
		}

		/**
		 * Links existing entitlementpacks specified in the packIds array to the
		 * existing organization specified by the orgId using the token method.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param packIds
		 *            the ids of the entitlementpacks to link.
		 */
		public static void entitlementpackToOrgByToken(int orgId, int[] packIds) {
			for (int pack : packIds) {
				JSONObject json;
				try {
					if (isAdmin == true) {
						persistent.isAdmin = true;
					}
					json = persistent.getResponseJSONObject(
							Const.Api.ENTITLEMENTPACKS_ID_TOKEN,
							BasicCall.REST.GET, null, pack, pack);
				} catch (ResponseTypeMismatchException ex) {
					Logger.getLogger(Utility.class.getName()).log(Level.SEVERE,
							null, ex);
					System.out.println("The call: getResponseJSONObject"
							+ "(Const.Api.ENTITLEMENTPACKS_ID_TOKEN,"
							+ " BasicCall.REST.GET, on the id of " + pack
							+ " failed");
					return;
				}
				// GET the token.
				persistent.setToken(json.getString("token"));

				persistent.call(
						Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN,
						BasicCall.REST.PUT);
			}
		}

		/**
		 * Alternative for the {@link #entitlementpackToOrg(int, int[])} for
		 * single entitlementpack.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param packIds
		 *            the ids of the entitlementpacks to link.
		 */
		public static void entitlementpackToOrgByToken(int orgId, int packIds) {
			entitlementpackToOrgByToken(orgId, new int[] { packIds });
		}

		/**
		 * Links existing entitlementpacks specified in the packIds array to the
		 * existing organization specified by the orgId using the token method.
		 *
		 * @param orgID
		 *            the id of the organization to link.
		 * @param tokens
		 *            array of tokens of entitlementpacks.
		 */
		public static void entitlementpackToOrgByToken(int orgID,
				String[] tokens) {
			for (String token : tokens) {
				persistent.setToken(token);

				persistent.call(
						Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN,
						BasicCall.REST.PUT, null, orgID, orgID);
			}
		}

		/**
		 * Alternative for the {@link entitlementpackToOrgByToken(int orgID,
		 * String[] tokens)} for single entitlementpack.
		 *
		 * @param orgID
		 *            the id of the organization to link.
		 * @param token
		 *            token of an entitlementpack to link.
		 */
		public static void entitlementpackToOrgByToken(int orgID, String token) {
			entitlementpackToOrgByToken(orgID, new String[] { token });
		}

		/**
		 * Links existing entitlements specified in the entitlementIds array to
		 * the existing role specified by the roleId.
		 *
		 * @param roleId
		 *            the id of the role to link to.
		 * @param entitlementIds
		 *            the ids of the entitlements to link.
		 */
		public static void entitlementsToRole(int roleId, int[] entitlementIds) {
			for (int id : entitlementIds) {
				persistent.call(Const.Api.ROLES_ID_ENTITLEMENTS_EID,
						BasicCall.REST.PUT, null, roleId, id);
			}

		}

		/**
		 * Alternative call for {@link entitlementsToRole(int roleId, int[]
		 * entitlementIds)}.
		 *
		 * @param roleId
		 *            the id of the role to link to.
		 * @param entitlementId
		 *            the id of the entitlement to link.
		 */
		public static void entitlementsToRole(int roleId, int entitlementId) {
			entitlementsToRole(roleId, new int[] { entitlementId });
		}

		/**
		 * Links existing entitlements specified in the entitlementIds array to
		 * the existing role specified by the roleId, using the array method.
		 *
		 * @param roleId
		 *            id of the role to link to.
		 * @param entitlementIds
		 *            id of the entitlements to link.
		 */
		public static void entitlementsToRoleByArray(int roleId,
				int[] entitlementIds) {
			JSONObject json = new JSONObject();
			json.put("entitlements", entitlementIds);
			persistent.call(Const.Api.ROLES_ID_ENTITLEMENT, BasicCall.REST.PUT,
					json.toString(), roleId, roleId);
		}

		/**
		 * Links existing attributes specified in the attributeIds array to the
		 * existing service specified by the serviceId, using the array method.
		 *
		 * @param serviceId
		 *            id of the service to link to.
		 * @param attributeIds
		 *            id of the attributes to link, same number of elements
		 *            needed as in isPublics.
		 * @param isPublics
		 *            array of public(true) or private(false) booleans, same
		 *            number of elements needed as in attributeIds.
		 */
		public static void attributespecsToServiceSet(int serviceId,
				int[] attributeIds, boolean[] isPublics) {
			JSONObject json = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < attributeIds.length; i++) {
				JSONObject jsonTemp = new JSONObject();
				jsonTemp.put("attribute_spec", attributeIds[i]);
				if (i < isPublics.length) {
					jsonTemp.put("is_public", isPublics[i]);
				} else {
					jsonTemp.put("is_public", false);
				}
				jsonArray.put(jsonTemp);
			}

			json.put("attribute_specs", jsonArray);
			//
			// if (isAdmin == true) {
			// persistent.isAdmin = true;
			// }
			persistent.call(Const.Api.SERVICES_ID_ATTRIBUTESPEC,
					BasicCall.REST.PUT, json.toString(),
					// null,
					serviceId, serviceId);
		}

		/**
		 * Links existing attributespecs specified in the attributeIds array to
		 * the existing service specified by the serviceId. If the service id is
		 * odd the attributespec will be public, if even it will be private.
		 *
		 * @param serviceId
		 *            the id of the service to link to.
		 * @param attributeIds
		 *            the ids of the attributespecs to link.
		 * @param isPublic
		 *            boolean, if true the attributespec will be public, if
		 *            false it will be private.
		 */
		public static void attributespecsToService(int serviceId,
				int[] attributeIds, boolean isPublic) {
			JSONObject json = new JSONObject();

			json.put("is_public", isPublic);

			for (int asid : attributeIds) {
				if (isAdmin == true) {
					persistent.isAdmin = true;
				}
				persistent.call(Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
						BasicCall.REST.PUT, json.toString(), serviceId, asid);
			}
		}

		/**
		 * Alternative for the {@link attributespecsToService(int serviceId,
		 * int[] attributeIds)} for single attribute.
		 *
		 * @param serviceId
		 *            the id of the service to link to.
		 * @param attributeIds
		 *            the ids of the attributespecs to link.
		 * @param isPublic
		 *            boolean, if true the attributespec will be public, if
		 *            false it will be private.
		 */
		public static void attributespecsToService(int serviceId,
				int attributeIds, boolean isPublic) {
			attributespecsToService(serviceId, new int[] { attributeIds },
					isPublic);
		}

		/**
		 * Links existing attributespecs specified in the attributeIds array to
		 * the existing service specified by the serviceId. The attributespecs
		 * will be public.
		 *
		 * @param serviceId
		 *            the id of the service to link to.
		 * @param attributeIds
		 *            the ids of the attributespecs to link.
		 */
		public static void attributespecsPublicToService(int serviceId,
				int[] attributeIds) {
			attributespecsToService(serviceId, attributeIds, true);
		}

		/**
		 * Links existing attributespecs specified in the attributeIds array to
		 * the existing service specified by the serviceId. The attributespecs
		 * will be private.
		 *
		 * @param serviceId
		 *            the id of the service to link to.
		 * @param attributeIds
		 *            the ids of the attributespecs to link.
		 */
		public static void attributespecsPrivateToService(int serviceId,
				int[] attributeIds) {
			attributespecsToService(serviceId, attributeIds, false);
		}

		/**
		 * Links existing principal (as managers) specified in the attributeIds
		 * array to the existing service specified by the serviceId.
		 *
		 * @param serviceId
		 *            the id of the service to link to.
		 * @param principalIds
		 *            the ids of the principal to link.
		 */
		public static void managersToService(int serviceId, int[] principalIds) {
			for (int pid : principalIds) {
				persistent.call(Const.Api.SERVICES_ID_MANAGERS_PID,
						BasicCall.REST.PUT, null, serviceId, pid);
			}
		}

		/**
		 * Alternative call for {@link managersToService(int serviceId, int[]
		 * principalIds)}.
		 *
		 * @param serviceID
		 *            id of the service.
		 * @param principalID
		 *            id of the manager.
		 */
		public static void managersToService(int serviceID, int principalID) {
			managersToService(serviceID, new int[] { principalID });
		}

		public static void managersToServiceByArray(int serviceID,
				int[] principalID) {
			JSONObject json = new JSONObject();
			json.put("managers", principalID);
			persistent.call(Const.Api.SERVICES_ID_MANAGER, BasicCall.REST.PUT,
					json.toString(), serviceID, serviceID);
		}

		/**
		 * Links existing principal specified in the principalIds array to the
		 * existing role specified by the roleId. The principal has to be in the
		 * organization!
		 *
		 * @param roleId
		 *            the id of the role to link.
		 * @param principalIds
		 *            the ids of the principal to link.
		 */
		public static void principalToRole(int roleId, int[] principalIds) {
			for (int pid : principalIds) {
				persistent.call(Const.Api.ROLES_ID_PRINCIPALS_PID,
						BasicCall.REST.PUT, null, roleId, pid);
			}
		}

		/**
		 * Alternative for the {@link principalToRole(int roleId, int[]
		 * principalIds)} for single principal.
		 *
		 * @param roleId
		 *            the id of the role to link.
		 * @param principalId
		 *            the ids of the principal to link.
		 */
		public static void principalToRole(int roleId, int principalId) {
			principalToRole(roleId, new int[] { principalId });
		}

		/**
		 * Links existing principals specified in the principalIds array to the
		 * existing role specified by the roleId, using the array method.
		 *
		 * @param roleId
		 *            the id of the role to link.
		 * @param principalIds
		 *            the ids of the principal to link as an array.
		 */
		public static void principalToRoleSet(int roleId, int[] principalIds) {
			JSONObject json = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (int i : principalIds) {
				jsonArray.put(new JSONObject().put("principal", i));
			}
			json.put("principals", jsonArray);

			persistent.call(Const.Api.ROLES_ID_PRINCIPAL, BasicCall.REST.PUT,
					json.toString(), roleId, roleId);
		}

		/**
		 * Links existing principals specified in the principalIds array to the
		 * existing organization specified by the orgId as a member, using the
		 * array method.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param principalIds
		 *            the ids of the principal to link as an array.
		 */
		public static void memberToOrganizationSet(int orgId, int[] principalIds) {
			JSONObject json = new JSONObject();
			json.put("principals", principalIds);

			if (isAdmin == true) {
				persistent.isAdmin = true;
			}

			persistent.call(Const.Api.ORGANIZATIONS_ID_MEMBER,
					BasicCall.REST.PUT, json.toString(), orgId, orgId);
		}

		/**
		 * Links existing principals specified in the principalIds array to the
		 * existing organization specified by the orgId as a member.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param principalIds
		 *            the ids of the principal to link as an array.
		 */
		public static void memberToOrganization(int orgId, int[] principalIds) {
			for (int pid : principalIds) {
				persistent.call(Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
						BasicCall.REST.PUT, null, orgId, pid);
			}
		}

		/**
		 * Alternative for the {@link memberToOrganization(int orgId, int[]
		 * principalIds)} for single principal.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param principalId
		 *            the id of the principal to link.
		 */
		public static void memberToOrganization(int orgId, int principalId) {
			memberToOrganization(orgId, new int[] { principalId });
		}

		/**
		 * Links existing principals specified in the principalIds array to the
		 * existing organization specified by the orgId as a manager, using the
		 * array method.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param principalIds
		 *            the ids of the principal to link as an array.
		 */
		public static void managerToOrganizationSet(int orgId,
				int[] principalIds) {
			JSONObject json = new JSONObject();
			json.put("managers", principalIds);
			persistent.call(Const.Api.ORGANIZATIONS_ID_MANAGER,
					BasicCall.REST.PUT, json.toString(), orgId, orgId);
		}

		/**
		 * Links existing principal specified in the principalIds array to the
		 * existing organization specified by the orgId as a manager.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param principalIds
		 *            the ids of the principal to link as an array.
		 */
		public static void managerToOrganization(int orgId, int[] principalIds) {
			for (int pid : principalIds) {
				persistent.call(Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
						BasicCall.REST.PUT, null, orgId, pid);
			}
		}

		/**
		 * Alternative for the {@link managerToOrganization(int orgId, int[]
		 * principalIds)} for single principal.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param principalId
		 *            the id of the principal to link.
		 */
		public static void managerToOrganization(int orgId, int principalId) {
			managerToOrganization(orgId, new int[] { principalId });
		}

		/**
		 * Links existing service to attributevalue.
		 *
		 * @param avid
		 *            id of the attributevalue.
		 * @param sid
		 *            id of the service.
		 */
		public static void serviceToAttributevalueorganizations(int avid,
				int sid) {
			persistent.call(
					Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
					BasicCall.REST.PUT, null, avid, sid);
		}

		/**
		 * Links existing service to attributevalue.
		 *
		 * @param avid
		 *            id of the attributevalue.
		 * @param sid
		 *            id of the service.
		 */
		public static void serviceToAttributevalueprincipals(int avid, int sid) {
			persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
					BasicCall.REST.PUT, null, avid, sid);
		}

	}

	/**
	 * Utility class within Utility for removing links and/or objects.
	 */
	public static class Remove {

		/**
		 * Removes attributevalue for organization if exist.
		 *
		 * @param avoIDs
		 *            array of attributevalue ids.
		 */
		public static void attributevalueorganization(int[] avoIDs) {
			for (int avoID : avoIDs) {
				persistent.call(Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID,
						BasicCall.REST.DELETE, null, avoID, 0);
			}
		}

		/**
		 * Alternative for the {@link attributevalueorganization(int[] orgIDs)}
		 * for single attributevalues.
		 *
		 * @param avoID
		 *            attributevalue id.
		 */
		public static void attributevalueorganization(int avoID) {
			attributevalueorganization(new int[] { avoID });
		}

		/**
		 * Removes attributevalue for principal if exist.
		 *
		 * @param avpIDs
		 *            array of attributevalue ids.
		 */
		public static void attributevalueprincipal(int[] avpIDs) {
			for (int avpID : avpIDs) {
				persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID,
						BasicCall.REST.DELETE, null, avpID, 0);
			}
		}

		/**
		 * Alternative for the {@link attributevalueprincipal(int[] avpIDs)} for
		 * single attributevalues.
		 *
		 * @param avpID
		 *            attributevalue id.
		 */
		public static void attributevalueprincipal(int avpID) {
			attributevalueprincipal(new int[] { avpID });
		}

		/**
		 * Removes attributevalue for organization if exist.
		 *
		 * @param avoIDs
		 *            array of attributevalue ids.
		 */
		public static void invitation(int invitation_id) {
			persistent.call(Const.Api.INVITATIONS_ID, BasicCall.REST.DELETE,
					null, invitation_id, 0);
		}

		/**
		 * Removes existing linking of principal specified in the principalIds
		 * array to the existing role specified by the roleId.
		 *
		 * @param roleId
		 *            the id of the role to link.
		 * @param principalIds
		 *            the ids of the principal to link.
		 */
		public static void principalFromRole(int roleId, int[] principalIds) {
			for (int pid : principalIds) {
				persistent.call(Const.Api.ROLES_ID_PRINCIPALS_PID,
						BasicCall.REST.DELETE, null, roleId, pid);
			}
		}

		/**
		 * Alternative for the {@link principalFromRole(int roleId, int[]
		 * principalIds)} for single principal.
		 *
		 * @param roleId
		 *            the id of the role to link.
		 * @param principalId
		 *            the ids of the principal to link.
		 */
		public static void principalFromRole(int roleId, int principalId) {
			principalFromRole(roleId, new int[] { principalId });
		}

		/**
		 * Removes existing links between entitlementpacks specified in the
		 * packIds array to the existing organization specified by the orgId.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param packIds
		 *            the ids of the entitlementpacks to link.
		 */
		public static void entitlementpackFromOrg(int orgId, int[] packIds) {
			for (int eid : packIds) {
				persistent.call(
						Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
						BasicCall.REST.DELETE, null, orgId, eid);
			}
		}

		/**
		 * Alternative for the {@link entitlementpackFromOrg(int orgId, int[]
		 * packIds)} for single principal.
		 *
		 * @param orgId
		 *            the id of the organization to link.
		 * @param packIds
		 *            the ids of the entitlementpacks to link.
		 */
		public static void entitlementpackFromOrg(int orgId, int packIds) {
			entitlementpackFromOrg(orgId, new int[] { packIds });
		}

		/**
		 * Removes attributespec if exist.
		 *
		 * @param asIDs
		 *            array of attributespec ids.
		 */
		public static void attributespec(int[] asIDs) {
			for (int asID : asIDs) {
				if (isAdmin == true) {
					persistent.isAdmin = true;
				}
				persistent.call(Const.Api.ATTRIBUTESPECS_ID,
						BasicCall.REST.DELETE, null, asID, 0);
			}
		}

		/**
		 * Alternative for the {@link attributespec(int[] asIDs)} for single
		 * attributespec.
		 *
		 * @param asID
		 *            attributespec id.
		 */
		public static void attributespec(int asID) {
			attributespec(new int[] { asID });
		}

		/**
		 * Removes the link between attributespec and service.
		 *
		 * @param sID
		 *            id of the service to remove from.
		 * @param asID
		 *            id of the attributespec to remove.
		 */
		public static void attributespecFromService(int sID, int asID) {
			if (isAdmin == true) {
				persistent.isAdmin = true;
			}
			persistent.call(Const.Api.SERVICES_ID_ATTRIBUTESPECS_ASID,
					BasicCall.REST.DELETE, null, sID, asID);
		}

		/**
		 * Removes organization if exist.
		 *
		 * @param orgIDs
		 *            array of organization ids.
		 */
		public static void organization(int[] orgIDs) {
			for (int orgID : orgIDs) {
				persistent.call(Const.Api.ORGANIZATIONS_ID,
						BasicCall.REST.DELETE, null, orgID, 0);
			}
		}

		/**
		 * Alternative for the {@link attributespec(int[] asIDs)} for single
		 * organization.
		 *
		 * @param orgID
		 *            organization id.
		 */
		public static void organization(int orgID) {
			organization(new int[] { orgID });
		}

		/**
		 * Removes services if exist.
		 *
		 * @param serviceIDs
		 *            array of service ids.
		 */
		public static void service(int[] serviceIDs) {
			for (int serviceID : serviceIDs) {
				persistent.call(Const.Api.SERVICES_ID, BasicCall.REST.DELETE,
						null, serviceID, 0);
			}
		}

		/**
		 * Alternative for the {@link attributespec(int[] asIDs)} for single
		 * service.
		 *
		 * @param serviceID
		 *            service id.
		 */
		public static void service(int serviceID) {
			service(new int[] { serviceID });
		}

		/**
		 * Removes the current principal.
		 */
		public static void principalSelf() {
			persistent.call(Const.Api.PRINCIPAL, BasicCall.REST.DELETE);
		}

		/**
		 * Removes the principal with the provided fedid.
		 *
		 * @param fedid
		 *            the fedid of the principal to remove.
		 */
		public static void principal(String fedid) {
			persistent.call(Const.Api.PRINCIPALS_FEDID, BasicCall.REST.DELETE,
					null, 0, 0, fedid);
		}

		/**
		 * Removes the principal with the provided id.
		 *
		 * @param id
		 *            the id of the principal to remove.
		 */
		public static void principal(int id) {
			persistent.call(Const.Api.PRINCIPALS_ID_ID, BasicCall.REST.DELETE,
					null, id, id);
		}

		/**
		 * Alternative call for {@link #entitlement(int[])} for single
		 * entitlement.
		 *
		 * @param id
		 *            id of the entitlement to remove.
		 */
		public static void entitlement(int id) {
			entitlement(new int[] { id });
		}

		/**
		 * Removes the entitlement with the specified ids in the array of ids.
		 *
		 * @param ids
		 *            array of entitlement id's.
		 */
		public static void entitlement(int[] ids) {
			for (int id : ids) {
				persistent.call(Const.Api.ENTITLEMENTS_ID,
						BasicCall.REST.DELETE, null, id, id);
			}
		}

		/**
		 * Alternative call for {@link #entitlementpack(int[])} for single
		 * entitlementpack.
		 *
		 * @param id
		 *            id of the entitlementpack to remove.
		 */
		public static void entitlementpack(int id) {
			entitlementpack(new int[] { id });
		}

		/**
		 * Removes the entitlementpack with the specified ids in the array of
		 * ids.
		 *
		 * @param ids
		 *            array of entitlementpack id's.
		 */
		public static void entitlementpack(int[] ids) {
			for (int id : ids) {
				persistent.call(Const.Api.ENTITLEMENTPACKS_ID,
						BasicCall.REST.DELETE, null, id, id);
			}
		}

		/**
		 * Alternative call for {@link members(int[] orgIDs, int[] pIDs)}.
		 *
		 * @param orgID
		 *            the id of the organization to remove from.
		 * @param pID
		 *            the id of the principal to remove.
		 */
		public static void members(int orgID, int pID) {
			members(new int[] { orgID }, new int[] { pID });
		}

		/**
		 * Alternative call for {@link members(int[] orgIDs, int[] pIDs)}.
		 *
		 * @param orgIDs
		 *            the id of the organization to remove from.
		 * @param pID
		 *            the id of the principal to remove.
		 */
		public static void members(int[] orgIDs, int pID) {
			members(orgIDs, new int[] { pID });
		}

		/**
		 * Alternative call for {@link members(int[] orgIDs, int[] pIDs)}.
		 *
		 * @param orgID
		 *            the id of the organization to remove from.
		 * @param pIDs
		 *            the id of the principal to remove.
		 */
		public static void members(int orgID, int[] pIDs) {
			members(new int[] { orgID }, pIDs);
		}

		/**
		 * Removes the members from the specified organizations.
		 *
		 * @param orgIDs
		 *            the ids of the organization to remove from.
		 * @param pIDs
		 *            the ids of the principal to remove.
		 */
		public static void members(int[] orgIDs, int[] pIDs) {
			for (int orgID : orgIDs) {
				for (int pID : pIDs) {
					persistent.call(Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
							BasicCall.REST.DELETE, null, orgID, pID);
				}
			}
		}

		/**
		 * Alternative call for {@link managers(int[] orgIDs, int[] pIDs)}.
		 *
		 * @param orgID
		 *            the id of the organization to remove from.
		 * @param pID
		 *            the id of the principal to remove.
		 */
		public static void managers(int orgID, int pID) {
			managers(new int[] { orgID }, new int[] { pID });
		}

		/**
		 * Alternative call for {@link managers(int[] orgIDs, int[] pIDs)}.
		 *
		 * @param orgIDs
		 *            the id of the organization to remove from.
		 * @param pID
		 *            the id of the principal to remove.
		 */
		public static void managers(int[] orgIDs, int pID) {
			managers(orgIDs, new int[] { pID });
		}

		/**
		 * Alternative call for {@link managers(int[] orgIDs, int[] pIDs)}.
		 *
		 * @param orgID
		 *            the id of the organization to remove from.
		 * @param pIDs
		 *            the id of the principal to remove.
		 */
		public static void managers(int orgID, int[] pIDs) {
			managers(new int[] { orgID }, pIDs);
		}

		/**
		 * Removes the managers from the specified organizations.
		 *
		 * @param orgIDs
		 *            the ids of the organization to remove from.
		 * @param pIDs
		 *            the ids of the principal to remove.
		 */
		public static void managers(int[] orgIDs, int[] pIDs) {
			for (int orgID : orgIDs) {
				for (int pID : pIDs) {
					persistent.call(Const.Api.ORGANIZATIONS_ID_MANAGERS_PID,
							BasicCall.REST.DELETE, null, orgID, pID);
				}
			}
		}

		/**
		 * Alternative call for {@link roles(int[] roleIDs)}.
		 *
		 * @param roleID
		 *            the id of the role to delete.
		 */
		public static void roles(int roleID) {
			roles(new int[] { roleID });
		}

		/**
		 * Removes the role with the specified id.
		 *
		 * @param roleIDs
		 *            ids of the roles to remove.
		 */
		public static void roles(int[] roleIDs) {
			for (int roleID : roleIDs) {
				persistent.call(Const.Api.ROLES_ID, BasicCall.REST.DELETE,
						null, roleID, roleID);
			}
		}

		/**
		 * Removes the link between entitlement and entitlementpack.
		 *
		 * @param packIDs
		 *            ids of the entitlementpacks.
		 * @param eIDs
		 *            ids of the entitlements.
		 */
		public static void entitlementFromPack(int[] packIDs, int[] eIDs) {
			for (int packID : packIDs) {
				for (int eID : eIDs) {
					persistent.call(
							Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
							BasicCall.REST.DELETE, null, packID, eID);
				}
			}
		}

		/**
		 * Alternative call for {@link entitlementFromPack(int[] packIDs, int[]
		 * eIDs)}.
		 *
		 * @param packID
		 *            id of the entitlementpack.
		 * @param eID
		 *            id of the entitlement.
		 */
		public static void entitlementFromPack(int packID, int eID) {
			entitlementFromPack(new int[] { packID }, new int[] { eID });
		}

		/**
		 * Removes the managers specified by the pIDs from the service specified
		 * by the serviceID.
		 *
		 * @param serviceID
		 *            id of the service.
		 * @param pIDs
		 *            array of ids of managers.
		 */
		public static void managerFromService(int serviceID, int[] pIDs) {
			for (int pID : pIDs) {
				persistent.call(Const.Api.SERVICES_ID_MANAGERS_PID,
						BasicCall.REST.DELETE, null, serviceID, pID);
			}
		}

		/**
		 * Alternative call for {@link managerFromService(int serviceID, int[]
		 * pIDs)}
		 *
		 * @param serviceID
		 *            id of the service.
		 * @param pID
		 *            id of managers.
		 */
		public static void managerFromService(int serviceID, int pID) {
			managerFromService(serviceID, new int[] { pID });
		}

		/**
		 * Removes entitlements from role.
		 *
		 * @param roleID
		 *            the id of the role.
		 * @param entitlementIDs
		 *            array of ids of entitlements.
		 */
		public static void entitlementFromRole(int roleID, int[] entitlementIDs) {
			for (int entitlementID : entitlementIDs) {
				persistent.call(Const.Api.ROLES_ID_ENTITLEMENTS_EID,
						BasicCall.REST.DELETE, null, roleID, entitlementID);
			}
		}

		/**
		 * Alternative call for {@link entitlementFromRole(int roleID, int[]
		 * entitlementIDs)}.
		 *
		 * @param roleID
		 *            the id of the role.
		 * @param entitlementID
		 *            id of entitlements.
		 */
		public static void entitlementFromRole(int roleID, int entitlementID) {
			entitlementFromRole(roleID, new int[] { entitlementID });
		}

		/**
		 * Removes existing service from attributevalue.
		 *
		 * @param avid
		 *            id of the attributevalue.
		 * @param sid
		 *            id of the service.
		 */
		public static void serviceFromAttributevalueorganizations(int avid,
				int sid) {
			persistent.call(
					Const.Api.ATTRIBUTEVALUEORGANIZATIONS_ID_SERVICES_SID,
					BasicCall.REST.DELETE, null, avid, sid);
		}

		/**
		 * Removes existing service from attributevalue.
		 *
		 * @param avid
		 *            id of the attributevalue.
		 * @param sid
		 *            id of the service.
		 */
		public static void serviceFromAttributevalueprincipals(int avid, int sid) {
			persistent.call(Const.Api.ATTRIBUTEVALUEPRINCIPALS_ID_SERVICES_SID,
					BasicCall.REST.DELETE, null, avid, sid);
		}
	}

	/**
	 * Finds the numbers in the given string that match the /numbers{/.}
	 * pattern. Always returns a List, can't be null, but may be empty.
	 *
	 * @param string
	 *            string containing numbers.
	 * @return list containing the numbers in the given string.
	 */
	public static List<Integer> getNumber(String string) {
		string = string.replace(Const.HEXAA_HOST, "");

		List<Integer> answer = new ArrayList<>();

		Pattern number = Pattern.compile("/\\d+[^\\./]*");
		Matcher match = number.matcher(string);

		while (match.find()) {
			String temp = match.group();
			answer.add(Integer.parseInt(temp.substring(1)));
		}

		return answer;
	}
}
