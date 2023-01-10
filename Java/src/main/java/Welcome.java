import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import DatabasePackage.*;
import ViewPackage.Ender;
import ViewPackage.identification;
import ViewPackage.interface_premiere_connexion;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Welcome {
	public Welcome(){
		JFrame frame = new JFrame("Bienvenue sur l'application de chat");
   	 frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
         	new Ender();
         }
     });
		frame.getContentPane().add(BorderLayout.NORTH, new JLabel("Bienvenue sur l'application de chat"));
		JButton reset = new JButton("Réinitialiser");
        reset.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  DatabaseManager.clearPwd();
        		  DatabaseManager.clearDBUsers();
        		  DatabaseManager.clearDBHistory();
        		  JFrame ok = new JFrame("erreur");
              		ok.getContentPane().add(BorderLayout.CENTER, new JLabel("Application réinitialisé. Veuillez vous connecter"));
              		ok.setSize(350, 100);
              		ok.setVisible(true);
        	  } 
        	} );
        JButton connexion = new JButton("Connexion");
        connexion.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  if(DatabaseManager.pwdExists()){
        	        	identification C_interface = new identification();
        	            C_interface.setVisible(true);
        	        }
        	        else{
        	            interface_premiere_connexion N_interface = new interface_premiere_connexion();
        	            N_interface.setVisible(true);
        	        }
        		  frame.dispose();
        	  } 
        	} );
		frame.getContentPane().add(BorderLayout.CENTER, connexion);
		frame.getContentPane().add(BorderLayout.SOUTH, reset);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}
}
