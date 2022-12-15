package ViewPackage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import UserPackage.User;
import NetworkPackage.*;
import ThreadPackage.*;

public class ConversationThreadView extends Thread{
		public static String ipDistante;
		public static InetAddress inetIp;
		public JTable tableau;
		DefaultTableModel model;
		 public ConversationThreadView(String ip) {
			 ipDistante = ip;
			 try {
				inetIp = NetworkManager.stringToInet(ip);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }   
		 public void addMessage(String pseudo, String date, String mess) {
			 model.addRow(new Object[]{pseudo,date,mess});
		 }
		 public void run(){
			 try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Creating the Frame
		        JFrame frame = new JFrame("Conversation avec "+ DatabaseManager.getPseudo(ipDistante).toString());
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setSize(1100, 800);

		        //Creating the MenuBar and adding components
		        JMenuBar mb = new JMenuBar();
		        JMenu m1 = new JMenu("Déconnexion de la conversation");
		        mb.add(m1);
		        /*
		        JMenuItem m11 = new JMenuItem("Close");
		        JMenuItem m22 = new JMenuItem("Open New");
		        m1.add(m11);
		        m1.add(m22);
		        */

		        //Creating the panel at bottom and adding components
		        JPanel panel = new JPanel(); // the panel is not visible in output
		        JLabel label = new JLabel("Entrez du texte");
		        final JTextArea tf = new JTextArea(3, 50); // accepts upto 10 characters
		        JButton send = new JButton("Envoyer");
		        send.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		  String message = new String(tf.getText());
		        		  NetworkManager.sendMessage(message, inetIp);
						tf.setText(""); 
		        	  } 
		        	} );
		        JButton reset = new JButton("Effacer");
		        reset.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		  tf.setText("");
		        	  } 
		        	} );
		        panel.add(label); // Components Added using Flow Layout
		        panel.add(tf);
		        panel.add(send);
		        panel.add(reset);

		        // Text Area at the Center
		        //String[][] history = DatabaseManager.getMessages(ipDistante);
		        String pseudo = DatabaseManager.getPseudo(ipDistante);
				model = new DefaultTableModel();       	
				tableau = new JTable(model); 

				// Create a couple of columns 
				model.addColumn("Date"); 
				model.addColumn("De"); 
				model.addColumn("Message");
				System.out.println("avant try");
		        try {
		        	System.out.println("init try");
		        	System.out.println(ipDistante);
					 Object[][] data = DatabaseManager.getMessages(ipDistante);
					 System.out.println("historique chargé");
					 System.out.println("longueur" + data.length);
					for ( int i=0; i<data.length; i++ ) {
					    if (data[i][0].equals(ipDistante)) {
					    	data[i][0] = pseudo;
					    }
					    else {
					    	data[i][0] = User.defaultViewPseudo;
					    }
						model.addRow(new Object[]{data[i][1], data[i][0], data[i][2]});
						System.out.println("addRow : "+i);
					}
					
				// Append a row 

				tableau.setRowHeight(30);
				TableColumnModel columnModel = tableau.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(100);
				columnModel.getColumn(1).setPreferredWidth(200);
				columnModel.getColumn(2).setPreferredWidth(800);
				 frame.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			            	System.out.println("window closed");
			            	ViewManager.TabIpChatThreadView.remove(inetIp);
			                ThreadManager.endChat(inetIp);
			                System.exit(0);
			            }
			        });
					frame.getContentPane().add(BorderLayout.SOUTH, panel);
			        frame.getContentPane().add(BorderLayout.NORTH, mb);
			        frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(tableau));
			        frame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    }
}
