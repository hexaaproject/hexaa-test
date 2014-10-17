package sztaki.hexaa.httputility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;
import sztaki.hexaa.httputility.BasicCall.REST;

/**
 * Provides authentication via Master Secret Authentication. Use the public
 * authenticate() method, as it's makes sure that there is no unnecessary API
 * key requests.
 */
public class Authenticator {

    /**
     * Checks if the session is authenticated or not, and authenticates if
     * necessary. Gets a short time limited API key and uses the /api/token GET
     * method to get the usual 1 hour limited API key. The given fedid has to be
     * in a valid e-mail format (some@thing.example).
     *
     * @param fedid the fedid we want to authenticate with, normally the fedid
     * is the {@link Const.HEXAA_FEDID}, if not use a valid e-mail format.
     */
    public void authenticate(String fedid) {
        // Load the properties file if it has not been yet loaded and it exist.
        if (!Const.PROPERTIES_LOADED) {
            this.loadProperties();
        }

        // We check the current connection, if we don't get 
        System.out.print("** AUTHENTICATE **\t");
        String response = new BasicCall().call(Const.Api.PRINCIPAL_SELF, REST.GET);
        System.out.print("** AUTHENTICATE **\t");
        System.out.println(response);

        // If the response is Forbidden, we start the authentication
        if (!response.contains(fedid)) {
            System.out.print("** AUTHENTICATE **\t");
            System.out.println("Getting temporary API key.");

            BasicCall postToken = new BasicCall();

            JSONObject json = new JSONObject();
            json.put("fedid", fedid);
            json.put("apikey", this.getAPIKey());
            json.put("email", fedid);
            json.put("display_name", fedid + "_name");

            System.out.print("** AUTHENTICATE **\t");
            System.out.println("Temporary API key acquired.");

            System.out.print("** AUTHENTICATE **\t");
            response = postToken.call(
                    Const.Api.TOKENS,
                    BasicCall.REST.POST,
                    json.toString(),
                    0, 0);

            JSONObject jsonResponse;
            System.out.println(response);
            if (response == null || response.isEmpty() || response.contains("503 Service Unavailable")) {
                System.out.println(response);
                return;
            }
            jsonResponse = (JSONObject) JSONParser.parseJSON(response);

            System.out.print("** AUTHENTICATE **\t");
            System.out.println(jsonResponse.toString());

            Const.HEXAA_AUTH = jsonResponse.get("token").toString();

            System.out.print("** AUTHENTICATE **\t");
            System.out.println("Normal API key acquired.");

            /*End of Forbidden*/
        }

    }

    /**
     * Provides the necessary data and hashing for the temporal API key.
     *
     * @return String temporal API key for limited time of authentication.
     */
    public String getAPIKey() {
        String out = null;
        String sha256hex;

        // Set a ZoneId so we can get zone specific time, in this case "UTC"
        ZoneId id = ZoneId.of("UTC");
        LocalDateTime date = LocalDateTime.now(id);
        try {
            // Format the date to the required pattern
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            out = date.format(format);
        } catch (DateTimeException exc) {
            throw exc;
        }
        String temp = Const.MASTER_SECRET;

        temp = temp.concat(out);

        sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(temp);
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
        System.out.println(System.getProperty("user.dir"));
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = Const.PROPERTIES;
//            input = Authenticator.class.getClassLoader().getResourceAsStream(filename);
            input = new FileInputStream(filename);

            //load a properties file from class path, inside static method
            prop.load(input);

            System.out.println("*** Loading Properties ***");
            //get the property value and print it out
            Const.HEXAA_PORT = Integer.parseInt(prop.getProperty("port"));
            System.out.println("*** " + Const.HEXAA_PORT);
            
            Const.SSH_PORT = Integer.parseInt(prop.getProperty("ssh"));
            System.out.println("*** " + Const.SSH_PORT);

            Const.HEXAA_HOST = prop.getProperty("host");
            System.out.println("*** " + Const.HEXAA_HOST);

            Const.HEXAA_FEDID = prop.getProperty("fedid");
            System.out.println("*** " + Const.HEXAA_FEDID);

            Const.MASTER_SECRET = prop.getProperty("master_secret");
            System.out.println("*** " + Const.MASTER_SECRET);

            Const.PROPERTIES_LOADED = true;

        } catch (IOException ex) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

    }
}
