package ThreadPackage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

public class ThreadManager {
	private static Hashtable<Long, InetAddress> TableListeningIdThIpDistante = new Hashtable<Long, InetAddress>();
	private static Hashtable<Long, InetAddress> TableSendingIdThIpDistante = new Hashtable<Long, InetAddress>();
	
	public void addListeningThreadInTab(Long id_th, InetAddress ip) {
		TableListeningIdThIpDistante.put(id_th,ip);
	}
	public static void removeListeningThreadInTab(Long id_th) {
		TableListeningIdThIpDistante.remove(id_th);
	}
	public void addSendingThreadInTab(Long id_th, InetAddress ip) {
		TableSendingIdThIpDistante.put(id_th,ip);
	}
	public static void removeSendingThreadInTab(Long id_th) {
		TableSendingIdThIpDistante.remove(id_th);
	}
	/* crée les threads de réception et d'envoi de messages quand qqn commence une discussion avec nous */
	public static void createThreadsForChat(Socket socket) {
        ListeningChatThread lth = new ListeningChatThread(socket);
        lth.start();
        TableListeningIdThIpDistante.put(lth.getId(),socket.getInetAddress());
		SendingChatThread sth = new SendingChatThread(socket);
		sth.start();
		TableSendingIdThIpDistante.put(sth.getId(),socket.getInetAddress());
	}
	public static Thread getSendingThreadRef(InetAddress ip) throws Exception {
		Long id = (long) 0;
		 for(Entry<Long, InetAddress> entry: TableListeningIdThIpDistante.entrySet()){
	            if(ip.equals(entry.getValue())){
	                id =entry.getKey();
	                break;
	            }
	        }
		 if (id==0) {
			Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
			for(Thread thread : setOfThread){
			    if(thread.getId()==id){
			        return thread;
			    }
			}
			throw new Exception("sending Thread doesn't exists !");
		 }
		 else {
			throw new Exception("sending Thread doesn't exists !");
		 }
	}
	public static void endChat(InetAddress ip) {
		 for(Entry<Long, InetAddress> entry: TableListeningIdThIpDistante.entrySet()){
	            if(ip.equals(entry.getValue())){
	                ThreadManager.killListeningThread(entry.getKey()); 
	            }
	        }
		 for(Entry<Long, InetAddress> entry: TableSendingIdThIpDistante.entrySet()){
	            if(ip.equals(entry.getValue())){
	                ThreadManager.killSendingThread(entry.getKey()); 
	            }
	        }

	}
	/**/
	public static void killListeningThread(Long id_th) {
		ThreadManager.removeListeningThreadInTab(id_th);
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
		    if(thread.getId()==id_th){
		        thread.interrupt();
		    }
		}
		/*Thread.interrupt()*/
	}
	public static void killSendingThread(Long id_th) {
		ThreadManager.removeSendingThreadInTab(id_th);
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		for(Thread thread : setOfThread){
		    if(thread.getId()==id_th){
		        thread.interrupt();
		    }
		}
		/*Thread.interrupt()*/
	}
	public void killAllThreads() {
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();

		//Iterate over set to find yours
		for(Thread thread : setOfThread){
			 if(TableSendingIdThIpDistante.contains(thread.getId())){
			    	ThreadManager.removeSendingThreadInTab(thread.getId());
			        thread.interrupt();
			    }
			 if(TableListeningIdThIpDistante.contains(thread.getId())){
			    	ThreadManager.removeListeningThreadInTab(thread.getId());
			        thread.interrupt();
			    }
		}
	}
	public static void sendMessage(String msg, InetAddress Ip) throws Exception {
		Thread th = getSendingThreadRef(Ip);
		((SendingChatThread) th).updateMessage(msg);
		th.notify();
	}
}
