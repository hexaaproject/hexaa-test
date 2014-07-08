/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sztaki.hexaa.httputility.apicalls.Token;
import sztaki.hexaa.httputility.apicalls.principals.Principal;

/**
 *
 * @author Bana Tibor
 */
public class Authenticator {

    public void authenticate() {
        // We check the current connection, if we don't get 
        BasicCall getPrincipal = new Principal();
        String response = getPrincipal.call(BasicCall.REST.GET);

        // If the response is Forbidden, we start the authentication
        if (response.contains("401") || response.contains("403")) {
            try {
                System.out.println("Forbidden");

                BasicCall postToken = new Token();

                JSONObject json = new JSONObject();
                json.put("fedid", "ede91bt@gmail.com@partners.sztaki.hu");
                json.put("apikey", this.getAPIKey());

                response = postToken.call(BasicCall.REST.POST, json.toString());

                JSONObject jsonResponse;
                jsonResponse = (JSONObject) new JSONParser().parse(response);

                ServerConstants.HEXAA_AUTH = jsonResponse.get("token").toString();

                /*End of Forbidden*/
            } catch (ParseException ex) {
                Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

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
        String text = "7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv";

        text = text.concat(out);

        sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
        if (sha256hex == null) {
            System.exit(0);
        }

        return sha256hex;

    }
}
