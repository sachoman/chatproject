package ThreadPackage;

import DatabasePackage.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ListeningChatThread extends Thread{
	private Socket th_socket;
	public void setSocket(Socket Socket) {
		th_socket = Socket;
	}
	public ListeningChatThread(Socket socket) {
		th_socket = socket;
	}
	public void run() {
		ObjectInputStream in;
		System.out.println("Accept chat thread launched");
		try {
			in = new ObjectInputStream(th_socket.getInputStream());
			while(true) {
				Object msg = in.readObject();
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	    		Date date = new Date(System.currentTimeMillis());
				DatabaseManager.storeMessage(th_socket.getInetAddress().toString(),InetAddress.getLocalHost().toString(), msg.toString(), formatter.format(date));
				System.out.println("Message chat reçu : " + msg);
			}
		} catch (IOException | ClassNotFoundException e1) {
			System.out.println("Connexion sur socket : "+th_socket+" terminée");
			ThreadManager.removeThreadInTab(currentThread().getId());
			//TODO notify the chat view !!!
			
		}
	}
}
