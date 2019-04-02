package net.biocrafting.groupbot.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.biocrafting.groupbot.databases.DBConnection;
import net.biocrafting.groupbot.databases.CloseManager;
import net.biocrafting.groupbot.databases.ConnectionException;

public class GroupBot {
	
	private List<Integer> userIds = new ArrayList<Integer>(); 
	private List<Integer> serverGroups = new ArrayList<Integer>();
	private int server_id;
	private Boolean random = Boolean.FALSE;
	
	public GroupBot(String[] args) {
		if (!parseArguments(args)) {
			throw new IllegalArgumentException(
					"Invalid parameters. Valid parameters are: \n "
					+ "  server=ID \n"
					+ "   random=<true|false> \n"
					+ "   debug=<true|false> \n"
					+ "   1,2,3,4,5,6,6 <a list of server groups>");
		};

	}
	
	private boolean parseArguments(String[] args) {
		try {
			for (String arg : args) {
				String subArg = arg.substring(arg.lastIndexOf("=") + 1);
				if(arg.startsWith("server=")) {
					server_id = Integer.parseInt(subArg);
				} else if(arg.startsWith("random=")) {
					this.random = Boolean.valueOf(subArg);
				} else if(arg.startsWith("debug=")) {
					Configuration.setDebug(Boolean.valueOf(subArg));
				} else {
					String[] groups = arg.split(",");
					for (String group : groups) {
						serverGroups.add(Integer.parseInt(group));
					}
				}
			}	
			if (serverGroups.isEmpty() || server_id == 0) {
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public boolean extractAll() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement("SELECT client_id FROM clients WHERE server_id=?");
			stmt.setInt(1, server_id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				userIds.add(rs.getInt("client_id"));
			}
			return true;
		} catch (ConnectionException | SQLException e) {
			if (Configuration.isDebugEnable()) {
				e.printStackTrace();
			}
			return false;
		} finally {
			CloseManager.closeQuietly(conn, stmt, rs);
		}
	}
	
	public boolean setGroup() throws ConnectionException, SQLException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO group_server_to_client (group_id, server_id, id1, id2) VALUES (?,?,?,?);");
			//stmt = conn.prepareStatement("DELETE FROM group_server_to_client WHERE group_id=? AND server_id=? AND id1=? AND id2=?;");
			
			
			for (int user :userIds) {
				if (random) {
					ArrayList<Integer> randomGroups = new ArrayList<Integer>(this.serverGroups);
					Random random = new Random();
					for (int i = 0; i < this.serverGroups.size() / 2; i++) {
						randomGroups.remove(random.nextInt(randomGroups.size()));
					}
					for (int group : randomGroups) {
						stmt.setInt(1, group);
						stmt.setInt(2, server_id);
						stmt.setInt(3, user);
						stmt.setInt(4, 0);
						stmt.addBatch();
					}
				} else {
					for (int group : serverGroups) {
						stmt.setInt(1, group);
						stmt.setInt(2, server_id);
						stmt.setInt(3, user);
						stmt.setInt(4, 0);
						stmt.addBatch();
					}
				}
			}
			stmt.executeBatch();
			System.out.println("Updated ServerGroups of " + userIds.size() + " clients");
			return true;
		} finally {
			CloseManager.closeQuietly(conn, stmt, rs);
		}
		
	}
}
