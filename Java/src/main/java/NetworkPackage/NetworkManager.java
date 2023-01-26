package NetworkPackage;

import ThreadPackage.*;
import UserPackage.User;

import java.util.List;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import DatabasePackage.DatabaseManager;

public class NetworkManager {
	public static Hashtable<InetAddress, Socket> TabIpSock = new Hashtable<InetAddress, Socket>();
	public static Hashtable<Socket, ObjectOutputStream> TabSockOut = new Hashtable<Socket, ObjectOutputStream>();
	private static String broadcast="255.255.255.255";
	private static int TCP_app_port = 9632;
	private static int UDP_app_port = 9500;
	public char buffer;
	public static int getUdpAppPort() {
		return UDP_app_port;
	}
	public static int getTcpAppPort() {
		return TCP_app_port;
	}
	public static String getBroadcast() {
		return broadcast;
	}
	public static InetAddress stringToInet(String ip) throws UnknownHostException {
		return InetAddress.getByName(ip.substring(1));
	}
	public static void listOurAddresses() throws SocketException {
		Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements())
		{
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration<?> ee = n.getInetAddresses();
		    while (ee.hasMoreElements())
		    {
		        InetAddress i = (InetAddress) ee.nextElement();
		    }
		}
	}
	public static void removeOutFromSock(Socket sock) {
		ObjectOutputStream out = TabSockOut.get(sock);
		try {
			out.close();
		} catch (IOException e) {
		}
		TabSockOut.remove(sock);
	}
	public static void StartNetworkManager() throws ClassNotFoundException, IOException {
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
	    while (en.hasMoreElements()) {
	      NetworkInterface ni = en.nextElement();
	      //System.out.println(" Display Name = " + ni.getDisplayName());

	      List<InterfaceAddress> list = ni.getInterfaceAddresses();
	      Iterator<InterfaceAddress> it = list.iterator();

	      while (it.hasNext()) {
	        InterfaceAddress ia = it.next();
	        //System.out.println(" Broadcast = " + ia.getBroadcast());
	        if (ia.getBroadcast() != null) {
	        	broadcast = ia.getBroadcast().toString();
	        }
	      }
	    }
		NetworkListeningThread th = new NetworkListeningThread(UDP_app_port);
		th.start();
        WaitingChatServer waitserv = new WaitingChatServer();
        waitserv.start();
	}
	public static void notifyCo() {
		NetworkSendingThread nths = new NetworkSendingThread(0);
		nths.start();
	}
	public static void notifyDeCo() {
		NetworkSendingThread nths = new NetworkSendingThread(1);
		nths.start();
	}
	public static void sendRepCo() {
		NetworkSendingThread nths = new NetworkSendingThread(3);
		nths.start();
	}
	public static void sendPseudo(String pseudo) {
		NetworkSendingThread nths = new NetworkSendingThread(2);
		nths.setPseudo(pseudo);
		nths.start();
	}
	public boolean SendUpdatePseudo(String pseudo) {
		if (DatabaseManager.checkAvailability(pseudo)) {
			NetworkSendingThread th = new NetworkSendingThread(2);
			th.setPseudo(pseudo);
			th.start();
			return true;
		} else {
			return false;
		}
	}
	public static void ChatWithUser(InetAddress ip) {
		try {
			Socket socket = new Socket(ip,NetworkManager.getTcpAppPort());
			NetworkManager.TabIpSock.put(ip, socket);
			ThreadManager.createThreadForChat(socket, true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendMessage(String msg, InetAddress ip) {
		SendingChatThread th = new SendingChatThread();
		th.setMessage(msg);
		th.setSocket(TabIpSock.get(ip));
		th.setOut(TabSockOut.get(TabIpSock.get(ip)));
		th.start();
	}
}
