package ThreadPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectChatThread extends Thread{
	public InetAddress ip_distante;
	public int port_distant;
	public ConnectChatThread(InetAddress ip, int port) {
		ip_distante = ip;
		port_distant = port;
	}
	public void run() {
		Socket socket;
		try {
			socket = new Socket(ip_distante,port_distant);
			ObjectInputStream in;
			ObjectOutputStream out;
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			//crée thread d'envoi
			SendingMessagesThread sth = new SendingMessagesThread(out);
			sth.start();
			
			while(true) {
				Object msg = in.readObject();
				System.out.println(msg);
				
			}
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
