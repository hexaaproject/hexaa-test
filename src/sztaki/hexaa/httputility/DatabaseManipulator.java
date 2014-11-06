package sztaki.hexaa.httputility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to change the database through server side scripts, uses ssh connection
 * and runs the server side script via Runtime.exec.
 */
public final class DatabaseManipulator {

    /**
     * Drops the current database, recreates and reinitializes the tables via a
     * server side script.
     */
    public void dropDatabase() {
        try {

            Runtime rt = Runtime.getRuntime();

            Process proc;

            if (Const.HEXAA_HOST.equals("localhost")) {
                // Call for server side script if it runs on the server
                proc = rt.exec(new String[]{
                    "/var/lib/jenkins/databasedrop.sh"
                });
            } else {
                // Call for server side script remotely
                proc = rt.exec(new String[]{
                    "ssh", "root@" + Const.HEXAA_HOST,
                    "-p", Integer.toString(Const.SSH_PORT),
                    "~/databasedrop.sh"
                });
            }

            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DatabaseManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Clears the cache and log directories on the server via a server side
     * script.
     */
    public void dropCache() {
        try {

            Runtime rt = Runtime.getRuntime();

            Process proc;

            if (Const.HEXAA_HOST.equals("localhost")) {
                // Call for server side script if it runs on the server
                proc = rt.exec(new String[]{
                    "/var/lib/jenkins/cachedrop.sh"
                });
            } else {
                // Call for server side script remotely
                proc = rt.exec(new String[]{
                    "ssh", "root@" + Const.HEXAA_HOST,
                    "-p", Integer.toString(Const.SSH_PORT),
                    "~/cachedrop.sh"
                });
            }

            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DatabaseManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // mysql --user=root --password=pass hexaa -e "SELECT enable_token FROM service;"
    /**
     * Clears the cache and log directories on the server via a server side
     * script.
     */
    public String getServiceEnableToken() {
        String output = new String();
        try {

            Runtime rt = Runtime.getRuntime();

            Process proc;

            if (Const.HEXAA_HOST.equals("localhost")) {
                // Call for server side script if it runs on the server
                proc = rt.exec(new String[]{
                    "mysql --user=root --password=pass hexaa -e \"SELECT enable_token FROM service;\""
                });
            } else {
                // Call for server side script remotely
                proc = rt.exec(new String[]{
                    "ssh", "root@" + Const.HEXAA_HOST,
                    "-p", Integer.toString(Const.SSH_PORT),
                    "mysql --user=root --password=pass hexaa -e \"SELECT enable_token FROM service;\""
                });
            }

            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // kick them off
            errorGobbler.start();

            try {
                InputStreamReader isr = new InputStreamReader(proc.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("-")) {
                        output = line;
                    }
                    System.out.println("SQL " + ">" + line);
                }
            } catch (IOException ioe) {
            }

            // any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DatabaseManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    /**
     * Uses threads to read error and input stream messages from the input to
     * avoid puffer problems.
     */
    private class StreamGobbler extends Thread {

        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + ">" + line);
                }
            } catch (IOException ioe) {
            }
        }
    }
}
