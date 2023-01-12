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
	  final static int port = 9632;

	  public void run() {
	      try (ServerSocket serverSocket = new ServerSocket(port)) {
	    		 
	             System.out.println("Wait Server Launched, listening on port " + port);
	  
	             while (true) {
	                 Socket socket = serverSocket.accept();
	                 InetAddress ip = socket.getInetAddress();
	                 NetworkManager.TabIpSock.put(ip, socket);
	                 System.out.println("New client connected");
	                 ThreadManager.createThreadForChat(socket, false);
	            }
	  
	       } catch (IOException ex) {
	             System.out.println("Server exception: " + ex.getMessage());
	             ex.printStackTrace();
	       }
	  }
}
