package NetworkPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkListeningThread extends Thread {
	private int port;
	public NetworkListeningThread(int port) {
		this.port = port;
	}
		
	public void run(){
		System.out.println("Network listening thread launched");
		DatagramSocket dgramSocket;
		try {
			dgramSocket = new DatagramSocket(port);
			byte[] buffer = new byte[256];
			while (true) {
				try {
					DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
					dgramSocket.receive(inPacket);
					InetAddress clientAddress = inPacket.getAddress();
					Enumeration e = NetworkInterface.getNetworkInterfaces();
					Boolean booladresse = true;
					while(e.hasMoreElements())
					{
					    NetworkInterface n = (NetworkInterface) e.nextElement();
					    Enumeration ee = n.getInetAddresses();
					    while (ee.hasMoreElements())
					    {
					        InetAddress i = (InetAddress) ee.nextElement();
					        if (clientAddress.toString().equals("/"+i.getHostAddress())) {
					        	booladresse = false;
					        }
					    }
					}
					if (booladresse) {
						//System.out.println("UDP message from : " + clientAddress.toString());
						int clientPort = inPacket.getPort();
						String message = new String(inPacket.getData(), 0, inPacket.getLength());
						NetworkTraitementMessage th = new NetworkTraitementMessage(clientAddress, clientPort, message);
						th.run();
					}
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
