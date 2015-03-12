package sztaki.hexaa;

public interface DataLoader {

	/**
	 * Searches for the value pair of the given string, may return null if the
	 * string can't be found.
	 * 
	 * @param key
	 *            String, representing a key with an expected value pair.
	 * @return String, the value associated with the given key.
	 */
	public String getString(String key);

	/**
	 * Searches for the value pair of the given string, may return null if the
	 * string can't be found.
	 * 
	 * @param key
	 *            String, representing a key with an expected value pair.
	 * @return Integer, the value associated with the given key.
	 */
	public Integer getInt(String key);

}
