package ViewPackage;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class ViewManager {
	public static Hashtable<InetAddress, ConversationThreadView> TabIpChatThreadView = new Hashtable<InetAddress, ConversationThreadView>();
	public static void newChatThreadView(InetAddress ip) {
		ConversationThreadView cth = new ConversationThreadView(ip.toString());
		TabIpChatThreadView.put(ip,cth);
		cth.start();
	}
	public static void addMessageView(InetAddress ip, String[] mes) {
		ConversationThreadView cth = TabIpChatThreadView.get(ip);
		cth.addMessage(mes);
	}
	public static void main(String args[]) throws UnknownHostException, InterruptedException {
		newChatThreadView(InetAddress.getByName("10.1.1.54"));
		
		Thread.sleep(500);
		String[] mes  = {"paul","date","contenu message"};
		addMessageView(InetAddress.getByName("10.1.1.54"), mes);
		
	}
}
