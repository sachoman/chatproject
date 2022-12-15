package ViewPackage;

import ThreadPackage.*;
import DatabasePackage.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import java.sql.Connection;





public class TestJframe {
    public static boolean first = false ; 
 
    
    
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try {
					DatabaseManager.initTables();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                            if(DatabaseManager.pwdExists()){
                            	identification C_interface = new identification();
                                C_interface.setVisible(true);
                            }
                            else{
                                interface_premiere_connexion N_interface = new interface_premiere_connexion();
                                N_interface.setVisible(true);
                            }
                }       
		});
	}
}