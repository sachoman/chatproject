package ThreadPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class AcceptChatThread extends Thread{
	private Socket th_socket;
	public void setSocket(Socket Socket) {
		th_socket = Socket;
	}
	public AcceptChatThread(Socket socket) {
		th_socket = socket;
	}
	public void run() {
		ObjectInputStream in;
		ObjectOutputStream out;
		System.out.println("Accept chat thread launched");
		try {
			in = new ObjectInputStream(th_socket.getInputStream());
			out = new ObjectOutputStream(th_socket.getOutputStream());
			SendingMessagesThread sth = new SendingMessagesThread(out);
			sth.start();
			while(true) {
				Object msg = in.readObject();
				System.out.println("Message chat reçu : " + msg);
				
			}
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
