package sztaki.hexaa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides data for the tests and utility classes with the ability to change
 * the underlying data-structure with other types of storage without any kind of
 * change in the program.
 */
public class MyProperties extends Properties {

	/**
	 * UID for serialization purposes.
	 */
	private static final long serialVersionUID = 373432405421744070L;

	/**
	 * Returns a string value for the given key.
	 * 
	 * @param key
	 *            String, key for the property.
	 * @return String, can be null if the key is not found.
	 */
	public String getString(String key) {
		return this.getProperty(key);
	}

	/**
	 * Returns an int value for the given key.
	 * 
	 * @param key
	 *            String, key for the property.
	 * @return int, can be 0 if the key is not found.
	 */
	public int getInt(String key) {
		String temp = this.getProperty(key);
		if (temp != null) {
			return Integer.valueOf(temp);
		} else {
			return 0;
		}
	}

	/**
	 * Loads the properties file with the given name for this properties.
	 * 
	 * @param filename
	 *            name of a valid properties file.
	 */
	private void loadFromFile(String filename) {
		InputStream filestream = null;
		try {
			filestream = new FileInputStream(filename);
			this.load(filestream);
		} catch (FileNotFoundException e) {
			System.err.println("The file " + filename
					+ " is not a valid .properties file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not close the file: " + filename);
			e.printStackTrace();
		} finally {
			try {
				filestream.close();
			} catch (IOException e) {
				System.err.println("Could not close the file: " + filename);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the config.properties file as the default properties.
	 */
	public MyProperties() {
		super();

		loadFromFile("config.properties");
	}

	/**
	 * Loads a custom properties file as the default properties.
	 * 
	 * @param filename
	 *            the name (and path if not in the project directory) of the
	 *            file.
	 */
	public MyProperties(String filename) {
		super();

		loadFromFile(filename);
	}

}
