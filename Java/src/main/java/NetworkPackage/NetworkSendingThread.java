package NetworkPackage;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class NetworkSendingThread extends Thread {
	private int protocole;
	//0 -> "co" 1->"deco" 2->"pseudo x" 3-> réponse de connexion
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
			System.out.println("messagede déconnexion envoyé");
		}
		if (protocole == 2) {
			message = "pseudo " + pseudo;
		}
		if (protocole == 3) {
			/* réponse à un message de connexion, pour indiquer que nous le sommes aussi*/
			message = "repco";
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
