package ViewPackage;
import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import ThreadPackage.ThreadManager;
import ViewPackage.ViewManager;

public class Ender{
	public Ender() {
	System.out.println("fin du game");
		ThreadManager.killAllThreads();
		NetworkManager.notifyDeCo();
		ViewManager.AccueilThRef.interrupt();
		ViewManager.endAllViews();
		DatabaseManager.DecoAllUsers();
	}
}
