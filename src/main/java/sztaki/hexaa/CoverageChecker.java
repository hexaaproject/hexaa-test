package sztaki.hexaa;

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
	 * Storing the strings for faster processing.
	 */
	private static ArrayList<String> alreadyCalled = null;

	/**
	 * Default constructor, can not be called from the outside.
	 */
	protected CoverageChecker() {
		remainingCalls = new ArrayList<>();
		alreadyCalled = new ArrayList<String>();

		File target = new File("target");
		target.mkdir();
		
		File notcalled = new File("target/not_called_list.txt");

		if (!notcalled.exists()) {
			try {
				notcalled.createNewFile();
				OutputStream out = new FileOutputStream(notcalled);
				Files.copy(
						FileSystems.getDefault()
								.getPath("config/call_list_dist.txt"), out);
			} catch (IOException ex) {
				// TODO IOException
				return;
			}
		}

		File called = new File("target/called_list.txt");
		if (!called.exists()) {
			try {
				notcalled.createNewFile();
			} catch (IOException ex) {
				// TODO IOException
				return;
			}
		}

		Scanner s;
		try {
			s = new Scanner(notcalled);
			while (s.hasNextLine()) {
				remainingCalls.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException ex) {
			// TODO FileNotFoundException
		}
	}

	/**
	 * Initialize the class if needed, does nothing if it is already
	 * initialized. Calls the getCoverageChecker() method, this is here just to
	 * avoid the non-used return value warnings.
	 */
	public static void Init() {
		if (coverageChecker == null || remainingCalls == null) {
			getCoverageChecker();
		}
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
	 * @param s
	 *            String expected in the format of
	 *            "RESTCALL(GET/POST/PUT/PATCH/DELETE) /api/..."
	 */
	public static void checkout(String s) {
		Init();

		boolean rewriteNeeded = remainingCalls.remove(s);

		if (rewriteNeeded == true) {
			File f = new File("target/not_called_list.txt");
			FileWriter fw = null;
			try {
				fw = new FileWriter(f, false);
				for (String line : remainingCalls) {
					fw.write(line + "\n");
				}
				fw.close();
			} catch (IOException ex) {
				// TODO IOException
			}
		}
		
		if (!alreadyCalled.contains(s)) {
			alreadyCalled.add(s);

			File f = new File("target/called_list.txt");
			FileWriter fw = null;
			try {
				fw = new FileWriter(f, true);
				fw.write(s + "\n");
				fw.close();
			} catch (IOException ex) {
				// TODO IOException
			}
		}
		
	}

	/**
	 * Prints the current not yet used calls to the screen. IMPORTANT this does
	 * not close the class, the list will not be written to file by this.
	 */
	public static void printout() {
		Init();

		for (String s : remainingCalls) {
			System.out.println(s);
		}
	}
}
