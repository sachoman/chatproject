package NetworkPackage;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class NetworkSendingThread extends Thread {
	private int protocole;
	private String ip;
	//0 -> "co" 1->"deco" 2->"pseudo x"
	private String pseudo; 
	public NetworkSendingThread(int protocole) {
		this.protocole = protocole;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public void run() {
		System.out.println("debut traitement");
		String message ="";
		if (protocole ==0) {
			message = "co";
		}
		if (protocole == 1 ) {
			message = "deco";
		}
		if (protocole == 2) {
			message = "pseudo " + pseudo;
		}
		DatagramSocket dgramSocket;
		try {
			dgramSocket = new DatagramSocket();
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),InetAddress.getByName(NetworkManager.getBroadcast()), NetworkManager.getUdpAppPort());
			dgramSocket.send(outPacket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
