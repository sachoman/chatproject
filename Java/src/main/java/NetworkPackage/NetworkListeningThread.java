package NetworkPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class NetworkListeningThread extends Thread {
	private int port;
	public NetworkListeningThread(int port) {
		this.port = port;
	}
		
	public void run(){
		System.out.println("init listening thread");
		DatagramSocket dgramSocket;
		try {
			dgramSocket = new DatagramSocket(port);
			byte[] buffer = new byte[256];
			System.out.println("listening dgram socket init");
			while (true) {
				try {
					DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
					dgramSocket.receive(inPacket);
					System.out.println("message recu");
					InetAddress clientAddress = inPacket.getAddress();
					int clientPort = inPacket.getPort();
					String message = new String(inPacket.getData(), 0, inPacket.getLength());
					NetworkTraitementMessage th = new NetworkTraitementMessage(clientAddress, clientPort, message);
					th.run();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
