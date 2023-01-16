package ThreadPackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import NetworkPackage.NetworkManager;
import ViewPackage.ViewManager;

public class ThreadManager {
	public static Hashtable<Long, InetAddress> TableIdThIpDistante = new Hashtable<Long, InetAddress>();
	
	public void addThreadInTab(Long id_th, InetAddress ip) {
		TableIdThIpDistante.put(id_th,ip);
	}
	public static void removeThreadInTab(Long id_th) {
		try {
		System.out.println("id thread : " + id_th);
		InetAddress ip = TableIdThIpDistante.get(id_th);
		ThreadManager.endChat(ip);
		TableIdThIpDistante.remove(id_th);
		System.out.println("ip distante : " + ip);
		Socket sock = NetworkManager.TabIpSock.get(ip);
		NetworkManager.TabIpSock.remove(ip);
		NetworkManager.removeOutFromSock(sock);
		try {
			sock.close();
		} catch (IOException e) {
		};
		} catch(Exception e) {
			
		}
	}
	/* crée les threads de réception des messages quand qqn commence une discussion avec nous */
	public static void createThreadForChat(Socket socket, Boolean bool) throws IOException {
        ListeningChatThread lth = new ListeningChatThread(socket);
        lth.start();
        ObjectOutputStream out;
		out = new ObjectOutputStream(socket.getOutputStream());
		NetworkManager.TabSockOut.put(socket, out);
        TableIdThIpDistante.put(lth.getId(),socket.getInetAddress());
        //lance interface graphique 
        ViewManager.newChatThreadView(socket.getInetAddress(), bool);
	}
	public static void endChat(InetAddress ip) {
		 for(Entry<Long, InetAddress> entry: TableIdThIpDistante.entrySet()){
	            if(ip.equals(entry.getValue())){
	            	System.out.println("debut end chat  2");
	            	ViewManager.endChat(ip);
	            	Long id_th = entry.getKey();
	                TableIdThIpDistante.remove(id_th);
	                Socket sock = NetworkManager.TabIpSock.get(ip);
	        		NetworkManager.TabIpSock.remove(ip);
	        		NetworkManager.removeOutFromSock(sock);
	        		try {
	        			sock.close();
	        		} catch (IOException e) {
	        		};
	            }
	        }   
		 
	}
	/*
	public static void killThread(Long id_th) {
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
		    if(thread.getId()==id_th){
		        thread.interrupt();
		    }
		}
	}*/
	public static void killAllThreads() {
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
			 if(TableIdThIpDistante.contains(thread.getId())){
			    	try{
			    		ThreadManager.removeThreadInTab(thread.getId());
			    	}
			    	catch (Exception e) {
			    		
			    	}
			    }
		}
	}
}
