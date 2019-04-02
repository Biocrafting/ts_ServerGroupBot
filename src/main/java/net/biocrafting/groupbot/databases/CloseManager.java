package net.biocrafting.groupbot.databases;




import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Insipiriert von  https://commons.apache.org/proper/commons-dbutils/apidocs/src-html/org/apache/commons/dbutils/DbUtils.html
 * @author Marcel
 */
public final class CloseManager {

	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
	
	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	public static void close(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}
	
    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
       } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

	public static void closeQuietly(ResultSet rs) {
		try {
			close(rs);
		} catch (SQLException e) { // NOPMD
			// quiet
		}
	}

	public static void closeQuietly(Statement stmt) {
		try {
			close(stmt);
		} catch (SQLException e) { // NOPMD
			// quiet
		}
	}

	public static void closeQuietly(Connection con, Statement stm, ResultSet rs) { 
		try {
			closeQuietly(rs);
		} finally {
			try {
				closeQuietly(stm);
			} finally {
				closeQuietly(con);
			}
		}

	}
}
