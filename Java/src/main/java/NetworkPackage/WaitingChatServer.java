package NetworkPackage;

import java.io.BufferedReader;
import ThreadPackage.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ThreadPackage.ListeningChatThread;

public class WaitingChatServer extends Thread{

	  public void run() {
	      try (ServerSocket serverSocket = new ServerSocket(NetworkManager.getTcpAppPort())) {
	    		 
	             System.out.println("Wait Server Launched, listening on port " + NetworkManager.getTcpAppPort());
	  
	             while (true) {
	                 Socket socket = serverSocket.accept();
	                 InetAddress ip = socket.getInetAddress();
	                 NetworkManager.TabIpSock.put(ip, socket);
	                 ThreadManager.createThreadForChat(socket, false);
	            }
	  
	       } catch (IOException ex) {
	             System.out.println("Server exception: " + ex.getMessage());
	             ex.printStackTrace();
	       }
	  }
}
