

import java.io.IOException;

import DatabasePackage.DatabaseManager;
import ViewPackage.Welcome;
import NetworkPackage.*;

public class Launcher {
	public static void main(String[] args) throws Exception  {
		try {
			DatabaseManager.initTables();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			NetworkManager.StartNetworkManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DatabaseManager.clearDBUsers();
		new Welcome();
        
	}
}
