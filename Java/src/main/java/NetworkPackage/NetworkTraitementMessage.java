package NetworkPackage;

import DatabasePackage.DatabaseManager;
import UserPackage.User;
import ViewPackage.ConversationThreadView;
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
		if (Netmessage.equals("co")) {
			if (DatabaseManager.existsUser(NetclientAddress.toString())) {
				DatabaseManager.updateCoStatus(NetclientAddress.toString(), true);
			}
			else {
			}
			NetworkManager.sendRepCo();
			NetworkManager.sendPseudo(User.getPseudo());
			try {
				ViewManager.AccueilThRef.updateUsersView();
			}
			catch(Exception e){
				
			}
		}
		else {
			
			if (Netmessage.equals("deco")) {
				if (DatabaseManager.existsUser(NetclientAddress.toString())) {
					DatabaseManager.updateCoStatus(NetclientAddress.toString(), false);
				}
				try {
					ViewManager.AccueilThRef.updateUsersView();
				}
				catch (Exception e) {
					
				}
			}
			else {
				if (Netmessage.equals("repco")){
					if (DatabaseManager.existsUser(NetclientAddress.toString())&&(User.pseudo!=null)) {
						DatabaseManager.updateCoStatus(NetclientAddress.toString(), true);
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
						try{
							ViewManager.AccueilThRef.updateUsersView();
							try {
								ConversationThreadView cth = ViewManager.TabIpChatThreadView.get(NetclientAddress);
								cth.updateConvView();
							}
							catch (Exception e) {
								
							}
						}
						catch(Exception e){
							
						}
					}
					else {
					}
				}
			}
		}
	}
}
