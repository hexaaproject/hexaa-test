package sztaki.hexaa.httputility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the apicalllist.txt file, so after test suites run only the not yet
 * called apicall should stay in it.
 */
public class CoverageChecker {

    /**
     * Self reference for singleton usage.
     */
    private static CoverageChecker coverageChecker = null;

    /**
     * Storing the strings for faster processing.
     */
    private static ArrayList<String> remainingCalls = null;

    /**
     * Default constructor, can not be called from the outside.
     */
    protected CoverageChecker() {
        remainingCalls = new ArrayList<>();

        File f = new File("apicalllist.txt");
        OutputStream out = null;

        if (!f.exists()) {
            try {
                f.createNewFile();
                out = new FileOutputStream(f);
                Files.copy(FileSystems.getDefault().getPath("apicalllist_dist.txt"), out);
            } catch (IOException ex) {
                Logger.getLogger(CoverageChecker.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }

        Scanner s;
        try {
            s = new Scanner(f);
            while (s.hasNextLine()) {
                remainingCalls.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CoverageChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initialize the class if needed, does nothing if it is already
     * initialized. Calls the getCoverageChecker() method, this is here just to
     * avoid the non-used return value warnings.
     */
    public static void Init() {
        getCoverageChecker();
    }

    /**
     * Returns the object of the singleton class, if needed it initialize the
     * class.
     *
     * @return the object of the CoverageChecker class.
     */
    public static CoverageChecker getCoverageChecker() {
        if (coverageChecker == null) {
            coverageChecker = new CoverageChecker();
        }
        return coverageChecker;
    }

    /**
     * Checks for the given string in the remainingCalls list, if it is there it
     * removes it and rewrites the apicalllist.txt file as well.
     *
     * @param s String expected in the format of
     * "RESTCALL(GET/POST/PUT/PATCH/DELETE) /api/..."
     */
    public static void checkout(String s) {
        if (coverageChecker == null) {
            Init();
        }
        boolean rewriteNeeded = remainingCalls.remove(s);

        if (rewriteNeeded == true) {
            File f = new File("apicalllist.txt");
            FileWriter fw = null;
            try {
                fw = new FileWriter(f, false);
                for (String line : remainingCalls) {
                    fw.write(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(CoverageChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Prints the current not yet used calls to the screen. IMPORTANT this does
     * not close the class, the list will not be written to file by this.
     */
    public static void printout() {
        if (coverageChecker == null) {
            Init();
        }
        System.out.println(remainingCalls);
    }
}
