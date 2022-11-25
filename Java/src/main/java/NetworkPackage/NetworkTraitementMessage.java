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
		if (Netmessage == "co") {
			DatabaseManager.updateCoStatus(NetclientAddress.toString(), true);
		}
		else {
			if (Netmessage == "deco") {
				DatabaseManager.updateCoStatus(NetclientAddress.toString(), false);
			}
			else {
				String[] message_spli = Netmessage.split(" ");
				DatabaseManager.updateUser(NetclientAddress.toString(),message_spli[1], true);
			}
		}
	}
}
