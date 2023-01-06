

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import UserPackage.User;
import ViewPackage.Accueil;
import ViewPackage.ConversationThreadView;
import ViewPackage.ViewManager;
import NetworkPackage.*;
import ThreadPackage.*;

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
		DatabaseManager.DecoAllUsers();
		NetworkManager.notifyCo();
        User.setPseudo("sacho");
        NetworkManager.sendPseudo("sacho");
        Accueil thaccueil = new Accueil();
        ViewManager.AccueilThRef = thaccueil;
        thaccueil.start();
        /*NetworkManager.ChatWithUser(InetAddress.getByName("10.1.5.11"));*/
        
	}
}
