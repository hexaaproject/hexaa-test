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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sztaki.hexaa.httputility.apicalls.Token;
import sztaki.hexaa.httputility.apicalls.principals.Principal;

/**
 *
 * @author Bana Tibor
 */
public class Authenticator {

    public Authenticator() {
        // We check the current connection, if we don't get 
        HttpUtilityBasicCall getPrincipal = new Principal();
        String response = getPrincipal.call(HttpUtilityBasicCall.REST.GET);

        System.out.println(response);
        // If the response is Forbidden, we start the authentication
        if (response.contains("401") || response.contains("403")) {
            System.out.println("Forbidden");

            String out = null;
            String sha256hex;

            // Set a ZoneId so we can get zone specific time, in this case "UTC"
            ZoneId id = ZoneId.of("UTC");
            LocalDateTime date = LocalDateTime.now(id);
            System.out.println(date.toString());
            try {
                // Format the date to the required pattern
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                out = date.format(format);
                System.out.println(out);
            } catch (DateTimeException exc) {
                throw exc;
            }
            String text = "7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv";

            text = text.concat(out);
            System.out.println(text);

            sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
            if (sha256hex == null) {
                System.exit(0);
            }
            System.out.println(sha256hex);

            HttpUtilityBasicCall postToken = new Token();

            JSONObject json = new JSONObject();
            json.put("fedid", "ede91bt@gmail.com@partners.sztaki.hu");
            json.put("apikey", sha256hex);

            response = postToken.call(HttpUtilityBasicCall.REST.POST, json.toString());

            System.out.println(response);

            if (response != null && !response.contains("Bad Request") && !response.contains("Method Not Found")) {
                ServerConstants.HEXAA_AUTH = response;
            }

            /*End of Forbidden*/
        }

    }
}
