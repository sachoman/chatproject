package ViewPackage;

import ThreadPackage.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;




public class TestJframe {
    public static boolean first = false ; 
 
    
    
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
                            if(first == false){
                                Connection C_interface = new Connection();
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