package sztaki.hexaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		DataLoader data = new DataProp();

		Runtime rt = Runtime.getRuntime();

		Process proc;

		if (new DataProp().getString("ssh_host").equals("localhost")) {
			try {
				// Call for server side script if it runs on the server
				proc = rt.exec(new String[] { data.getString("sh_dir")
						+ "databasedrop.sh" });
			} catch (IOException ex) {
				System.err
						.println("The local execution failed before or during removing the mysql database. The database integrity my be damaged.");
				return;
			}
		} else {
			try {
				// Call for server side script remotely
				proc = rt.exec(new String[] {
						"ssh",
						data.getString("ssh_user") + "@"
								+ data.getString("ssh_host"), "-p",
						data.getString("ssh_port"), "~/databasedrop.sh" });
			} catch (IOException ex) {
				System.err
						.println("The ssh connection failed before or during removing the mysql database. The database integrity my be damaged.");
				return;
			}
		}

		// any error message?
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(),
				"ERROR");

		// any output?
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(),
				"OUTPUT");

		// kick them off
		errorGobbler.start();
		outputGobbler.start();

		// any error???
		int exitVal = 0;
		try {
			exitVal = proc.waitFor();
		} catch (InterruptedException e) {
			System.err
					.println("The process was interrupted before or during removing the mysql database. The database integrity my be damaged.");
		}
		System.out.println("ExitValue: " + exitVal);

	}

	/**
	 * Clears the cache and log directories on the server via a server side
	 * script.
	 */
	public void dropCache() {
		DataLoader data = new DataProp();

		Runtime rt = Runtime.getRuntime();

		Process proc;

		if (new DataProp().getString("ssh_host").equals("localhost")) {
			try {
				// Call for server side script if it runs on the server
				proc = rt.exec(new String[] { data.getString("sh_dir")
						+ "cachedrop.sh" });
			} catch (IOException ex) {
				System.err
						.println("The local execution failed before or during removing the cache.");
				return;
			}
		} else {
			try {
				// Call for server side script remotely
				proc = rt.exec(new String[] {
						"ssh",
						data.getString("ssh_user") + "@"
								+ data.getString("ssh_host"), "-p",
						data.getString("ssh_port"), "~/cachedrop.sh" });
			} catch (IOException ex) {
				System.err
						.println("The ssh connection failed before or during removing the cache.");
				return;
			}
		}

		// any error message?
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(),
				"ERROR");

		// any output?
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(),
				"OUTPUT");

		// kick them off
		errorGobbler.start();
		outputGobbler.start();

		// any error???
		int exitVal = 0;
		try {
			exitVal = proc.waitFor();
		} catch (InterruptedException e) {
			System.err
					.println("The proccess was interrupted before or during removing the cache.");
		}
		System.out.println("ExitValue: " + exitVal);

	}

	// mysql --user=root --password=pass hexaa -e
	// "SELECT enable_token FROM service;"
	/**
	 * Gets the service enable token directly from the mysql database.
	 * 
	 * @return String, the one time use token to enable the service.
	 */
	public String getServiceEnableToken() {
		DataLoader data = new DataProp();
		String output = new String();

		Runtime rt = Runtime.getRuntime();

		Process proc;

		if (new DataProp().getString("ssh_host").equals("localhost")) {
			// Call for server side script if it runs on the server
			try {
				proc = rt
						.exec(new String[] { "mysql --user=root --password=pass hexaa -e \"SELECT enable_token FROM service;\"" });
			} catch (IOException ex) {
				System.err
						.println("The local execution failed before or during getting the service enable token.");
				return "";
			}
		} else {
			// Call for server side script remotely
			try {
				proc = rt
						.exec(new String[] {
								"ssh",
								data.getString("ssh_user") + "@"
										+ data.getString("ssh_host"),
								"-p",
								data.getString("ssh_port"),
								"mysql --user=root --password=pass hexaa -e \"SELECT enable_token FROM service;\"" });
			} catch (IOException ex) {
				System.err
						.println("The ssh connection failed before or during getting the service enable token.");
				return "";
			}
		}

		// any error message?
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(),
				"ERROR");

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
				// System.out.println("SQL " + ">" + line);
			}
		} catch (IOException ioe) {
			System.err
					.println("The inputstream from the mysql database was unreachable.");
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
				System.err.println("The inputstream from the " + this.type
						+ " could not be reached.");
			}
		}
	}
}
