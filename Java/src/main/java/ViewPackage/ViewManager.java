package ViewPackage;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

import DatabasePackage.DatabaseManager;

public class ViewManager {
	public static Hashtable<InetAddress, ConversationThreadView> TabIpChatThreadView = new Hashtable<InetAddress, ConversationThreadView>();
	public static void newChatThreadView(InetAddress ip) {
		ConversationThreadView cth = new ConversationThreadView(ip.toString());
		TabIpChatThreadView.put(ip,cth);
		cth.start();
	}
	public static void addMessageView(InetAddress ip, String date, String pseudo, String mess) {
		ConversationThreadView cth = TabIpChatThreadView.get(ip);
		cth.addMessage(pseudo, date, mess);
	}
	public static void main(String args[]) throws UnknownHostException, InterruptedException {
		newChatThreadView(InetAddress.getByName("10.1.1.54"));
		
	}
	public static void endChat(InetAddress ip) {
		ConversationThreadView cth = TabIpChatThreadView.get(ip);
		TabIpChatThreadView.remove(ip);
		cth.interrupt();
	}
}
