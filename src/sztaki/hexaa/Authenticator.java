package sztaki.hexaa;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import sztaki.hexaa.BasicCall.REST;

/**
 * Provides authentication via Master Secret Authentication. Use the public
 * authenticate() method, as it's makes sure that there is no unnecessary API
 * key requests.
 */
public class Authenticator {

	/**
	 * Alternative call for {@link authenticate(String fedid, String secret)}
	 * for non differentiated master_secret authentications and legacy purposes.
	 * Always uses the {@link Const.MASTER_SECRET}.
	 * 
	 * @param fedid
	 *            the fedid to authenticate with, normally the fedid is the
	 *            {@link Const.HEXAA_FEDID}, if not use a valid e-mail format.
	 * @return
	 */
	public int authenticate(String fedid) {
		return this.authenticate(fedid, Const.MASTER_SECRET);
	}

	/**
	 * Checks if the session is authenticated or not, and authenticates if
	 * necessary. Gets a short time limited API key and uses the /api/token GET
	 * method to get the usual 1 hour limited API key. The given fedid has to be
	 * in a valid e-mail format (some@thing.example).
	 *
	 * @param fedid
	 *            the fedid to authenticate with, normally the fedid is the
	 *            {@link Const.HEXAA_FEDID}, if not use a valid e-mail format.
	 */
	public int authenticate(String fedid, String secret) {
		if (!Const.PROPERTIES_LOADED) {
			this.loadProperties();
		}

		System.out.print("** AUTHENTICATE **\t");
		String response = new BasicCall().call(Const.Api.PRINCIPAL_SELF,
				REST.GET);

		if (!response.contains(fedid)) {

			JSONCall postToken = new JSONCall();

			JSONObject json = new JSONObject();
			json.put("fedid", fedid);
			json.put("apikey", this.getAPIKey(secret));
			json.put("email", fedid);
			json.put("display_name", fedid + "_name");

			JSONObject jsonResponse;
			try {
				System.out.print("** AUTHENTICATE **\t");
				jsonResponse = postToken.getResponseJSONObject(
						Const.Api.TOKENS, BasicCall.REST.POST, json.toString());
			} catch (ResponseTypeMismatchException ex) {
				System.out
						.println("Unable to authenticate, please make sure that the server is reachable, and config.properties is correct.");
				return 1;
			}

			System.out.print("** AUTHENTICATE **\t");
			System.out.println(jsonResponse.toString());

			if (jsonResponse.has("token")) {
				Const.HEXAA_AUTH = jsonResponse.get("token").toString();
			} else {
				System.out.println("Unable to authenticate. TempToken: "
						+ json.get("apikey"));
				return 1;
			}

			JSONObject principalSelf;
			try {
				System.out.print("** AUTHENTICATE **\t");
				principalSelf = postToken.getResponseJSONObject(
						Const.Api.PRINCIPAL_SELF, REST.GET);
				BasicCall.HEXAA_ID = principalSelf.getInt("id");
			} catch (ResponseTypeMismatchException | JSONException ex) {
				System.out.println("Unable to find principal: "
						+ ex.getMessage());
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Provides the necessary data and hashing for the temporal API key.
	 *
	 * @return String temporal API key for limited time of authentication.
	 */
	public String getAPIKey(String secret) {
		String timestamp = null;
		String sha256hex;

		// Set a ZoneId so we can get zone specific time, in this case "UTC"
		ZoneId id = ZoneId.of("UTC");
		LocalDateTime date = LocalDateTime.now(id);
		try {
			// Format the date to the required pattern
			DateTimeFormatter format = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm");
			timestamp = date.format(format);
		} catch (DateTimeException exc) {
			throw exc;
		}

		secret = secret.concat(timestamp);

		sha256hex = org.apache.commons.codec.digest.DigestUtils
				.sha256Hex(secret);
		if (sha256hex == null) {
			System.exit(0);
		}

		return sha256hex;

	}

	/**
	 * Load the .properties file specified in the Const, config.properties is
	 * the default.
	 */
	public void loadProperties() {
		System.out.println("*** Custom config.properties file : "
				+ System.getProperty("user.dir"));
		Properties prop = new Properties();
		InputStream input = null;

		try {

			String filename = Const.PROPERTIES;
			// input =
			// Authenticator.class.getClassLoader().getResourceAsStream(filename);
			input = new FileInputStream(filename);

			// load a properties file from class path, inside static method
			prop.load(input);

			System.out.print("*** Loading Properties ***");
			// get the property value and print it out
			Const.HEXAA_PORT = Integer.parseInt(prop.getProperty("port"));
			System.out.print("\t* port : " + Const.HEXAA_PORT);

			Const.SSH_PORT = Integer.parseInt(prop.getProperty("ssh"));
			System.out.print("\t* ssh : " + Const.SSH_PORT);

			Const.HEXAA_HOST = prop.getProperty("host");
			System.out.print("\t* host : " + Const.HEXAA_HOST);

			Const.HEXAA_FEDID = prop.getProperty("fedid");
			System.out.print("\t* fedid : " + Const.HEXAA_FEDID);

			Const.MASTER_SECRET = prop.getProperty("master_secret");
			System.out.print("\t* master_secret : " + Const.MASTER_SECRET);

			System.out.println("\t*** Properties are loaded ***");
			Const.PROPERTIES_LOADED = true;

		} catch (IOException ex) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out
							.println("Problems occured with the loading of the config.properties file");
				}
			}
		}

	}
}
