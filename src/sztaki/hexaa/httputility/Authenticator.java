/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        if (response.contains("401") || response.contains("403")) {
            System.out.println("Forbidden");

            byte[] digest = null;
            String out = null;

            try {
                ZoneId id = ZoneId.of("UTC");
                LocalDateTime date = LocalDateTime.now(id);
                System.out.println(date.toString());
                try {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("y-M-d  h:m");
                    out = date.format(format);
                    System.out.println(out);
                } catch (DateTimeException exc) {
                    throw exc;
                }
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String text = "7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv";
                text = text.concat(out);

                //md.update(text.getBytes("UTF-8"));
                md.update(text.getBytes("UTF-8"));
                digest = md.digest();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (digest == null) {
                System.exit(0);
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println(sb.toString());

            HttpUtilityBasicCall postToken = new Token();

            response = postToken.call(HttpUtilityBasicCall.REST.POST, sb.toString(), 0, 2);

            System.out.println(response);

            if (response != null) {
                ServerConstants.HEXAA_AUTH = response;
            }

            /*End of Forbidden*/
        }

    }
}
