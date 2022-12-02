package ThreadPackage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

public class ThreadManager {
	private static Hashtable<Long, InetAddress> TableIdThIpDistante = new Hashtable<Long, InetAddress>();
	public void addThreadInTab(Long id_th, InetAddress ip) {
		TableIdThIpDistante.put(id_th,ip);
	}
	public static void removeThreadInTab(Long id_th) {
		TableIdThIpDistante.remove(id_th);
	}
	/* crée les threads de réception et d'envoi de messages quand qqn commence une discussion avec nous */
	public static void createThreadsForChat(Socket socket) {
        ListeningChatThread lth = new ListeningChatThread(socket);
        lth.start();
        TableIdThIpDistante.put(lth.getId(),socket.getInetAddress());
		SendingChatThread sth = new SendingChatThread(socket);
		sth.start();
		TableIdThIpDistante.put(sth.getId(),socket.getInetAddress());
	}
	public static void endChat(InetAddress ip) {
		 for(Entry<Long, InetAddress> entry: TableIdThIpDistante.entrySet()){
	            if(ip.equals(entry.getValue())){
	                ThreadManager.killThread(entry.getKey()); 
	                ThreadManager.removeThreadInTab(entry.getKey());
	            }
	        }

	}
	/**/
	public static void killThread(Long id_th) {
		removeThreadInTab(id_th);
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
		    if(thread.getId()==id_th){
		        removeThreadInTab(thread.getId());
		        thread.interrupt();
		    }
		}
		/*Thread.interrupt()*/
	}
	public void killAllThreads() {
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();

		//Iterate over set to find yours
		for(Thread thread : setOfThread){
		    if(TableIdThIpDistante.contains(thread.getId())){
		        removeThreadInTab(thread.getId());
		        thread.interrupt();
		    }
		}
	}
}