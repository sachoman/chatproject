package ViewPackage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.AttributedString;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import ThreadPackage.ThreadManager;
import UserPackage.User;

public class Accueil extends Thread{
	public InetAddress inetIp = null;
	public JTable tableau;
	Container pane;
	DefaultTableModel model = new DefaultTableModel();
	JFrame frame;
	public Accueil() {
	}  
	/*
	 * InetAddress ip = NetworkManager.stringToInet(DatabaseManager.getIp(value.toString()));
				 if (ViewManager.TabIpChatThreadView.get(ip).newmessage) { 
					 System.out.println("ca marche");
			            c.setFont(c.getFont().deriveFont(Font.BOLD));
			        }
	 */
	public Container AccueilPane() {
		 pane = new JPanel();
		 pane.setLayout(new GridBagLayout());
		 
		 GridBagConstraints gbc = new GridBagConstraints();
		 
		 JLabel title = new JLabel("Accueil");
	   	 	gbc.gridx = 1;
	   	 	gbc.gridy = 1;
	   	 	gbc.gridwidth = 3;
	   	 	gbc.gridheight = 1;
		   	 Font fonttitle = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		   	 title.setFont(fonttitle);
		   	 gbc.insets = new Insets(5,5,30,5);
	 	 pane.add(title,gbc);
	 	 
	   	 JTextArea message = new JTextArea("Cliquez sur un utilisateur connecté de la liste à gauche pour commencer ou continuer une discussion avec lui");
		   	 message.setLineWrap(true);
		     message.setWrapStyleWord(true);
		     message.setEditable(false);
		     message.setBackground(null);
		     Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
		     message.setFont(font);
		   	 gbc.gridx = 1;
		   	 gbc.gridy = 2;
		   	 gbc.gridwidth = 3;
		   	 gbc.gridheight = 1;
		   	 gbc.insets = new Insets(5,5,30,5);
		   	 gbc.fill = GridBagConstraints.VERTICAL;
		   	 gbc.fill = GridBagConstraints.HORIZONTAL;
	   	 pane.add(message,gbc);
		 
		 
		 JButton updatepwd = new JButton("Modifier le mot de passe");
	        gbc.gridx = 1;
	   	 	gbc.gridy = 3;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 pane.add(updatepwd,gbc);
	   	 
	   	JButton updatepseudo = new JButton("Changer son pseudo");
	        gbc.gridx = 2;
	   	 	gbc.gridy = 3;
	   	 	gbc.gridwidth = 1;
	   	 	gbc.gridheight = 1;
	   	 	gbc.insets = new Insets(5,5,5,5);
	   	 pane.add(updatepseudo,gbc);
	   	updatepwd.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		updatePassword upd = new updatePassword();
	      	  } 
	      	} );
	   	updatepseudo.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		updatePseudo upsd = new updatePseudo();
	      	  } 
	      	} );
	   	 return pane;
	}
	public void fermeConv() {
		System.out.println("on ferme la conv \n");
		  frame.remove(pane);
		  pane = AccueilPane();
		  frame.add(BorderLayout.CENTER, pane);
		updateUsersView();
		frame.setVisible(true);
	}
	public void updateUsersView() {
        try {
			 String[][] data = DatabaseManager.getConnectedUsers();
			 int n = model.getRowCount();
	   		  for (int i=n-1;i>=0; i--) {
	   			  model.removeRow(i);
	   		  }
			 if (data != null) {
				for ( int i=0; i<data.length; i++ ) {
					InetAddress ip = NetworkManager.stringToInet(data[i][0]);
					try {
						ConversationThreadView lth = ViewManager.TabIpChatThreadView.get(ip);
						 if (lth.newmessage) { 
							 model.addRow(new Object[]{data[i][1],lth.cptmessages});
						 }
						 else {
							model.addRow(new Object[]{data[i][1],""});
						 }
					}
					catch (Exception e) {
						model.addRow(new Object[]{data[i][1],""});
						}
					 
					//check si un nouveau message, alors c'est en gras
					
				}
			 }
			 else {
				 model.addRow(new Object[]{"Aucun utilisateur connecté"});
			 }

        }
        catch (Exception e) {
        	
        }
	}
	public void run(){
		NetworkManager.notifyCo();
		 try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Creating the Frame
	        frame = new JFrame("Accueil");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(1100, 800);
	        
	        // Text Area at the Center
	        //String[][] history = DatabaseManager.getMessages(ipDistante);
			model = new DefaultTableModel(); 
			model.addColumn("utilisateurs");
			model.addColumn("");
			tableau = new JTable(model);
			tableau.getColumnModel().getColumn(1).setMaxWidth(50);
			tableau.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	int selectedRow = tableau.getSelectedRow();
	            	String value = (String) model.getValueAt(selectedRow, 0);
	            	try {
	            		InetAddress adresseDistante = NetworkManager.stringToInet(DatabaseManager.getIp(value));
	            		if (ViewManager.TabIpChatThreadView.containsKey(adresseDistante)) {
		            			ViewManager.TabIpChatThreadView.get(adresseDistante).newmessage = false;
	            				ViewManager.TabIpChatThreadView.get(adresseDistante).cptmessages = 0;
		            			frame.remove(pane);
		            			pane = ViewManager.TabIpChatThreadView.get(adresseDistante).frame.getContentPane();
		            			frame.add(BorderLayout.CENTER, pane);
		            			ViewManager.AccueilThRef.updateUsersView();
		            			frame.setVisible(true);
		            			frame.repaint();
		            			ViewManager.AccueilThRef.inetIp = adresseDistante;
	            		}
	            		else {
	            			NetworkManager.ChatWithUser(adresseDistante);
	            		}
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
			// Create a couple of columns 

			// Append a row 
			tableau.setDefaultEditor(Object.class, null);
			tableau.setRowHeight(30);
			updateUsersView();
			 frame.addWindowListener(new WindowAdapter() {
		            public void windowClosing(WindowEvent e) {
		            	new Ender();
		            }
		        });
			 /*
				frame.getContentPane().add(BorderLayout.SOUTH, panel);
				*/
			pane = AccueilPane();
			JScrollPane scrollpane = new JScrollPane(tableau);
			scrollpane.setPreferredSize(new Dimension(250,500));
		        frame.add(BorderLayout.CENTER, pane);
		        frame.add(BorderLayout.WEST, scrollpane);
		        frame.setVisible(true);
		        

	}
}
