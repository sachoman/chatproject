package NetworkPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import ThreadPackage.AcceptChatThread;

public class WaitingChatServer extends Thread{
	  final static int port = 9632;

	  public void run() {
	      try (ServerSocket serverSocket = new ServerSocket(port)) {
	    		 
	             System.out.println("Wait Server Launched, listening on port " + port);
	  
	             while (true) {
	                 Socket socket = serverSocket.accept();
	                 System.out.println("New client connected");
	                 AcceptChatThread ath = new AcceptChatThread(socket);
	                 ath.start();
	            }
	  
	       } catch (IOException ex) {
	             System.out.println("Server exception: " + ex.getMessage());
	             ex.printStackTrace();
	       }
	  }
}
