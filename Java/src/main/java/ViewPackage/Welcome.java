package ViewPackage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import DatabasePackage.*;
import ViewPackage.Ender;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Welcome {
	public Welcome(){
		JFrame frame = new JFrame("Bienvenue sur l'application de chat");
   	 frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
         	new Ender();
         }
     });
   	 
		//design
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(5,5,5,5);
     frame.setLayout(new GridBagLayout());
   	 	JLabel bienvenue = new JLabel("Bienvenue");
   	 	gbc.gridx = 2;
   	 	gbc.gridy = 1;
   	 	gbc.gridwidth = 1;
   	 	gbc.gridheight = 1;
	   	 gbc.insets = new Insets(5,5,30,5);
	   	Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	  	 bienvenue.setFont(font);
   	 frame.add(bienvenue,gbc);
   	 
   	JTextArea message = new JTextArea("Connectez-vous pour accèder à l'application de chat. Il est possible de réinitialiser l'appliation, cela supprimera toutes les données.");
	 	message.setLineWrap(true);
	    message.setWrapStyleWord(true);
	    message.setEditable(false);
	    message.setBackground(null);
	    Font font2 = new Font(Font.DIALOG, Font.PLAIN, 12);
	    message.setFont(font2);
	 	gbc.gridx = 1;
	 	gbc.gridy = 2;
	 	gbc.gridwidth = 3;
	 	gbc.gridheight = 1;
	 	gbc.insets = new Insets(5,5,30,5);
	 	gbc.fill = GridBagConstraints.VERTICAL;
	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	frame.add(message,gbc);
   	 
        JButton connexion = new JButton("Connexion");
        gbc.gridx = 1;
   	 	gbc.gridy = 3;
   	 	gbc.gridwidth = 1;
   	 	gbc.gridheight = 1;
   	 gbc.insets = new Insets(5,5,5,5);
   	 	frame.add(connexion,gbc);
   	 	
		JButton reset = new JButton("Réinitialiser");
		gbc.gridx = 3;
   	 	gbc.gridy = 3;
   	 	gbc.gridwidth = 1;
   	 	gbc.gridheight = 1;
   	 gbc.insets = new Insets(5,5,5,5);
   	 	frame.add(reset,gbc);

        
		frame.setSize(600, 400);
		frame.setVisible(true);
        //button actions
        reset.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      		  JFrame ok = new JFrame("Confirmation ?");
      		 ok.setLayout(new GridBagLayout());
			      	JTextArea message2 = new JTextArea("Êtes-vous sûr de vouloir réiniatiliser l'application ?");
			    	 	message2.setLineWrap(true);
			    	    message2.setWrapStyleWord(true);
			    	    message2.setEditable(false);
			    	    message2.setBackground(null);
			    	    Font font2 = new Font(Font.DIALOG, Font.PLAIN, 12);
			    	    message2.setFont(font2);
			    	 	gbc.gridx = 1;
			    	 	gbc.gridy = 2;
			    	 	gbc.gridwidth = 3;
			    	 	gbc.gridheight = 1;
			    	 	gbc.insets = new Insets(5,5,5,5);
			    	 	gbc.fill = GridBagConstraints.VERTICAL;
			    	 	gbc.fill = GridBagConstraints.HORIZONTAL;
			    	 	message2.setPreferredSize(new Dimension(300,15));
			    	ok.add(message2,gbc);
			       	 
			            JButton oui = new JButton("Oui");
			            gbc.gridx = 1;
			       	 	gbc.gridy = 3;
			       	 	gbc.gridwidth = 1;
			       	 	gbc.gridheight = 1;
			       	 gbc.insets = new Insets(5,5,5,5);
			       	oui.setPreferredSize(new Dimension(100,20));
			       	 	ok.add(oui,gbc);
			       	 	
			    		JButton annuler = new JButton("Annuler");
			    		gbc.gridx = 3;
			       	 	gbc.gridy = 3;
			       	 	gbc.gridwidth = 1;
			       	 	gbc.gridheight = 1;
			       	 gbc.insets = new Insets(5,5,5,5);
			       	annuler.setPreferredSize(new Dimension(100,20));
			       	 	ok.add(annuler,gbc);
            		ok.setSize(350, 100);
            		ok.setVisible(true);
            		
            		annuler.addActionListener(new ActionListener() { 
                    	  public void actionPerformed(ActionEvent e) { 
                    		  ok.dispose();
                    	  } 
                    	} );
            		oui.addActionListener(new ActionListener() { 
                    	  public void actionPerformed(ActionEvent e) { 
                    		  DatabaseManager.clearDBUsers();
                    		  DatabaseManager.clearPwd();
                    		  DatabaseManager.clearDBHistory();
                    		  ok.dispose();
                    	  } 
                    	} );
      	  } 
      	} );
        connexion.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  if(DatabaseManager.pwdExists()){
        			  Login login = new Login();
        	        }
        	        else{
        	        	new firstLogin();
        	        }
        		  frame.dispose();
        	  } 
        	} );
	}
}
