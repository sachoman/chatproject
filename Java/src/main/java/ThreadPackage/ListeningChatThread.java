package ThreadPackage;

import DatabasePackage.*;
import ViewPackage.ViewManager;

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
				DatabaseManager.storeMessage(th_socket.getInetAddress().toString(),"/"+InetAddress.getLocalHost().getHostAddress().toString(),  formatter.format(date), msg.toString());
				System.out.println("Message chat reçu : " + msg);
				String pseudo = DatabaseManager.getPseudo(th_socket.getInetAddress().toString());
				ViewManager.addMessageView(th_socket.getInetAddress(), pseudo,formatter.format(date), msg.toString());
				//si la frame n'est pas visible, on met en gras l'utilisateur qui a envoyé un messgae sur la page d'accueil
				if (!(th_socket.getInetAddress() == ViewManager.AccueilThRef.inetIp)) {
					ViewManager.TabIpChatThreadView.get(th_socket.getInetAddress()).newmessage = true;
					ViewManager.TabIpChatThreadView.get(th_socket.getInetAddress()).cptmessages +=1;
					//update accueil
					ViewManager.AccueilThRef.updateUsersView();
				}
			}
		} catch (IOException | ClassNotFoundException e1) {
			System.out.println("Connexion sur socket : "+th_socket+" terminée");
			//notify the chat view
			ViewManager.endChat(th_socket.getInetAddress());
			//notify thread manager
			try {
				ThreadManager.removeThreadInTab(currentThread().getId());
			}
			catch (Exception e) {
				
			}
		}
	}
}
