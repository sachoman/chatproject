package ThreadPackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

public class SendingMessagesThread extends Thread{
	public ObjectOutputStream out_sock;
	public SendingMessagesThread(ObjectOutputStream out) {
		out_sock = out;
	}
	public void run() {
		System.out.println("Sending messages thread launch");
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out_sock.writeObject("xyz \n");
				System.out.println("Message envoyé");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
