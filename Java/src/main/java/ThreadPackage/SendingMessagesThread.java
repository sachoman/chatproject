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
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out_sock.writeObject("envoi message \n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
