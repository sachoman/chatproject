package NetworkPackage;

import DatabasePackage.DatabaseManager;

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
		if (Netmessage == "co") {
			if (DatabaseManager.existsUser(NetclientAddress.toString())) {
				DatabaseManager.updateCoStatus(NetclientAddress.toString(), true);
			}
			else {
				DatabaseManager.addUser(NetclientAddress.toString(), "inconnu");
			}
		}
		else {
			
			if (Netmessage == "deco") {
				if (DatabaseManager.existsUser(NetclientAddress.toString())) {
					DatabaseManager.updateCoStatus(NetclientAddress.toString(), false);
				}
				else {
					DatabaseManager.addUser(NetclientAddress.toString(), "inconnu");
					DatabaseManager.updateCoStatus(NetclientAddress.toString(), false);
				}
			}
			else {
				String[] message_spli = Netmessage.split(" ");
				if (message_spli[0] == "pseudo") {
					if (DatabaseManager.existsUser(NetclientAddress.toString())) {
						DatabaseManager.updateUser(NetclientAddress.toString(),message_spli[1], true);
					}
					else {
						DatabaseManager.addUser(NetclientAddress.toString(), message_spli[1]);
					}
				}
			}
		}
	}
}
