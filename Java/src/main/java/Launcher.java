

import java.io.IOException;

import DatabasePackage.DatabaseManager;
import ViewPackage.Welcome;
import NetworkPackage.*;

public class Launcher {
	public static void main(String[] args) throws Exception  {
		try {
			DatabaseManager.initTables();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			NetworkManager.StartNetworkManager();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseManager.clearDBUsers();
		new Welcome();
		/*
        User.setPseudo("sacho");
        NetworkManager.sendPseudo("sacho");
        Accueil thaccueil = new Accueil();
        ViewManager.AccueilThRef = thaccueil;
        thaccueil.start();
        */
        /*NetworkManager.ChatWithUser(InetAddress.getByName("10.1.5.11"));*/
        
	}
}
