package NetworkPackage;

import java.io.IOException;

import DatabasePackage.DatabaseManager;

public class NetworkManager {
	private static int TCP_app_port = 9400;
	private static int UDP_app_port = 9500;
	public char buffer;
	public static int getUdpAppPort() {
		return UDP_app_port;
	}
	public static int getTcpAppPort() {
		return TCP_app_port;
	}
	public NetworkManager() throws ClassNotFoundException, IOException {
		NetworkListeningThread th = new NetworkListeningThread(UDP_app_port);
	}
    public static void main(String[] args) throws ClassNotFoundException, IOException {
    	DatabaseManager dbmanager = new DatabaseManager();
        dbmanager.initTables();
        /*
        //dbmanager.addUser("192.168.65.21","Paulo l'artichaut");
        System.out.println(dbmanager.getPseudo("192.168.65.21"));
        System.out.println(dbmanager.getIp("Paulo l'artichaut"));
        System.out.println(dbmanager.isConnected("192.168.65.21"));
        DatabaseManager.updateUser("192.168.65.21","Paulo l'artichaut", false);
        System.out.println(dbmanager.existsUser("192.168.65.21"));
        System.out.println(dbmanager.existsUser("193.168.65.21"));
        */
        NetworkManager nm = new NetworkManager();
    }
}