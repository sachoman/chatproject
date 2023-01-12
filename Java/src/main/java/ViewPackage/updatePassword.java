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

import DatabasePackage.DatabaseManager;

public class updatePassword {
	public updatePassword() {
		JFrame frame = new JFrame("Changement du mot de passe");
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
	     
	     JLabel title = new JLabel("Changement du mot de passe");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 0;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 Font fonttitle = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	   	 title.setFont(fonttitle);
	   	 gbc.insets = new Insets(5,5,30,5);
	 	frame.add(title,gbc);
	   	 	JTextArea message = new JTextArea("Veuillez renseigner votre ancien et mot de passe et le nouveau mot de passe désiré");
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
	   	 	
	   	 JLabel lmdp = new JLabel("Ancien mot de passe");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 2;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
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
	   	
	   	JLabel lmdp1 = new JLabel("Nouveau mot de passe");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 4;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	 	frame.add(lmdp1,gbc);
	 	
	 	
	 	JPasswordField pfield1 = new JPasswordField();
	 		gbc.gridx = 1;
	 		gbc.gridy = 5;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 	gbc.ipadx = 200;
	   	 	gbc.ipady =  5;
	   	 	gbc.fill = GridBagConstraints.VERTICAL;
	   	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 	pfield1.setPreferredSize(new Dimension(200,20));
	   	frame.add(pfield1,gbc);
	   	
	   	JLabel lmdp2 = new JLabel("Confirmez le nouveau mot de passe");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 6;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	 	frame.add(lmdp2,gbc);
	 	
	 	
	 	JPasswordField pfield2 = new JPasswordField();
	 		gbc.gridx = 1;
	 		gbc.gridy = 7;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 	gbc.ipadx = 200;
	   	 	gbc.ipady =  5;
	   	 	gbc.fill = GridBagConstraints.VERTICAL;
	   	 	gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 	pfield2.setPreferredSize(new Dimension(200,20));
	   	frame.add(pfield2,gbc);
	 	
		   	JButton valider = new JButton("Valider");
		        gbc.gridx = 2;
		   	 	gbc.gridy = 8;
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
	   	 	gbc.gridy = 8;
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
	      		  String entre = new String(pfield.getPassword());
	      		String entre1 = new String(pfield1.getPassword());
	      		String entre2= new String(pfield2.getPassword());
	      		if (DatabaseManager.testPwd(entre)) {
		      		if (entre1.equals(entre2)) {
			      		if ((entre1.equals(""))||(entre1 == null)) {
			      			wrong2.setText("Le nouveau mot de passe ne doit pas être vide");
				    	 	frame.setVisible(true);
			             }
			             else {
			                 DatabaseManager.updatePwd(entre,entre1);
			                 frame.dispose();
			             }
		      		}
		      		else {
		      			wrong2.setText("Veuillez saisir le même mot de passe dans les deux derniers champs");
			    	 	frame.setVisible(true);
		      		}
	      		}
	      		else {
	      			wrong2.setText("Votre ancien mot de passe ne correspond pas");
		    	 	frame.setVisible(true);
	      		}
	      	  } 
	      	} );
         
	}
}
