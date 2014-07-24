package sztaki.hexaa.httputility;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
     * method to get the usual 1 hour limited API key.
     */
    public void authenticate() {
        // We check the current connection, if we don't get 
        String response = new BasicCall().call(Const.Api.PRINCIPAL_SELF, REST.GET);
        System.out.println(response);

        // If the response is Forbidden, we start the authentication
        if (response.contains("401") || response.contains("403") || response.contains("404")) {
            System.out.println("Getting temporary API key.");

            BasicCall postToken = new BasicCall();

            JSONObject json = new JSONObject();
            json.put("fedid", "ede91bt@gmail.com@partners.sztaki.hu");
            json.put("apikey", this.getAPIKey());
            json.put("email", "ede91bt@gmail.com@partners.sztaki.hu");
            json.put("display_name", "ede91bt");

            System.out.println("Temporary API key acquired.");

            response = postToken.call(
                    Const.Api.TOKENS,
                    BasicCall.REST.POST,
                    json.toString(),
                    0, 0);

            JSONObject jsonResponse;
            jsonResponse = (JSONObject) JSONParser.parseJSON(response);

            System.out.println(jsonResponse.toString());

            Const.HEXAA_AUTH = jsonResponse.get("token").toString();

            System.out.println("Normal API key acquired.");

            /*End of Forbidden*/
        }

    }

    /**
     * Provides the necessary data and hashing for the temporal API key.
     *
     * @return String temporal API key for limited time of authentication.
     */
    private String getAPIKey() {
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
        String text = "7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv";

        text = text.concat(out);

        sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
        if (sha256hex == null) {
            System.exit(0);
        }

        return sha256hex;

    }
}
