package DatabasePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseManager {
	public static String url = "jdbc:sqlite:chatdb.db";

	public static class PasswordHasher {
	    public static String hashPassword(String password) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
	            StringBuilder hexString = new StringBuilder();
	            for (byte b : hash) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) hexString.append('0');
	                hexString.append(hex);
	            }
	            return hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
    private static Connection connect() {
    	Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	public static void initTables () throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String sql = "CREATE TABLE IF NOT EXISTS history (\n"
				+ " id int PRIMARY KEY, \n"
                + "	from_ip text, \n"
				+ " to_ip text, \n"
                + " date text, \n"
                + "	content text \n"
                + ");";
		try (Connection conn = DatabaseManager.connect();
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
		try (Connection conn = DatabaseManager.connect();
				Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		sql = "CREATE TABLE IF NOT EXISTS pwd (\n"
				+ "id int PRIMARY KEY, \n"
				+ "password text \n"
				+ ");";
		try (Connection conn = DatabaseManager.connect();
				Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	
// Users
	public static void addUser(String ip, String pseudo) {
		String sql = "INSERT INTO users(ip,pseudo,connected) VALUES(?,?,?)";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ip);
            pstmt.setString(2, pseudo);
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void updateUser(String ip, String pseudo, boolean connected_status) {
		String sql = "UPDATE users SET pseudo = ?, connected = ? WHERE ip= ?";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pseudo);
            pstmt.setBoolean(2, connected_status);
            pstmt.setString(3, ip);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void DecoAllUsers() {
		System.out.println("all users deco");
		String sql = "UPDATE users SET connected = ? ";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, false);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void updateCoStatus(String ip, boolean connected_status) {
		String sql = "UPDATE users SET connected = ? WHERE ip= ?";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, connected_status);
            pstmt.setString(2, ip);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static String getPseudo(String ip) {
		String sql = "SELECT pseudo FROM users WHERE ip=?";
        try (Connection conn = connect();
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
	public static String getIp(String pseudo) {
		String sql = "SELECT ip FROM users WHERE pseudo=?";
        try (Connection conn = DatabaseManager.connect();
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
        try (Connection conn = DatabaseManager.connect();
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
	public static boolean existsUser(String ip) {
		return (getPseudo(ip)!="");
	}
	
	
// History
	public static void storeMessage(String ip_from, String ip_to, String date, String message) {
		// First, we need to get the id on the last line
		int id = 0;
		String sql = "SELECT MAX(id) FROM history";
        try (Connection conn = DatabaseManager.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	try (ResultSet rs = pstmt.executeQuery()){
        		id = rs.getInt(1);
            }
        	catch (SQLException e) {
                throw e;
            }
        	sql = "INSERT INTO history(id,from_ip,to_ip,date,content) VALUES(?,?,?,?,?)";
    		try (Connection conn2 = connect();
                    PreparedStatement pstmt2 = conn2.prepareStatement(sql)) {
                pstmt2.setInt(1, id + 1);
                pstmt2.setString(2, ip_from);
                pstmt2.setString(3, ip_to);
                pstmt2.setString(4, date);
                pstmt2.setString(5, message);
                pstmt2.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static String[][] getMessages(String ip) {
		// First, we need to get the id on the last line
		int nb_rows = 0;
		String sql = "SELECT COUNT(*) FROM history WHERE from_ip=? OR to_ip=?";
        try (Connection conn = DatabaseManager.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setString(1, ip);
        	pstmt.setString(2, ip);
        	try (ResultSet rs = pstmt.executeQuery()){
        		nb_rows = rs.getInt(1);
            }
        	catch (SQLException e) {
                throw e;
            }
        	sql = "SELECT from_ip, date, content FROM history WHERE from_ip=? OR to_ip=? ORDER BY date";
            try (Connection conn2 = DatabaseManager.connect();
            		PreparedStatement pstmt2 = conn2.prepareStatement(sql)){
            	pstmt2.setString(1, ip);
            	pstmt2.setString(2, ip);
            	try (ResultSet rs = pstmt2.executeQuery()){
            		String[][] tab = new String[nb_rows][3];
            		int i = 0;
            		while (rs.next()) {
            			tab[i][0] = rs.getString(1);
            			tab[i][1] = rs.getString(2);
            			tab[i][2] = rs.getString(3);
            			i++;
            		}
            		return tab;
                }
            	catch (SQLException e) {
                    throw e;
                }
                // loop through the result set
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
	public static void clearConversation(String ip) {
		// First, we need to get the id on the last line
		String sql = "DELETE FROM history WHERE from_ip=? OR to_ip=?";
        try (Connection conn = DatabaseManager.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setString(1, ip);
        	pstmt.setString(2, ip);
        	pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void clearDBHistory(){
		String sql = "DELETE FROM history";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void clearDBUsers(){
		String sql = "DELETE FROM users";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void clearPwd(){
		String sql = "DELETE FROM pwd";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static String[][] getConnectedUsers() throws ClassNotFoundException {
		// First, we need to get the id on the last line
		initTables();
		int nb_rows = 0;
		String sql = "SELECT COUNT(*) FROM users WHERE connected = ?";
        try (Connection conn = connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setBoolean(1, true);
        	/*pstmt.executeUpdate();*/
        	try (ResultSet rs = pstmt.executeQuery()){
        		nb_rows = rs.getInt(1);
            }
        	catch (SQLException e) {
                throw e;
            }
        	sql = "SELECT ip, pseudo FROM users WHERE connected = ?";
            try (Connection conn2 = DatabaseManager.connect();
            		PreparedStatement pstmt2 = conn2.prepareStatement(sql)){
            	pstmt2.setBoolean(1, true);
            	//pstmt2.executeUpdate();
            	try (ResultSet rs = pstmt2.executeQuery()){
            		String[][] tab = new String[nb_rows][2];
            		int i = 0;
            		while (rs.next()) {
            			tab[i][0] = rs.getString(1);
            			tab[i][1] = rs.getString(2);
            			i++;
            		}
            		return tab;
                }
            	catch (SQLException e) {
                    throw e;
                }
                // loop through the result set
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
	public static boolean checkAvailability(String pseudo) {
		String sql = "SELECT COUNT(*) FROM users WHERE pseudo=? AND connected=?";
		int nb;
        try (Connection conn = DatabaseManager.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)){
        	pstmt.setString(1, pseudo);
        	pstmt.setBoolean(2,true);
        	try (ResultSet rs = pstmt.executeQuery()){
        		nb = rs.getInt(1);
        		System.out.println(nb);
        		if (nb == 0) {
        			return true;
        		} else {
        			return false;
        		}
            }
            // loop through the result set
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return false;
	}
	
	
// Pwd
	public static boolean pwdExists() {
		String sql = "SELECT COUNT(*) FROM pwd";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery();){
        		int nb;
        		nb = rs.getInt(1);
        		if (nb == 0) {
        			return false;
        		} else {
        			return true;
        		}
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return false;
	}
	public static void setPwd(String pwd) {
		String sql = "INSERT INTO pwd(id,password) VALUES(?,?)";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, PasswordHasher.hashPassword(pwd));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static boolean updatePwd(String old_pwd, String new_pwd) {
		if (testPwd(old_pwd)) {
			String sql = "UPDATE pwd SET password = ? WHERE id=1";
			try (Connection conn = connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, PasswordHasher.hashPassword(new_pwd));
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
			return true;
		} else {
			return false;
		}
	}
	public static boolean testPwd(String pwd) {
		String sql = "SELECT password FROM pwd WHERE id=1";
		try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery();){
        		String password;
        		password = rs.getString("password");
        		if (password.equals(PasswordHasher.hashPassword(pwd))) {
        			return true;
        		} else {
        			return false;
        		}
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return false;
	}
	/**
     * @param args the command line arguments
	 * @return 
     * @throws ClassNotFoundException 
     */
	/*
    public static void main(String[] args) throws ClassNotFoundException {
    	DatabaseManager dbmanager = new DatabaseManager();
        dbmanager.initTables();
        //dbmanager.addUser("192.168.65.21","Paulo l'artichaut");
        System.out.println(dbmanager.getPseudo("192.168.65.21"));
        System.out.println(dbmanager.getIp("Paulo l'artichaut"));
        System.out.println(dbmanager.isConnected("192.168.65.21"));
        DatabaseManager.updateUser("192.168.65.21","Paulo l'artichaut", false);
        System.out.println(dbmanager.existsUser("192.168.65.21"));
        System.out.println(dbmanager.existsUser("193.168.65.21"));
    }
    */
}