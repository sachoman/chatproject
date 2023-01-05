package ViewPackage;
import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import ThreadPackage.ThreadManager;
import ViewPackage.ViewManager;

public class Ender extends Thread{
	public Ender() {
		
	}
	public void run() {
		ThreadManager.killAllThreads();
		NetworkManager.notifyDeCo();
		ViewManager.AccueilThRef.interrupt();
		ViewManager.endAllViews();
		DatabaseManager.DecoAllUsers();
	}
}
