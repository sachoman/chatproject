package ViewPackage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import ThreadPackage.ThreadManager;

public class ViewManager {
	public static Accueil AccueilThRef;
	public static Hashtable<InetAddress, ConversationThreadView> TabIpChatThreadView = new Hashtable<InetAddress, ConversationThreadView>();
	public static void newChatThreadView(InetAddress ip, Boolean bool) {
		ConversationThreadView cth = new ConversationThreadView(ip.toString(), bool);
		TabIpChatThreadView.put(ip,cth);
		cth.start();
	}
	public static void addMessageView(InetAddress ip, String date, String pseudo, String mess) {
		System.out.println(ip);
		ConversationThreadView cth = TabIpChatThreadView.get(ip);
		cth.addMessage(pseudo, date, mess);
	}
	/*
	public static void main(String args[]) throws UnknownHostException, InterruptedException {
		newChatThreadView(InetAddress.getByName("10.1.1.54"));
		
	}
	*/
	public static void endChat(InetAddress ip) {
		ConversationThreadView cth = TabIpChatThreadView.get(ip);
		cth.frame.dispose();
		TabIpChatThreadView.remove(ip);
			if ((ViewManager.AccueilThRef.inetIp != null)&&(ViewManager.AccueilThRef.inetIp == ip)){
				ViewManager.AccueilThRef.fermeConv();
			}
		cth.interrupt();
	}
	public static void endAllViews() {
		for(Entry<InetAddress, ConversationThreadView> entry: TabIpChatThreadView.entrySet()){
            entry.getValue().interrupt();
            TabIpChatThreadView.remove(entry.getKey());
        }
    }

}
