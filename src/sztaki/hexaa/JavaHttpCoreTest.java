package sztaki.hexaa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Just for random things, basically don't use it.
 */
public class JavaHttpCoreTest {

	/**
	 * Main program starting point.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
//		new Authenticator().authenticate(Const.HEXAA_FEDID);
//		new JavaHttpCoreTest().properties();
//		new Authenticator().loadProperties();
//		try {
//		System.out.println(new BasicCall().getResponseJSONObject(Const.Api.PROPERTIES, BasicCall.REST.GET));
//		} catch(ResponseTypeMismatchException ex){
//			System.out.println("Properties unreachable.");
//		}
//		System.out.println(new Authenticator().getAPIKey(Const.MASTER_SECRET));
//		System.out.println(new String("355f0db4244b2304d1cc8c20944415aa226beab36c8dfa36fb408c7d4cd0eaca").length());
//		new DatabaseManipulator().dropDatabase();
		
		MyProperties properties = new MyProperties();
		
		for (Entry e : properties.entrySet()) {
			System.out.println(e);
		}
		
	}

//	private void properties() {
//		Properties prop = new Properties();
//		OutputStream output = null;
//
//		File f = new File(Const.PROPERTIES);
//		if (f.exists()) {
//			return;
//		} else {
//			System.out.println("The file config.properties does not exists,"
//					+ " creating it with default attributes.");
//			System.err
//					.println("The file config.properties does not exists, no"
//							+ " properties can be read, if your setup does not match the"
//							+ " default this may cause inconsitent tests (mostly failed"
//							+ " tests and errors).");
//		}
//
//		try {
//
//			output = new FileOutputStream(Const.PROPERTIES);
//
//			// set the properties value
//			prop.setProperty("port", "80");
//			prop.setProperty("ssh", "20");
//			prop.setProperty("host", "localhost");
//			prop.setProperty("fedid", "tesztAdmin@sztaki.hu");
//			prop.setProperty("master_secret",
//					"7lrfjlpu5br2vpv1jcaogdz481b28xf7lz85wqmv");
//
//			// save properties to project root folder
//			prop.store(output, null);
//
//		} catch (IOException io) {
//		} finally {
//			if (output != null) {
//				try {
//					output.close();
//				} catch (IOException e) {
//				}
//			}
//
//		}
//	}
}
