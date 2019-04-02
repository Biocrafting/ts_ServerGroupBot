package net.biocrafting.groupbot.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.biocrafting.groupbot.bot.Configuration;

/**
 * @author Marcel
 */
public class DBConnection {
	private static DBConnection instance;

	public static Connection getConnection() throws ConnectionException {
		if (instance == null) {
			instance = new DBConnection();
		}
		try {
			return instance.establish();
		} catch (SQLException e) {
			throw new ConnectionException(e.getMessage(), e.getCause());
		}
	}
    
	private DBConnection() {}
    
	private Connection establish() throws SQLException, ConnectionException {
		try {
			Class.forName(Configuration.DB_DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ConnectionException(e.getMessage(), e.getCause());
		}
		final String dbConnectUrl = Configuration.DB_URL + Configuration.PROPERTY_DB_SERVER + "/";
		Connection conn = DriverManager.getConnection(dbConnectUrl + Configuration.PROPERTY_DB_NAME,
				Configuration.PROPERTY_DB_USER , Configuration.PROPERTY_DB_PASSWORD);
		return conn;
	}
}
