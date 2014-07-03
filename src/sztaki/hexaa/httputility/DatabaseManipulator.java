/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bana Tibor
 */
public final class DatabaseManipulator {
    
    private ArrayList<String> commands;
    
    private static final String serverSSH = new String(" ssh user@192.168.203.183");
    
    public void dropDatabase() {
        try {
            this.flushCommands();
            //this.addCommand("~/databasedrop.sh");
            this.addCommand("exit");
            
            Runtime rt = Runtime.getRuntime();
            
            String[] cmdArray = new String[commands.size()];
            cmdArray = commands.toArray(cmdArray);
            
            Process proc = rt.exec(new String [] {"ssh", "user@192.168.203.183","~/databasedrop.sh","exit"} );

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
    
    public DatabaseManipulator() {
        commands = new ArrayList<>();
        
        this.flushCommands();
    }
    
    public void addCommand(String cmd) {
        if (cmd != null) {
            commands.add(cmd);
        }
    }
    
    public void rmvCommand(String cmd) {
        if (cmd != null) {
            commands.remove(cmd);
        }
    }
    
    public void flushCommands() {
        commands.clear();
        
        commands.add("bash");
        //commands.add("-c");
        commands.add(serverSSH);
    }
    
    class StreamGobbler extends Thread {
        
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
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + ">" + line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
