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
public class DataProp implements DataLoader {

	/**
	 * Returns a string value for the given key. Takes care of the data load.
	 * 
	 * @param key
	 *            String, key for the property.
	 * @return String, can be null if the key is not found.
	 */
	public String getString(String key) {
		synchronized (DataProp.class) {
			if (this.isEmpty()) {
				this.load();
			}
		}

		return instance.getProperty(key);
	}

	/**
	 * Returns an int value for the given key. Takes care of the data load.
	 * 
	 * @param key
	 *            String, key for the property.
	 * @return int, can be 0 if the key is not found.
	 */
	public Integer getInt(String key) {
		synchronized (DataProp.class) {
			if (this.isEmpty()) {
				this.load();
			}
		}
		String temp = instance.getProperty(key);
		if (temp != null) {
			return Integer.valueOf(temp);
		} else {
			return null;
		}
	}

	// public String getStatusLine(String key) {
	// // TODO Auto-generated method stub
	// System.err.println("StatusLine request not yet implemented");
	// return null;
	// }

//	public String getAPI(String key) {
//		// TODO Auto-generated method stub
//		System.err.println("API request not yet implemented");
//		return null;
//	}

	
	/* *** Property specific data storage *** */
	/**
	 * Static instance of a Properties to avoid multiple load or inconsistency.
	 */
	private static Properties instance = new Properties();
	
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
			instance.load(filestream);
		} catch (FileNotFoundException e) {
			System.err.println("The file " + filename
					+ " is not a valid .properties file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not read the file: " + filename);
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
	 * Loads all necessary .properties file.
	 */
	private void load() {
		if (this.isEmpty()) {
			loadFromFile("config.properties");
			//			loadFromFile("statusline.properties");
		}		
	}

	/**
	 * Returns true if the data is not loaded.
	 * 
	 * @return
	 */
	private boolean isEmpty() {
		return instance.isEmpty();
	}


}
