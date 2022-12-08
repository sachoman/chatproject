package NetworkPackage;

import ThreadPackage.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

import DatabasePackage.DatabaseManager;

public class NetworkManager {
	private static Hashtable<InetAddress, Socket> TabIpSock = new Hashtable<InetAddress, Socket>();
	private static Hashtable<Socket, ObjectOutputStream> TabSockOut = new Hashtable<Socket, ObjectOutputStream>();
	private static String broadcast="10.1.255.255";
	private static int TCP_app_port = 9400;
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
	public static void StartNetworkManager() throws ClassNotFoundException, IOException {
		NetworkListeningThread th = new NetworkListeningThread(UDP_app_port);
		th.start();
        WaitingChatServer waitserv = new WaitingChatServer();
        waitserv.start();
	}
	public void SendUpdatePseudo(String pseudo) {
		NetworkSendingThread th = new NetworkSendingThread(2);
		th.setPseudo(pseudo);
		th.start();
	}
	public static void ChatWithUser(InetAddress ip) {
		try {
			Socket socket = new Socket(ip,9632);
			NetworkManager.TabIpSock.put(ip, socket);
			ObjectOutputStream out;
			out = new ObjectOutputStream(socket.getOutputStream());
			NetworkManager.TabSockOut.put(socket, out);
			ThreadManager.createThreadForChat(socket);
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
    public static void main(String[] args) throws Exception {
        DatabaseManager.initTables();
        /*
        String[][] temp;
        temp = DatabaseManager.getMessages("1.1.1.1");
        for (int i = 0; i < temp.length; i++) { //this equals to the row in our matrix.
            for (int j = 0; j < temp[i].length; j++) { //this equals to the column in each row.
               System.out.print(temp[i][j] + " ");
            }
            System.out.println(); //change line on console as row comes to end in the matrix.
         }
        /*
        //dbmanager.addUser("192.168.65.21","Paulo l'artichaut");
        System.out.println(dbmanager.getPseudo("192.168.65.21"));
        System.out.println(dbmanager.getIp("Paulo l'artichaut"));
        System.out.println(dbmanager.isConnected("192.168.65.21"));
        DatabaseManager.updateUser("192.168.65.21","Paulo l'artichaut", false);
        System.out.println(dbmanager.existsUser("192.168.65.21"));
        System.out.println(dbmanager.existsUser("193.168.65.21"));
        DatabaseManager.addUser("1.1.1.1", "Paulo");
        System.out.println(DatabaseManager.checkAvailability("Paulo"));
        System.out.println(DatabaseManager.checkAvailability("totoo"));
        */
        
        NetworkManager.StartNetworkManager();
        
        NetworkManager.ChatWithUser(InetAddress.getByName("10.1.5.232"));
        
        Thread.sleep(500);
        NetworkManager.sendMessage("hello mec",InetAddress.getByName("10.1.5.232"));
        while (true) {
        	Thread.sleep(2000);
            NetworkManager.sendMessage("hello mec while true",InetAddress.getByName("10.1.5.232"));
        }
    }
}
