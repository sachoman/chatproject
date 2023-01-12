package ViewPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

public class Login {
	public Login() {
		JFrame frame = new JFrame("Connexion");
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
	   	 JLabel lco = new JLabel("Connexion");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 1;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	   	 lco.setFont(font);
	   	 gbc.insets = new Insets(5,5,50,5);
	 	frame.add(lco,gbc);
	 	
	   	 JLabel lmdp = new JLabel("Mot de passe");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 2;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 lmdp.setPreferredSize(new Dimension(300,20));
	 	frame.add(lmdp,gbc);
	 	
	 	JPasswordField pfield = new JPasswordField();
	 		gbc.gridx = 1;
	 		gbc.gridy = 3;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 	gbc.ipadx = 200;
	   	 	gbc.ipady =  5;
	   	 	gbc.fill = GridBagConstraints.VERTICAL;
	   	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 	pfield.setPreferredSize(new Dimension(200,20));
	   	frame.add(pfield,gbc);
	 	
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
		   	 	valider.setPreferredSize(new Dimension(100,23));
	   	 	frame.add(valider,gbc);
	   	 	
	   	 	
	   	 JLabel wrong2 = new JLabel("");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 4;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 	Font font2 = new Font(Font.DIALOG, Font.BOLD, 7);
	   	 wrong2.setForeground(Color.red);
	      wrong2.setFont(font2);
	 	frame.add(wrong2,gbc);
	 	
	   	 frame.setResizable(true);
	   	frame.setSize(600, 400);
		frame.setVisible(true);
	   	 		
	   	valider.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		String entre = new String(pfield.getPassword());
	      		if (DatabaseManager.testPwd(entre)) {
                	frame.dispose();
                	pseudoDefinition defpseudo = new pseudoDefinition();
                }
                else {
                	wrong2.setText("Mauvais mot de passe");
                }
	      	  } 
	      	} );
         
	}
}
