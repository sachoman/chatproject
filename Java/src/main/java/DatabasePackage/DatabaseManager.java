package DatabasePackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	public static String url = "jdbc:sqlite:chatdb.db";
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	public void initTables () throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String sql = "CREATE TABLE IF NOT EXISTS history (\n"
				+ " id int PRIMARY KEY, \n"
                + "	from_ip text, \n"
				+ " to_ip text, \n"
                + " date text, \n"
                + "	content text \n"
                + ");";
		try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		sql = "CREATE TABLE IF NOT EXISTS users (\n"
				+ "ip text PRIMARY KEY, \n"
				+ "pseudo text, \n"
				+ "connected bool \n"
				+ ");";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public void addUser(String ip, String pseudo) {
		String sql = "INSERT INTO users(ip,pseudo,connected) VALUES(?,?,?)";
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ip);
            pstmt.setString(2, pseudo);
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public void updateUser(String ip, String pseudo, boolean connected_status) {
		String sql = "UPDATE users SET pseudo = ?, connected = ? WHERE ip= ?";
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pseudo);
            pstmt.setBoolean(2, connected_status);
            pstmt.setString(3, ip);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public String getPseudo(String ip) {
		String sql = "SELECT pseudo FROM users WHERE ip=?";
        try (Connection conn = this.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setString(1, ip);
        	try (ResultSet rs = pstmt.executeQuery();){
        		return rs.getString("pseudo");
            }
            // loop through the result set
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return "";
	}
	public String getIp(String pseudo) {
		String sql = "SELECT ip FROM users WHERE pseudo=?";
        try (Connection conn = this.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setString(1, pseudo);
        	try (ResultSet rs = pstmt.executeQuery()){
        		return rs.getString("ip");
            }
        	catch (SQLException e) {
                throw e;
            }
            // loop through the result set
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return "";
	}
	public boolean isConnected(String ip) {
		String sql = "SELECT connected FROM users WHERE ip=?";
        try (Connection conn = this.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setString(1, ip);
        	try (ResultSet rs = pstmt.executeQuery()){
        		return rs.getBoolean("connected");
            }
            // loop through the result set
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return false;
	}
	public boolean existsUser(String ip) {
		return (getPseudo(ip)!="");
	}
	/**
     * @param args the command line arguments
	 * @return 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws ClassNotFoundException {
    	DatabaseManager dbmanager = new DatabaseManager();
        dbmanager.initTables();
        //dbmanager.addUser("192.168.65.21","Paulo l'artichaut");
        System.out.println(dbmanager.getPseudo("192.168.65.21"));
        System.out.println(dbmanager.getIp("Paulo l'artichaut"));
        System.out.println(dbmanager.isConnected("192.168.65.21"));
        dbmanager.updateUser("192.168.65.21","Paulo l'artichaut", false);
        System.out.println(dbmanager.existsUser("192.168.65.21"));
        System.out.println(dbmanager.existsUser("193.168.65.21"));
    }
}
