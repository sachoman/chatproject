package ThreadPackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DatabasePackage.DatabaseManager;

public class SendingChatThread extends Thread{
	private ObjectOutputStream out;
	private String message;
	private Socket th_sock;
	public void setMessage(String msg) {
		message = msg;
	}
	public void setSocket(Socket sock) {
		th_sock = sock;
	}
	public void setOut(ObjectOutputStream outstream) {
		out = outstream;
	}
	public void run() {
		System.out.println("Sending message thread launch");
		try {
			out.writeObject(message);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		    Date date = new Date(System.currentTimeMillis());
			DatabaseManager.storeMessage("/"+InetAddress.getLocalHost().getHostAddress().toString(),th_sock.getInetAddress().toString(), message, formatter.format(date));
			System.out.println("Message envoyé");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
