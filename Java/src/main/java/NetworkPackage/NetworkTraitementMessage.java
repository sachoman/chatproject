package NetworkPackage;

import DatabasePackage.DatabaseManager;
import UserPackage.User;
import ViewPackage.ViewManager;

import java.net.InetAddress;

public class NetworkTraitementMessage extends Thread{
	private InetAddress NetclientAddress;
	private int netclienPort;
	private String Netmessage;
	public NetworkTraitementMessage(InetAddress clientAddress, int clientPort, String message) {
		this.NetclientAddress = clientAddress;
		this.Netmessage = message;
		this.netclienPort = clientPort;
		
	}
	public void run() {
		System.out.println("debut traitement");
		if (Netmessage.equals("co")) {
			if (DatabaseManager.existsUser(NetclientAddress.toString())) {
				DatabaseManager.updateCoStatus(NetclientAddress.toString(), true);
			}
			else {
				DatabaseManager.addUser(NetclientAddress.toString(), "inconnu");
			}
			NetworkManager.sendRepCo();
			NetworkManager.sendPseudo(User.getPseudo());
			ViewManager.AccueilThRef.updateUsersView();
		}
		else {
			
			if (Netmessage.equals("deco")) {
				if (DatabaseManager.existsUser(NetclientAddress.toString())) {
					DatabaseManager.updateCoStatus(NetclientAddress.toString(), false);
				}
				else {
					DatabaseManager.addUser(NetclientAddress.toString(), "inconnu");
					DatabaseManager.updateCoStatus(NetclientAddress.toString(), false);
				}
				ViewManager.AccueilThRef.updateUsersView();
			}
			else {
				if (Netmessage.equals("repco")){
					if (DatabaseManager.existsUser(NetclientAddress.toString())) {
						DatabaseManager.updateCoStatus(NetclientAddress.toString(), true);
					}
					else {
						DatabaseManager.addUser(NetclientAddress.toString(), "inconnu");
					}
				}
				else {
					String[] message_spli = Netmessage.split(" ");
					if (message_spli[0].equals("pseudo")) {
						if (DatabaseManager.existsUser(NetclientAddress.toString())) {
							DatabaseManager.updateUser(NetclientAddress.toString(),message_spli[1], true);
						}
						else {
							DatabaseManager.addUser(NetclientAddress.toString(), message_spli[1]);
						}
						ViewManager.AccueilThRef.updateUsersView();
					}
					else {
						System.out.println("pas pour nous");
					}
				}
			}
		}
	}
}
