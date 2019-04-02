package net.biocrafting.groupbot.bot;

public class Configuration {
	
	/**
	 * ######################################### 
	 *            SET YOUR DATABASE 
	 *            CONFIGURATION HERE
	 * ######################################### 
	 */
	public static final String PROPERTY_DB_NAME = "";
	public static final String PROPERTY_DB_USER = "";
	public static final String PROPERTY_DB_PASSWORD = "";
	public static final String PROPERTY_DB_SERVER = "";
	/**
	 * ######################################### 
	 * ######################################### 
	 */
	
	
	private static Configuration instance;
	private Boolean debugEnable = Boolean.TRUE;
	
	private Configuration() {}
	public static boolean isDebugEnable() {
		if (instance == null ) 
			instance = new Configuration();
		return instance.debugEnable;
	}
	public static void setDebug(boolean debug) {
		if (instance == null)
			instance = new Configuration();
		instance.debugEnable = debug;
	}
}