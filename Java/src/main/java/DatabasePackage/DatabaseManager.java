package DatabasePackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	public static String url = "jdbc:sqlite:chatdb.db";
	public static void connectDatabase() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Database connected.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
		try (Connection conn = DriverManager.getConnection(url);
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
		try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public static void addUser(String ip, String pseudo) {
		String sql = "INSERT INTO users (IP,PSEUDO,CONNECTED) " +
                "VALUES ("+ip+","+pseudo+",True);"; 
		try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	/**
     * @param args the command line arguments
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws ClassNotFoundException {
        connectDatabase();
        initTables();
        addUser("192.168.65.21","Paulo l'artichaut");
    }
}
