package NetworkPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class NetworkListeningThread {
	public NetworkListeningThread(int port) throws ClassNotFoundException, IOException {
		DatagramSocket dgramSocket = new DatagramSocket(port);
		byte[] buffer = new byte[256];
		while (true) {
			DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
			dgramSocket.receive(inPacket);
			InetAddress clientAddress = inPacket.getAddress();
			int clientPort = inPacket.getPort();
			String message = new String(inPacket.getData(), 0, inPacket.getLength());
			NetworkTraitementMessage th = new NetworkTraitementMessage(clientAddress, clientPort, message);
			th.run();
		}
	}
}
