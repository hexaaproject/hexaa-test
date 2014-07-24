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
     * Drops the current database, recreates and reinitializes the tables.
     */
    public void dropDatabase() {
        try {

            Runtime rt = Runtime.getRuntime();

            // Call for server side script
            Process proc = rt.exec(new String[]{"ssh", "user@"+Const.HEXAA_HOST,
                "~/databasedrop.sh", "exit"});

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
