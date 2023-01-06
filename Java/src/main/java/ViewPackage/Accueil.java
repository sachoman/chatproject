package ViewPackage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import NetworkPackage.NetworkManager;
import ThreadPackage.ThreadManager;
import UserPackage.User;

public class Accueil extends Thread{
	public static InetAddress inetIp;
	public JTable tableau;
	DefaultTableModel model;
	public Accueil() {
	}  
	public void updateUsersView() {
        try {
			 String[][] data = DatabaseManager.getConnectedUsers();
			 System.out.println(data);
			 int n = model.getRowCount();
	   		  for (int i=n-1;i>=0; i--) {
	   			  model.removeRow(i);
	   		  }
			 if (data != null) {
				 System.out.println("data non null");
				for ( int i=0; i<data.length; i++ ) {
					model.addRow(new Object[]{data[i][1]});
					System.out.println("addRow : "+i);
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
		 try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Creating the Frame
	        JFrame frame = new JFrame("Accueil");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(1100, 800);
	        
	        // Text Area at the Center
	        //String[][] history = DatabaseManager.getMessages(ipDistante);
			model = new DefaultTableModel(); 
			model.addColumn("utilisateurs");
			tableau = new JTable(model); 
			tableau.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	int selectedRow = tableau.getSelectedRow();
	            	String value = (String) model.getValueAt(selectedRow, 0);
	            	try {
						NetworkManager.ChatWithUser(NetworkManager.stringToInet(DatabaseManager.getIp(value)));
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
			// Create a couple of columns 

			// Append a row 

			tableau.setRowHeight(30);
			TableColumnModel columnModel = tableau.getColumnModel();
			updateUsersView();
			 frame.addWindowListener(new WindowAdapter() {
		            public void windowClosing(WindowEvent e) {
		            	new Ender();
		            }
		        });
			 /*
				frame.getContentPane().add(BorderLayout.SOUTH, panel);
		        frame.getContentPane().add(BorderLayout.NORTH, mb);
		        */
		        frame.getContentPane().add(BorderLayout.WEST, new JScrollPane(tableau));
		        frame.setVisible(true);
		        

	}
}
