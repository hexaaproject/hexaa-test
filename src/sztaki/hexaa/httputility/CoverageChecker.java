package sztaki.hexaa.httputility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
 * called apicall should stay in it. Used only in BasicTestSuite to make sure
 * it's started and finished correctly.
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
    
    public static void Init() {
        getCoverageChecker();
    }
    
    public static CoverageChecker getCoverageChecker() {
        if (coverageChecker == null) {
            coverageChecker = new CoverageChecker();
        }
        return coverageChecker;
    }
    
    public static void checkout(String s) {
        if (coverageChecker == null) {
            Init();
        }
        remainingCalls.remove(s);
    }
    
    public static void printout() {
        if (coverageChecker == null) {
            Init();
        }
        System.out.println(remainingCalls);
    }
}
