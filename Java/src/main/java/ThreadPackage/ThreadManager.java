package ThreadPackage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import NetworkPackage.NetworkManager;

public class ThreadManager {
	public static Hashtable<Long, InetAddress> TableIdThIpDistante = new Hashtable<Long, InetAddress>();
	
	public void addThreadInTab(Long id_th, InetAddress ip) {
		TableIdThIpDistante.put(id_th,ip);
	}
	public static void removeThreadInTab(Long id_th) {
		TableIdThIpDistante.remove(id_th);
		InetAddress ip = TableIdThIpDistante.get(id_th);
		Socket sock = NetworkManager.TabIpSock.get(id_th);
		NetworkManager.removeOutFromSock(sock);
		try {
			sock.close();
		} catch (IOException e) {
		}
		killThread(id_th);
	}
	/* crée les threads de réception des messages quand qqn commence une discussion avec nous */
	public static void createThreadForChat(Socket socket) {
        ListeningChatThread lth = new ListeningChatThread(socket);
        lth.start();
        TableIdThIpDistante.put(lth.getId(),socket.getInetAddress());
	}
	public static void endChat(InetAddress ip) {
		 for(Entry<Long, InetAddress> entry: TableIdThIpDistante.entrySet()){
	            if(ip.equals(entry.getValue())){
	                ThreadManager.killThread(entry.getKey()); 
	            }
	        }

	}
	/**/
	public static void killThread(Long id_th) {
		ThreadManager.removeThreadInTab(id_th);
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
		    if(thread.getId()==id_th){
		        thread.interrupt();
		    }
		}
	}
	public void killAllThreads() {
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
			 if(TableIdThIpDistante.contains(thread.getId())){
			    	ThreadManager.removeThreadInTab(thread.getId());
			        thread.interrupt();
			    }
		}
	}
}
