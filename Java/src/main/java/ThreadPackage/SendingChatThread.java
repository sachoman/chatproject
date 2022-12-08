package ThreadPackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DatabasePackage.DatabaseManager;

public class SendingChatThread extends Thread{
	public Socket th_sock;
	public String message;
	public void updateMessage(String msg) {
		message = msg;
	}
	public SendingChatThread(Socket sock) {
		th_sock = sock;
	}
	public void run() {
		System.out.println("Sending messages thread launch");
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(th_sock.getOutputStream());
			while(true) {
					wait();
					out.writeObject(message);
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		    		Date date = new Date(System.currentTimeMillis());
					DatabaseManager.storeMessage(InetAddress.getLocalHost().toString(),th_sock.getInetAddress().toString(), message, formatter.format(date));
					System.out.println("Message envoyé");
			}
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
