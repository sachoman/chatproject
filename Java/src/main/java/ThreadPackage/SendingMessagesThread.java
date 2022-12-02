package ThreadPackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SendingMessagesThread extends Thread{
	public Socket th_sock;
	public SendingMessagesThread(Socket sock) {
		th_sock = sock;
	}
	public void run() {
		System.out.println("Sending messages thread launch");
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(th_sock.getOutputStream());
			while(true) {
					Thread.sleep(2000);
					out.writeObject("sacha \n");
					System.out.println("Message envoyé");
			}
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
