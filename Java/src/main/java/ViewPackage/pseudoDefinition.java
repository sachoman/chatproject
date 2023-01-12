package ViewPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import UserPackage.User;

public class pseudoDefinition {
	public pseudoDefinition() {
		JFrame frame = new JFrame("Définissez votre pseudo");
	   	 frame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) {
	         	new Welcome();
	         }
	     });
			//design
	     GridBagConstraints gbc = new GridBagConstraints();
	     gbc.insets = new Insets(5,5,5,5);
	     frame.setLayout(new GridBagLayout());
	     frame.setMaximumSize(new Dimension(500,300));
	     JLabel title = new JLabel("Pseudo");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 0;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 Font fonttitle = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	   	 title.setFont(fonttitle);
	   	 gbc.insets = new Insets(5,5,30,5);
	 	frame.add(title,gbc);
	   	 	JTextArea message = new JTextArea("Veuillez définir votre pseudo pour cette session de chat. Le pseudo est le nom qui apparaîtra auprès des autres utilisateurs");
		   	 	message.setLineWrap(true);
		        message.setWrapStyleWord(true);
		        message.setEditable(false);
		        message.setBackground(null);
		        Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
		        message.setFont(font);
		   	 	gbc.gridx = 1;
		   	 	gbc.gridy = 1;
		   	 	gbc.gridwidth = 3;
		   	 	gbc.gridheight = 1;
		   	 	gbc.insets = new Insets(5,5,30,5);
		   	 	gbc.fill = GridBagConstraints.VERTICAL;
		   	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 	frame.add(message,gbc);
	   	 	
	   	 JLabel lmdp = new JLabel("Votre pseudo");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 2;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 lmdp.setPreferredSize(new Dimension(300,20));
	 	frame.add(lmdp,gbc);
	 	
	 	JTextField tfield = new JTextField();
	 		gbc.gridx = 1;
	 		gbc.gridy = 3;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 	gbc.ipadx = 200;
	   	 	gbc.ipady =  5;
	   	 	gbc.fill = GridBagConstraints.VERTICAL;
	   	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 	tfield.setPreferredSize(new Dimension(200,20));
	   	frame.add(tfield,gbc);
	 	
		   	JButton valider = new JButton("Valider");
		        gbc.gridx = 3;
		   	 	gbc.gridy = 4;
		   	 	gbc.gridwidth = 1;
		   	 	gbc.gridheight = 1;
		   	 	gbc.ipadx = 0;
		   	 	gbc.ipady =  0;
		   	 	gbc.insets = new Insets(5,5,5,5);
		   	 	gbc.fill = GridBagConstraints.VERTICAL;
		   	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 	frame.add(valider,gbc);
	   	 	
	   	 	
	   	 JLabel wrong2 = new JLabel("");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 4;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 	Font font2 = new Font(Font.DIALOG, Font.BOLD, 9);
	   	 wrong2.setForeground(Color.red);
	      wrong2.setFont(font2);
	 	frame.add(wrong2,gbc);
	 	
	   	 frame.setResizable(true);
	   	frame.setSize(600, 400);
		frame.setVisible(true);
	   	 		
	   	valider.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		String entre = new String(tfield.getText());
	      		if ((entre.equals(""))||(entre == null)) {
		      		wrong2.setText("Le pseudo ne peut pas être vide");
	      		}
	      		else {
	      			if (DatabaseManager.checkAvailability(entre)) {
	      				User.setPseudo(entre);
	                	frame.dispose(); 
	                	NetworkManager.sendPseudo(entre);
	                	Accueil thaccueil = new Accueil();
	                	ViewManager.AccueilThRef = thaccueil;
	                	thaccueil.start();
	      			}
	      			else {
		      			wrong2.setText("Ce pseudo est déjà utilisé par un autre collaborateur");
			    	 	frame.setVisible(true);
	      			}
	      		}
	      	  } 
	      	} );
         
	}
}
