package ViewPackage;
import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import ThreadPackage.ThreadManager;
import ViewPackage.ViewManager;

public class Ender{
	public Ender() {
	try {
		ThreadManager.killAllThreads();
	}
	catch (Exception e) {
		
	}
	try {
		NetworkManager.notifyDeCo();
	}
	catch (Exception e) {
		
	}
	try {
		ViewManager.AccueilThRef.interrupt();
	}
		catch (Exception e) {
			
		}
	try {
		ViewManager.endAllViews();
	}
		catch (Exception e) {
			
		}
	try {
		DatabaseManager.DecoAllUsers();
	}
		catch (Exception e) {
			
		}
		System.exit(0);
	}
}
