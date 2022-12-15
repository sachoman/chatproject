package ViewPackage;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import UserPackage.User;

public class ConversationThreadView extends Thread{
		public static String ipDistante;
		public JTable tableau;
		DefaultTableModel model;
		 public ConversationThreadView(String ip) {
			 ipDistante= ip;
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
			 ipDistante = "/10.1.1.54";
			//Creating the Frame
		        JFrame frame = new JFrame("Chat Frame");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setSize(1100, 800);

		        //Creating the MenuBar and adding components
		        JMenuBar mb = new JMenuBar();
		        JMenu m1 = new JMenu("Conversation");
		        mb.add(m1);
		        JMenuItem m11 = new JMenuItem("Close");
		        JMenuItem m22 = new JMenuItem("Open New");
		        m1.add(m11);
		        m1.add(m22);

		        //Creating the panel at bottom and adding components
		        JPanel panel = new JPanel(); // the panel is not visible in output
		        JLabel label = new JLabel("Entrez du texte");
		        JTextField tf = new JTextField(10); // accepts upto 10 characters
		        JButton send = new JButton("Envoyer");
		        JButton reset = new JButton("Effacer");
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
				model.addColumn("De"); 
				model.addColumn("Date"); 
				model.addColumn("Message");
		        try {
					 Object[][] data = DatabaseManager.getMessages(ipDistante);
					for ( int i=0; i<data.length; i++ ) {
					    if (data[i][0].equals(ipDistante)) {
					    	data[i][0] = pseudo;
					    }
					    else {
					    	data[i][0] = User.defaultViewPseudo;
					    }
						model.addRow(new Object[]{data[i][0], data[i][1], data[i][2]});
					}

				// Append a row 

				tableau.setRowHeight(30);
				TableColumnModel columnModel = tableau.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(100);
				columnModel.getColumn(1).setPreferredWidth(200);
				columnModel.getColumn(2).setPreferredWidth(800);
				
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
