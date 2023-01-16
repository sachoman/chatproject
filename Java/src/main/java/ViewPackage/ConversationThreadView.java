package ViewPackage;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import DatabasePackage.DatabaseManager;
import UserPackage.User;
import NetworkPackage.*;
import ThreadPackage.*;

public class ConversationThreadView extends Thread{
		public String ipDistante;
		public InetAddress inetIp;
		public JTable tableau;
		DefaultTableModel model = new DefaultTableModel();
		JFrame frame;
		Boolean init;
		public Boolean newmessage = false;
		public int cptmessages = 0;
		public class TextAreaRenderer extends JTextArea implements TableCellRenderer {
		    public TextAreaRenderer() {
		        setLineWrap(true);
		        setWrapStyleWord(true);
		    }

		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        setText((value == null) ? "" : value.toString());
		        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
		        if (table.getRowHeight(row) != getPreferredSize().height) {
		            table.setRowHeight(row, getPreferredSize().height);
		        }
		        return this;
		    }
		}
		 public ConversationThreadView(String ip, boolean init) {
			 this.ipDistante = ip;
				this.init = init;
			 try {
				this.inetIp = NetworkManager.stringToInet(ip);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }   
		 public void addMessage(String pseudo, String date, String mess) {
			 model.addRow(new Object[]{pseudo,date,mess});
		 }
		 public void updateConvView() {
			 String pseudo = DatabaseManager.getPseudo(ipDistante);
			 Object[][] data = DatabaseManager.getMessages(ipDistante);
			 int nbrows = model.getRowCount();
			 for (int i=0; i<nbrows; i++) {
				 model.removeRow(0);
			 }
				for ( int i=0; i<data.length; i++ ) {
				    if (data[i][0].equals(ipDistante)) {
				    	data[i][0] = pseudo;
				    }
				    else {
				    	data[i][0] = User.defaultViewPseudo;
				    }
					model.addRow(new Object[]{data[i][1], data[i][0], data[i][2]});
				}
				frame.setTitle("Conversation avec "+pseudo);
		 }
		 public void run(){
			 try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Creating the Frame
		        frame = new JFrame("Conversation avec "+ DatabaseManager.getPseudo(ipDistante).toString());
		        frame.setSize(1100, 800);

		        //Creating the MenuBar and adding components
		        JMenuBar mb = new JMenuBar();
		        JMenuItem m0 = new JMenuItem("Fermer la conversation");
		        mb.add(m0);
		        m0.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		  ViewManager.AccueilThRef.fermeConv();
		        	  } 
		        	} );
		        JMenuItem m1 = new JMenuItem("Effacer l'historique");
		        mb.add(m1);
		        m1.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		  DatabaseManager.clearConversation(ipDistante);
		        		  int n = model.getRowCount();
		        		  for (int i=n-1;i>=0; i--) {
		        			  model.removeRow(i);
		        		  }
		        	  } 
		        	} );
		        /*
		        JMenuItem m11 = new JMenuItem("Close");
		        JMenuItem m22 = new JMenuItem("Open New");
		        m1.add(m11);
		        m1.add(m22);
		        */

		        //Creating the panel at bottom and adding components
		        JPanel panel = new JPanel(); // the panel is not visible in output
		        JLabel label = new JLabel("Entrez du texte");
		       //champs de message à envoyer 
		        final JTextArea tf = new JTextArea(1, 50);
		        tf.setMargin(new Insets(10,10,10,10));
		        tf.setLineWrap(true);
		        tf.setWrapStyleWord(true);
		        
		        
		        JButton send = new JButton("Envoyer");
		        send.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		  String message = new String(tf.getText());
		        		  System.out.println(inetIp);
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
		        panel.setLayout(new GridBagLayout());
		        GridBagConstraints gbc = new GridBagConstraints();
		        
		        gbc.gridx = 0;
		   	 	gbc.gridy = 0;
		   	 	gbc.gridwidth = 1;
		   	 	gbc.gridheight = 1;
		  	 	gbc.weightx = 0;
		   	 	gbc.insets = new Insets(5,5,5,5);
		   	 panel.add(label,gbc);
			   	gbc.gridx = 1;
		   	 	gbc.gridy = 0;
		   	 	gbc.gridwidth = 1;
		   	 	gbc.gridheight = 1;
		  	 	gbc.weightx = 1;
		  	 	gbc.ipadx = 200;
		   	 	gbc.insets = new Insets(5,5,5,5);
		   	 panel.add(tf,gbc);
			   	gbc.gridx = 2;
		   	 	gbc.gridy = 0;
		   	 	gbc.gridwidth = 1;
		   	 	gbc.gridheight = 1;
		   	 	gbc.weightx = 0;
		   	 	gbc.ipadx = 0;
		   	 	gbc.insets = new Insets(5,5,5,5);		 
		   	 panel.add(send,gbc);
		   	 	gbc.gridx = 3;
			 	gbc.gridy = 0;
			 	gbc.gridwidth = 1;
			 	gbc.gridheight = 1;
			 	gbc.insets = new Insets(5,5,5,5);
			 panel.add(reset,gbc);
		       

		        // Text Area at the Center
		        //String[][] history = DatabaseManager.getMessages(ipDistante);
		        String pseudo = DatabaseManager.getPseudo(ipDistante);
				model = new DefaultTableModel();
				tableau = new JTable(model);
				//tableau.setEnabled(false);
				tableau.setDefaultEditor(Object.class, null);
				// Create a couple of columns 
				model.addColumn("Date"); 
				model.addColumn("De"); 
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
						model.addRow(new Object[]{data[i][1], data[i][0], data[i][2]});
					}
					

				//tableau.setRowHeight(TextAreaRenderer.getPreferredHeight(TextAreaRenderer.getTableCellRendererComponent));
				TableColumnModel columnModel = tableau.getColumnModel();
				// Pour avoir texte en plusieurs lignes
				tableau.setRowHeight(60);
				//set les largeur
				columnModel.getColumn(0).setPreferredWidth(100);
				columnModel.getColumn(1).setPreferredWidth(200);
				columnModel.getColumn(2).setPreferredWidth(800);
				
				 tableau.setDefaultRenderer(Object.class, new TextAreaRenderer());
				
				 frame.addWindowListener(new WindowAdapter() {
			            public void windowClosing(WindowEvent e) {
			                //ThreadManager.endChat(inetIp);
			            	frame.setVisible(false);
			            }
			        });
					frame.add(BorderLayout.SOUTH, panel);
			        frame.add(BorderLayout.NORTH, mb);
			        frame.add(BorderLayout.CENTER, new JScrollPane(tableau));
			       if (this.init) {
        			ViewManager.AccueilThRef.frame.remove(ViewManager.AccueilThRef.pane);
        			ViewManager.AccueilThRef.pane = this.frame.getContentPane();
        			ViewManager.AccueilThRef.frame.add(BorderLayout.CENTER, ViewManager.AccueilThRef.pane);
        			ViewManager.AccueilThRef.updateUsersView();
        			ViewManager.AccueilThRef.frame.setVisible(true);
        			ViewManager.AccueilThRef.inetIp = inetIp;
			       }
			       else {
			    	   this.init = true;
			       }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    }
}
