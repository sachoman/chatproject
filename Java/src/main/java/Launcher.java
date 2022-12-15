
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import UserPackage.User;
import ViewPackage.ConversationThreadView;
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
		NetworkManager.notifyCo();
        User.setPseudo("sacho");
        NetworkManager.sendPseudo("sacho");
        /*
        Thread.sleep(5000);
        NetworkManager.ChatWithUser(InetAddress.getByName("10.1.5.42"));
        */
	}
}